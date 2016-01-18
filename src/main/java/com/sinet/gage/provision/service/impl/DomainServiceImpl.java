
package com.sinet.gage.provision.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sinet.gage.dlap.clients.DlapAuthenticationClient;
import com.sinet.gage.dlap.clients.DlapDomainClient;
import com.sinet.gage.dlap.entities.Customization;
import com.sinet.gage.dlap.entities.Customization.AllowViewInGpeerCompletion;
import com.sinet.gage.dlap.entities.Customization.Securehash;
import com.sinet.gage.dlap.entities.Data;
import com.sinet.gage.dlap.entities.DomainRequest;
import com.sinet.gage.dlap.entities.DomainResponse;
import com.sinet.gage.dlap.entities.DomainUpdate;
import com.sinet.gage.dlap.entities.EdivateLearn;
import com.sinet.gage.dlap.exception.DomainDuplicateException;
import com.sinet.gage.dlap.exception.DomainNameDuplicateException;
import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.model.Domain;
import com.sinet.gage.provision.model.DomainType;
import com.sinet.gage.provision.model.SchoolDomainRequest;
import com.sinet.gage.provision.service.DomainService;

/**
 * 
 * @author Team Gage
 *
 */
@Service("domainService")
public class DomainServiceImpl implements DomainService {

	private static final Logger logger = Logger.getLogger(DomainServiceImpl.class);

	@Autowired
	AppProperties properties;

	@Autowired
	DlapDomainClient domainclient;

	@Autowired
	DlapAuthenticationClient authClient;

	/**
	 * List all state domains under customer domain
	 * 
	 * @return List of domains
	 */
	@Override
	public List<DomainResponse> findAllStateDomains(String token, int limit) {
		return findChildDomains(token, properties.getCustomerDomainId(), "", "", limit).stream()
				.filter(d -> d.getUserspace().startsWith("ed"))
				.sorted((s1, s2) -> s1.getName().compareTo(s2.getName()))
				.collect(Collectors.toList());
	}

	/**
	 * List all content domains under course catalog
	 * 
	 * @return List of domains
	 */
	@Override
	public List<DomainResponse> findAllContentDomains(String token, int limit) {
		return findChildDomains(token, properties.getCourseCatalogDomainId(), "", "", limit);
	}

	/**
	 * List all child domains under state domain
	 * 
	 * @param domainId
	 *            Parent domain Id
	 * @return List of domains
	 */
	@Override
	public List<DomainResponse> findAllChildDomains(String token, String domainId, String queryString,
			String searchText, int limit) {
		return findChildDomains(token, domainId, "/id>" + queryString, searchText, limit);
	}

	/**
	 * 
	 * @param token
	 * @param domainId
	 * @param queryString
	 * @param searchText
	 * @param limit
	 * @return
	 */
	private List<DomainResponse> findChildDomains(String token, String domainId, String queryString, String searchText,
			int limit) {
		List<DomainResponse> listOfDomains = domainclient.getDomainReponseList(token, Integer.parseInt(domainId),
				queryString, searchText, limit);
		listOfDomains = listOfDomains.stream().sorted((r1, r2) -> r1.getName().compareTo(r2.getName()))
				.collect(Collectors.toList());
		return listOfDomains;
	}

