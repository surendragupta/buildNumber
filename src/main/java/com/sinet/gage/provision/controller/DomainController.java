package com.sinet.gage.provision.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinet.gage.dlap.entities.Customization;
import com.sinet.gage.dlap.entities.DomainResponse;
import com.sinet.gage.dlap.exception.AvailabilityLicenseException;
import com.sinet.gage.dlap.exception.DomainDuplicateException;
import com.sinet.gage.dlap.exception.DomainNameDuplicateException;
import com.sinet.gage.dlap.exception.UserDuplicateException;
import com.sinet.gage.provision.model.DistrictDomainRequest;
import com.sinet.gage.provision.model.EditSchoolDomainRequest;
import com.sinet.gage.provision.model.Message;
import com.sinet.gage.provision.model.MessageUtil;
import com.sinet.gage.provision.model.SchoolDomainRequest;
import com.sinet.gage.provision.service.CourseService;
import com.sinet.gage.provision.service.DomainFacade;
import com.sinet.gage.provision.service.DomainService;
import com.sinet.gage.provision.util.Constants;
import com.sinet.gage.provision.util.MessageConstants;

/**
 * Rest controller for Domain resource
 * 
 * @author Team Gage
 *
 */
@RestController
@RequestMapping(value = "/domain")
public class DomainController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DomainController.class);

	@Autowired
	DomainService domainService;

	@Autowired
	CourseService courseService;

	@Autowired
	MessageUtil messageUtil;

	@Autowired
	DomainFacade domainFacade;

	@RequestMapping(value = "/{domainid}", method = RequestMethod.GET)
	public Message getDomain(@RequestHeader(value = "token", required = false) String token, @PathVariable("domainid") Long domainId) throws Exception {
		DomainResponse response = domainService.findDomain(token, domainId);

		if (response != null) {
			Customization customization = domainService.getDomainData(token, domainId);

			Map<String, List<String>> providerMap = new HashMap<>();
			if (customization != null && customization.getEdivatelearn() != null && "DISTRICT".equalsIgnoreCase(customization.getEdivatelearn().getDomaintype())) {
				providerMap = domainFacade.getSubscriptionsForDomain(token, domainId.toString());
			} else if (customization != null && customization.getEdivatelearn() != null && "SCHOOL".equalsIgnoreCase(customization.getEdivatelearn().getDomaintype())) {
				providerMap = domainFacade.getSubscriptionsForSchool(token, domainId.toString());
			}
			
			// replacing the multiple data default properties names
			Map<String, String> renameMap = new HashMap<String, String>();
			renameMap.put("data1", "domain");
			renameMap.put("data2", "customization");
			renameMap.put("data3", "subscribedproviderlist");
			renameMap.put("data4", "subscribedcourselist");
			return messageUtil.createMessageWithPayload(MessageConstants.DOMAIN_FOUND, response, customization,
					providerMap.get(Constants.CATALOG_DOMAIN_LIST), providerMap.get(Constants.COURSE_LIST), renameMap,
					DomainResponse.class);
		} else {
			LOGGER.debug(" state domains not  available  with domain id " + domainId);
			return messageUtil.createMessage(MessageConstants.DOMAIN_NOT_FOUND, Boolean.FALSE, domainId);
		}
	}

	/**
	 * Returns all state domains
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list/state", method = RequestMethod.GET)
	@ResponseBody
	public Message getAllStates(@RequestHeader(value = "token", required = false) String token) throws InterruptedException {
		List<DomainResponse> listOfDomainResponse = domainService.findAllStateDomains(token, 0);
		if ( !CollectionUtils.isEmpty( listOfDomainResponse ) ) {
			LOGGER.debug( "Found " + listOfDomainResponse.size() + " state domains" );
			return messageUtil.createMessageWithPayload( MessageConstants.DOMAINS_FOUND, listOfDomainResponse,Collections.singletonMap("dataModels", "domains"), DomainResponse.class );
		} else {
			LOGGER.debug( "No state domains available" );
			return messageUtil.createMessage(MessageConstants.DOMAINS_NOT_FOUND, Boolean.FALSE);
		}
	}

	/**
	 * Returns all content providers domains
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list/providers", method = RequestMethod.GET)
	@ResponseBody
	public Message getAllContentProviderDomains(@RequestHeader(value = "token", required = false) String token) throws InterruptedException {
		List<DomainResponse> listOfDomainResponse = domainService.findAllContentDomains(token, 0);
		if (!CollectionUtils.isEmpty( listOfDomainResponse ) ) {
			LOGGER.debug("Found " + listOfDomainResponse.size() + " content domains ");
			return messageUtil.createMessageWithPayload( MessageConstants.DOMAINS_FOUND, listOfDomainResponse, Collections.singletonMap("dataModels", "domains"), DomainResponse.class );
		} else {
			LOGGER.debug( "No course catalog domains available" );
			return messageUtil.createMessage(MessageConstants.DOMAINS_NOT_FOUND, Boolean.FALSE);
		}
	}

	/**
	 * Returns all child domains domains
	 * 
	 * @param domainId
	 * @return
	 */
	@RequestMapping(value = "/list/{domainId}", method = RequestMethod.GET)
	@ResponseBody
	public Message getAllChildDomains(@PathVariable("domainId") String domainId,
			@RequestParam(value = "querystring", required = false, defaultValue = "0") String queryString,
			@RequestParam(value = "searchtext", required = false, defaultValue = "") String searchText,
			@RequestParam(value = "limit", required = false, defaultValue = "0") Integer limit,
			@RequestHeader(value = "token", required = false) String token) {
		List<DomainResponse> listOfDomainResponse = domainService.findAllChildDomains( token, domainId, queryString,searchText, limit );
		if ( !CollectionUtils.isEmpty( listOfDomainResponse ) ) {
			LOGGER.debug("Found " + listOfDomainResponse.size() + " child domains " + domainId);
			return messageUtil.createMessageWithPayload(MessageConstants.DOMAINS_FOUND, listOfDomainResponse, Collections.singletonMap("dataModels", "domains"), DomainResponse.class);
		} else {
			LOGGER.debug( "No child domains available  for parent " + domainId );
			return messageUtil.createMessage( MessageConstants.DOMAINS_NOT_FOUND, Boolean.FALSE );
		}
	}
	
	/**
	 * Returns all child domains domains
	 * 
	 * @param domainId
	 * @return
	 */
	@RequestMapping(value = "/list/school/{domainId}", method = RequestMethod.GET)
	@ResponseBody
	public Message getAllChildDomainsWithDomainData(@PathVariable("domainId") String domainId,
			@RequestParam(value = "querystring", required = false, defaultValue = "0") String queryString,
			@RequestParam(value = "searchtext", required = false, defaultValue = "") String searchText,
			@RequestParam(value = "limit", required = false, defaultValue = "0") Integer limit,
			@RequestHeader(value = "token", required = false) String token) {
		List<DomainResponse> listOfDomainResponse = domainService.findAllChildDomainsWithDomainData(token, domainId, queryString,
				searchText, limit);
		if ( !CollectionUtils.isEmpty( listOfDomainResponse ) ) {
			LOGGER.debug("Found " + listOfDomainResponse.size() + " child domains " + domainId);
			return messageUtil.createMessageWithPayload( MessageConstants.DOMAINS_FOUND, listOfDomainResponse, Collections.singletonMap("dataModels", "domains"), DomainResponse.class );
		} else {
			LOGGER.debug( "No child domains available  for parent " + domainId );
			return messageUtil.createMessage( MessageConstants.DOMAINS_NOT_FOUND, Boolean.FALSE );
		}
	}

	/**
	 * Returns all child domains domains
	 * 
	 * @param District
	 *            Data
	 * @return Message
	 * @throws DomainDuplicateException
	 */
	@RequestMapping(value = "/create/district", method = RequestMethod.POST)
	@ResponseBody
	public Message createDistrictDomain( @RequestHeader(value = "token", required = false) String token, @Valid @RequestBody DistrictDomainRequest domainData ) throws InterruptedException, UserDuplicateException {
		boolean success = Boolean.FALSE;
		String code = "";
		try {
			success = domainFacade.createDistrictDomain(token, domainData);
		} catch (DomainDuplicateException e) {
			LOGGER.error("Duplicate domain login prefix with name exists " + domainData.getLoginPrefix());
			return messageUtil.createMessage( MessageConstants.DOMAIN_PREFIX_DUPLICATED, success, domainData.getLoginPrefix() );
		} catch (DomainNameDuplicateException e) {
			LOGGER.error("Duplicate domain with name exists " + domainData.getName());
			return messageUtil.createMessage( MessageConstants.DOMAIN_NAME_DUPLICATED, success, domainData.getName() );
		}
		code = success ? MessageConstants.DOMAIN_CREATED : MessageConstants.DOMAIN_NOT_CREATED;
		return messageUtil.createMessage( code, success );
	}

	/**
	 * 
	 * 
	 * @param District
	 *            Data
	 * @return Message
	 * @throws UserDuplicateException
	 */
	@RequestMapping(value = "/edit/district/{domainid}", method = RequestMethod.POST)
	@ResponseBody
	public Message editDistrictDomain( @RequestHeader(value = "token", required = false) String token, @RequestBody DistrictDomainRequest domainData, @PathVariable("domainid") String domainId ) {
		boolean success = Boolean.FALSE;
		String code = "";
		try {
			success = domainFacade.editDistrictDomain(token, domainData, domainId);
		} catch (DomainNameDuplicateException e) {
			LOGGER.error("Duplicate domain with name exists " + domainData.getName());
			return messageUtil.createMessage( MessageConstants.DOMAIN_NAME_DUPLICATED, success, domainData.getName() );
		}
		code = success ? MessageConstants.DOMAIN_UPDATED : MessageConstants.DOMAIN_NOT_UPDATED;
		return messageUtil.createMessage( code, success );
	}

	/**
	 * Create School rest service method
	 * 
	 * @param token
	 *            DLAP Authentication token
	 * @param request
	 *            Create School Domain request
	 * @return Message response pojo
	 */
	@RequestMapping(value = "/create/school", method = RequestMethod.POST)
	@ResponseBody
	public Message createSchoolDomain( @RequestHeader(value = "token", required = false) String token, @RequestBody SchoolDomainRequest request ) {
		boolean success = Boolean.FALSE;
		String code = "";
		try {
			success = domainFacade.createSchool( token, request );
		} catch ( DomainDuplicateException e ) {
			LOGGER.error( "Duplicate domain with same login prefix exists for " + request.getName() );
			return messageUtil.createMessage( MessageConstants.DOMAIN_PREFIX_DUPLICATED, success, request.getName() );
		} catch ( AvailabilityLicenseException e ) {
			LOGGER.error( "Unable to create domain " + request.getName() + " of district domain id " + request.getDistrictDomainId() + " because of unavailability of license." );
			return messageUtil.createMessage(MessageConstants.LICENSE_UNAVAILABILITY, success, request.getName() );
		} catch ( DomainNameDuplicateException e ) {
			LOGGER.error("Duplicate domain with same name exists for " + request.getName() );
			return messageUtil.createMessage(MessageConstants.DOMAIN_NAME_DUPLICATED, success, request.getName() );
		}
		code = success ? MessageConstants.DOMAIN_CREATED : MessageConstants.DOMAIN_NOT_CREATED;
		return messageUtil.createMessage(code, success);
	}

	/**
	 * Edit School rest service method
	 * 
	 * @param token
	 *            DLAP Authentication token
	 * @param request
	 *            Edit School Domain request
	 * @return Message response pojo
	 */
	@RequestMapping(value = "/edit/school", method = RequestMethod.POST)
	@ResponseBody
	public Message editSchoolDomain( @RequestHeader(value = "token", required = false) String token, @RequestBody EditSchoolDomainRequest request ) {
		boolean success = Boolean.FALSE;
		String code = "";
		try {
			success = domainFacade.editSchool(token, request);
			} catch (AvailabilityLicenseException e) {
				LOGGER.error("Unable to create domain " + request.getName() + " with domain id " + request.getDomainId() + " because of unavailability of license.");
				return messageUtil.createMessage(MessageConstants.LICENSE_UNAVAILABILITY, success, request.getName());
			} catch (DomainNameDuplicateException e) {
				LOGGER.error("Duplicate domain with name exists " + request.getName());
				return messageUtil.createMessage(MessageConstants.DOMAIN_NAME_DUPLICATED, success, request.getName());
			}
		code=success?MessageConstants.DOMAIN_UPDATED:MessageConstants.DOMAIN_NOT_UPDATED;
		return messageUtil.createMessage(code,success);
	}
}