	/**
	 * Create domain
	 * 
	 * @return boolean
	 * @throws DomainNameDuplicateException
	 */
	@Override
	public List<Integer> createDomain(String token, List<Domain> domainList, EdivateLearn edivateLearn, List<String> catalogIds, DomainType domainType ) throws DomainDuplicateException, DomainNameDuplicateException {
		List<DomainRequest> domainRequestList = new ArrayList<DomainRequest>(domainList.size());
		// we are sending only edivateLearn
		// we need to send securehash object also inplace of null
		List<Securehash> secureHashs = buildLTILists(token, catalogIds);

		// we are sending allowviewingpeercompletion object
		AllowViewInGpeerCompletion avigc = new AllowViewInGpeerCompletion("true");

		// copy files and resource base from district template
		Customization cust = getDataFromTemplateDomain( token, domainType );

		Customization customization = new Customization( edivateLearn, secureHashs, cust != null ? cust.getFiles() : null, cust != null ? cust.getResourcebase() : null, avigc, null, null, null, null, null);
		Data data = new Data( customization );

		for (Domain domain : domainList) {
			DomainRequest domainRequest = new DomainRequest( domain.getName(), domain.getUserspace(), domain.getParentId(), domain.getReference(), data);
			domainRequestList.add( domainRequest );
		}
		// check domain name duplication
		for (DomainRequest domain : domainRequestList) {
			List<DomainResponse> domianList = domainclient.getDomainReponseList(token, Integer.parseInt(domain.getParentid()), null, domain.getName(), 0);
			for (DomainResponse domainResponse : domianList) {
				if (domain.getName().equalsIgnoreCase(domainResponse.getName())) {
					throw new DomainNameDuplicateException("Domain Name Duplication");
				}
			}
		}
		return domainclient.createDomains(token, domainRequestList);
	}

	/**
	 * 
	 * @param token
	 * @param templateDomain
	 * @return
	 */
	private Customization getDataFromTemplateDomain(String token, DomainType domainType ) {
		Long templateDomain = domainType == DomainType.SCHOOL ? Long.valueOf( properties.getTemplateSchoolDomainId() ) : Long.valueOf( properties.getTemplateDistrictDomainId() );
		return getDomainData(token, templateDomain);
	}

	/**
	 * 
	 * @param token
	 * @param catalogIds
	 * @return
	 */
	private List<Securehash> buildLTILists(String token, List<String> catalogIds) {
		List<Securehash> listOfSecureHashs = null;
		if ( !CollectionUtils.isEmpty(catalogIds) ) {
			listOfSecureHashs = new ArrayList<>();
			for (String catalogId : catalogIds) {
				logger.debug(" Looking up LTI code for domain " + catalogId);
				Customization customization = getDomainData(token, Long.parseLong(catalogId));
				if (customization != null && !CollectionUtils.isEmpty(customization.getSecurehash())) {
					logger.debug(" Found LTI code for domain " + catalogId);
					listOfSecureHashs.addAll(customization.getSecurehash());
				}
			}
		}
		return listOfSecureHashs;
	}

	/**
	 * Delete domain
	 * 
	 * @return boolean
	 */
	@Override
	public boolean deleteDomain(String token, String domainId) {
		return domainclient.deleteDomain(token, domainId);
	}

	/**
	 * Update domain
	 * 
	 * @return boolean
	 * @throws DomainNameDuplicateException
	 */
	@Override
	public boolean updateDomain(String token, List<Domain> domainUpdateList, EdivateLearn edivateLearn, List<String> catalogIds, DomainType domainType) throws DomainNameDuplicateException {
		List<DomainUpdate> domainUpdateRequestList = new ArrayList<DomainUpdate>(domainUpdateList.size());
		// we are sending only edivateLearn
		// we need to send securehash object also inplace of null
		List<Securehash> secureHashs = buildLTILists(token, catalogIds);

		// we are sending allowviewingpeercompletion object
		AllowViewInGpeerCompletion avigc = new AllowViewInGpeerCompletion("true");

		// copy files and resource base from district template
		Customization domainCustomization = getDomainData(token, Long.parseLong(domainUpdateList.get(0).getDomainId()) );

		Customization customization = null;
		if ( domainCustomization != null ) {
		 customization = new Customization(edivateLearn, secureHashs,
				domainCustomization.getFiles() != null ? domainCustomization.getFiles() : null,
				domainCustomization.getResourcebase() != null ? domainCustomization.getResourcebase() : null, 
						avigc, domainCustomization.getLearningobjectives() != null ? domainCustomization.getLearningobjectives() : null,
								domainCustomization.getMenuitems() != null ? domainCustomization.getMenuitems() : null, 
										domainCustomization.getStrings() != null ? domainCustomization.getStrings() : null, 
												domainCustomization.getItemmetadata() != null ? domainCustomization.getItemmetadata() : null, 
														domainCustomization.getAuthentication() != null? domainCustomization.getAuthentication() : null);
		} else {
			customization = new Customization(edivateLearn, secureHashs,
					null, null, avigc, null, null, null, null, null);
		}

		Data data = new Data(customization);

		for (Domain domain : domainUpdateList) {
			DomainUpdate domainRequest = new DomainUpdate(domain.getDomainId(), domain.getName(), domain.getReference(), domain.getParentId(), null, data);
			domainUpdateRequestList.add(domainRequest);
		}

		// check for duplicate domain
		for (DomainUpdate domain : domainUpdateRequestList) {
			List<DomainResponse> domianList = domainclient.getDomainReponseList(token, Integer.parseInt(domain.getParentid()), null, domain.getName(), 0);
			for (DomainResponse domainResponse : domianList) {
				if (!domainUpdateRequestList.get(0).getDomainid().equals(domainResponse.getId())
						&& domainUpdateRequestList.get(0).getName().equalsIgnoreCase(domainResponse.getName())) {
					throw new DomainNameDuplicateException("Domain Name Duplication");
				}
			}
		}
		return domainclient.updateDomain(token, domainUpdateRequestList);
	}

	/**
	 * Get domain details searched by domainId
	 * 
	 * @param token
	 *            authentication token for dlap api
	 * @param domainId
	 *            domain id for searching domain
	 * @return domain response
	 */
	@Override
	public DomainResponse findDomain(String token, Long domainId) {
		return domainclient.getDomain(token, domainId);
	}

	/**
	 * Get domain customization data searched by domainId
	 * 
	 * @param token
	 *            authentication token for dlap api
	 * @param domainId
	 *            domain id for searching domain
	 * @return domain customization data
	 */
	@Override
	public Customization getDomainData(String token, Long domainId) {
		return domainclient.getDomain2Data(token, domainId.toString());
	}

	/**
	 * List all child domains under parent domain with domain data
	 * 
	 * @param domainId
	 *            Parent domain Id
	 * @return List of domains
	 */
	@Override
	public List<DomainResponse> findAllChildDomainsWithDomainData(String token, String domainId, String queryString,
			String searchText, int limit) {
		return domainclient.getDomainReponseListWithDomainData(token, Integer.parseInt(domainId), "/id>" + queryString,
				searchText, limit);
	}
	
	/**
	 * 
	 * @param token
	 * @param request
	 * @return
	 */
	@Override
	public boolean getAvailabilityOfLicense(String token, SchoolDomainRequest request, String existingDomainId) {
		boolean flag = false;
		Customization customization = getDomainData(token, request.getDistrictDomainId());
		int districtLicense = Integer.parseInt(customization.getEdivatelearn().getNooflicense());
		logger.debug("No of license of District: " + districtLicense);
		/* DefaultId=0 for queryString to fetch all domains */
		List<DomainResponse> listOfDomainResponse = findAllChildDomains(token, String.valueOf(request.getDistrictDomainId()), "0", "", 0);
		int schoolLicense = 0;//(Integer.parseInt(existingDomainId) == 0 ? request.getNumbersOfLicense() : 0);
		for (DomainResponse domainResponse : listOfDomainResponse) {
			Customization schoolCustomization = getDomainData( token, Long.parseLong( domainResponse.getId() ) );
			if (domainResponse.getId().equals(existingDomainId)) {
				schoolLicense += request.getNumbersOfLicense();
				flag = true;
			} else {
				schoolLicense += Integer.parseInt(schoolCustomization.getEdivatelearn().getNooflicense() != null ? schoolCustomization.getEdivatelearn().getNooflicense() : "0");
			}
		}
		if (!flag) {
			schoolLicense += request.getNumbersOfLicense();
		}
		logger.debug("Total no of license of district: " + districtLicense + ", and school license: " + schoolLicense);
		return districtLicense >= schoolLicense;
	}
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	@Override
	public List<String> findProviderIdsList(String token) {
		List<DomainResponse> domainList = findAllContentDomains(token, 0);
		return domainList.stream().map(DomainResponse::getId).collect(Collectors.toList());
	}
	
}
