package com.sinet.gage.provision.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sinet.gage.dlap.entities.CourseResponse;
import com.sinet.gage.dlap.entities.Customization;
import com.sinet.gage.dlap.entities.DomainResponse;
import com.sinet.gage.dlap.entities.EdivateLearn;
import com.sinet.gage.dlap.entities.SubscriptionResponse;
import com.sinet.gage.dlap.entities.UserRequest;
import com.sinet.gage.dlap.exception.AvailabilityLicenseException;
import com.sinet.gage.dlap.exception.DomainDuplicateException;
import com.sinet.gage.dlap.exception.DomainNameDuplicateException;
import com.sinet.gage.dlap.exception.UserDuplicateException;
import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.data.model.District;
import com.sinet.gage.provision.data.model.School;
import com.sinet.gage.provision.model.BaseDomainRequest;
import com.sinet.gage.provision.model.DistrictDomainRequest;
import com.sinet.gage.provision.model.Domain;
import com.sinet.gage.provision.model.DomainType;
import com.sinet.gage.provision.model.EditSchoolDomainRequest;
import com.sinet.gage.provision.model.SchoolDomainRequest;
import com.sinet.gage.provision.service.CourseService;
import com.sinet.gage.provision.service.DistrictService;
import com.sinet.gage.provision.service.DomainFacade;
import com.sinet.gage.provision.service.DomainService;
import com.sinet.gage.provision.service.ResourceService;
import com.sinet.gage.provision.service.SubscriptionService;
import com.sinet.gage.provision.service.UserService;
import com.sinet.gage.provision.util.Constants;
import com.sinet.gage.provision.util.DateUtil;
import com.sinet.gage.provision.util.PojoMapper;

/**
 * Service for domain management
 * 
 * @author Team Gage
 *
 */
@Service
public class DomainFacadeImpl implements DomainFacade {

	protected final Logger log = LoggerFactory.getLogger(DomainFacadeImpl.class);

	private DateUtil dateUtil = new DateUtil();
	
	@Autowired
	PojoMapper mapToObject;

	@Autowired
	UserService userService;

	@Autowired
	DomainService domainService;

	@Autowired
	ResourceService resourceService;

	@Autowired
	CourseService courseService;

	@Autowired
	DistrictService districtService;

	@Autowired
	SubscriptionService subscriptionService;

	@Autowired
	AppProperties properties;

	/**
	 * Creates the district domain
	 * 
	 * @param token
	 *            DLAP API authentication token
	 * @param request
	 *            CreateDistrictRequest pojo contians domain details
	 */
	@Override
	public boolean createDistrictDomain(String token, DistrictDomainRequest request)
			throws DomainDuplicateException, DomainNameDuplicateException {
		log.debug("Starts createDistrictDomain ");

		boolean success = Boolean.TRUE;

		List<Domain> domainList = mapToObject.mapToDomainList(request);
		String name = request.getName();

		List<Integer> domainIds = null;

		try {
			// step 1 create domain with null data
			log.debug("Creating new domain " + name);

			// create EdivateLearn object with form Data
			EdivateLearn edivateLearn = createEdivateLearnObject(request,"district", request.isFullSubscription(), request.getSubscriptionStartDate(), request.getSubscriptionEndDate());
			domainIds = domainService.createDomain(token, domainList, edivateLearn, request.getCourseCatalogs(), DomainType.DISTRICT );

			if ( CollectionUtils.isEmpty(domainIds) ) {
				log.debug("Failed to create new domain " + name);
				success = Boolean.FALSE;
				return success;
			}

			String domainId = domainIds.get(0).toString();
			log.debug("Created new domain " + name + " with domain id " + domainId);

			// step 2 create admin user for newly created District
			log.debug("Creating administrator user for domain " + name + " with domain id " + domainId);
			success &= persistDistrict(token, request, domainId);
			log.debug("Created new administrator user for domain " + name + " with domain id " + domainId);

			// step 3 copy resources from district template to newly created
			// domain
			log.debug("Copy resources to new domain " + name + " with domain id " + domainId);
			success &= resourceService.copyResources(token, properties.getTemplateDistrictDomainId(), domainId);
			log.debug("Copied resource to new domain " + name + " with domain id " + domainId);

			// step 4 copy courses from one domain to another
			log.debug("Copy courses to new domain " + name);
			success &= courseService.copyCourses(token, request, domainId);
			log.debug("Copied courses to new domain " + name + " with domain id " + domainId);

			// Step 5: subscribes to courses or provider domains
			log.debug("Creating subscriptions to new domain " + name);
			success &= subscriptionService.createDistrictDomainSubscription(token, request, domainId);
			log.debug("Creating subscriptions to new domain " + name + " with domain id " + domainId);

			if (!success) {
				throw new Exception("Unable to create domain " + name + " with domain id " + domainId
						+ " one or more steps failed rolling back");
			}

			log.debug("Completed createDistrictDomain ");

		} catch (DomainDuplicateException  | DomainNameDuplicateException e ) {
			log.error("Error creating District domain with duplicate login prefix or name  " + request.getLoginPrefix(), e);
			throw e;
		} catch ( UserDuplicateException | Exception e ) {
			log.error("Error creating District domain with user " + request.getName(), e);
			rollbackDistrictDomain(token, domainIds.get(0).toString());
			success = Boolean.FALSE;
		} 

		return success;
	}	

	/**
	 * Maps the domain details to EdivateLearn pojo
	 * 
	 * @param domainData
	 *            CreateDistrictRequest pojo contains domain details
	 * @return EdivateLearn pojo for domain data
	 */
     private EdivateLearn createEdivateLearnObject(BaseDomainRequest request,String domainType, boolean fullSubscription, String subscriptionStartDate, String subscriptionEndDate) {
         if ( StringUtils.isNotBlank( subscriptionStartDate ) && StringUtils.isBlank( subscriptionEndDate ) ) {
             Date endDate = dateUtil.addDays( subscriptionStartDate, properties.getDefaultSubscriptionInDays() );
             subscriptionEndDate = dateUtil.toMDYFormat( endDate );
         } else if ( StringUtils.isBlank( subscriptionStartDate ) ) {        	 
        	 Date endDate = dateUtil.addDays( dateUtil.toMDYFormat(), properties.getDefaultSubscriptionInDays() );
             subscriptionEndDate = dateUtil.toMDYFormat( endDate );
             subscriptionStartDate = dateUtil.toMDYFormat();
         }
         
         String pilotStartDate = "";
         if ( request.isPilotDomain() ) {
        	 pilotStartDate = dateUtil.toMDYFormat();
        	 if ( StringUtils.isBlank( request.getPilotEndDate() ) ) {		          
		          request.setPilotEndDate( dateUtil.toMDYFormat( dateUtil.addDays(pilotStartDate, properties.getDefaultPilotRunInDays() ) ) );   
        	 }
         } else { 
        	 request.setPilotEndDate( "" );
         }
		
    	 return new EdivateLearn(domainType, request.getLicenseType(), Integer.toString(request.getNumbersOfLicense()),
				request.getLicensePool(), request.isPilotDomain(), pilotStartDate,
				request.getPilotEndDate(), fullSubscription, subscriptionStartDate, subscriptionEndDate);
	}

	/**
	 * Delete created domain if domain creation is partially successful also
	 * removes the district domain record from database.
	 * 
	 * @param token
	 *            DLAP API authentication token
	 * @param domainId
	 *            Id of the domain
	 */
	protected void rollbackDistrictDomain(String token, String domainId) {
		log.debug("Rollback create domain for domainId " + domainId);
		domainService.deleteDomain(token, domainId);
		log.debug("Rollback district record for districtId " + domainId);
		districtService.deleteDistrict(Long.parseLong(domainId));
	}

	/**
	 * 
	 * @param token
	 * @param domain
	 * @param domainId
	 * @return
	 * @throws UserDuplicateException
	 */
	protected boolean persistDistrict(String token, DistrictDomainRequest domain, String domainId)
			throws UserDuplicateException {
		boolean success = Boolean.FALSE;

		UserRequest user = userService.createAdminUser(token, domainId);

		if (user != null && StringUtils.isNotBlank(user.getUserId())) {
			log.debug("Created Administrator user ");
			District district = mapToObject.mapToDistrict(domain, user, domainId);
			log.debug("Saving district domain record ");
			district = districtService.insertDistrict(district);
			if (district != null) {
				log.debug("Saved district domain record ");
				success = Boolean.TRUE;
			}
		}
		return success;
	}
	
	/**
	 * 
	 * @param token
	 * @param request
	 * @param domainId
	 * @return
	 * @throws UserDuplicateException
	 */
	protected boolean persistSchool(String token, SchoolDomainRequest request, String domainId)
			throws UserDuplicateException {
		boolean success = Boolean.FALSE;

		UserRequest user = userService.createAdminUser(token, domainId);

		if (user != null && StringUtils.isNotBlank(user.getUserId())) {
			log.debug("Created Administrator user ");
			School school = mapToObject.mapToSchool(request, user, domainId);
			log.debug("Saving district domain record ");
			school = districtService.insertSchool(request.getDistrictDomainId(), school);
			if (school != null) {
				log.debug("Saved school domain record ");
				success = Boolean.TRUE;
			}
		}
		return success;
	}
	
	/**
	 * 
	 * @param token
	 * @param request
	 * @param domainId
	 * @throws DomainNameDuplicateException
	 */
	@Override
	public boolean editDistrictDomain(String token, DistrictDomainRequest request, String domainId)
			throws DomainNameDuplicateException {
		log.debug("Starts editDistrictDomain ");
		boolean success = Boolean.TRUE;
		
		if (!org.springframework.util.StringUtils.hasLength(request.getSubscriptionEndDate())) {
			request.setSubscriptionEndDate(dateUtil.subscriptionEndDate(request.getSubscriptionStartDate(), properties.getDefaultSubscriptionInDays()));
		}
		// create EdivateLearn object with form Data
		EdivateLearn edivateLearn = createEdivateLearnObject(request,"district", request.isFullSubscription(), request.getSubscriptionStartDate(), request.getSubscriptionEndDate());

		List<Domain> domainUpdateList = mapToObject.mapToDomainList(request);
		domainUpdateList.get(0).setDomainId(domainId); // for now we are only
														// updating domain
														// details
		// step 1 update the domain
		try {
			log.debug("Updating the domain with name " + request.getName() + " with domainId " + domainId);
			success &= domainService.updateDomain(token, domainUpdateList, edivateLearn, request.getCourseCatalogs(), DomainType.DISTRICT );
			log.debug("Updated the domain with name " + request.getName() + " with domainId " + domainId);

			// step 2 update the courses
			/*log.debug("Deleting the courses for domain with name " + request.getName() + " with domainId "
					+ domainId);
			success &= courseService.deleteAllCoursesOfDomain(token, domainId);
			log.debug("Deleted the courses for domain with name " + request.getName() + " with domainId "
					+ domainId);*/

			log.debug("Copy courses for with name " + request.getName() + " with domainId " + domainId);
			success &= courseService.copyCourses(token, request, domainId);
			log.debug("Updated the courses for domain with name " + request.getName() + " with domainId "
					+ domainId);

			// step 3 update subscription
			/*log.debug("Deleting subscriptions for domain with name " + request.getName() + " with domainId "
					+ domainId);
			success &= subscriptionService.deleteSubscription(token, domainId);
			log.debug("Deleted subscriptions for domain with name " + request.getName() + " with domainId "
					+ domainId);*/

			log.debug("Creating subscriptions to new domain " + request.getName());
			success &= subscriptionService.createDistrictDomainSubscription(token, request, domainId);
			log.debug("Updated subscriptions for domain name " + request.getName() + " with domain id "
					+ domainId);

			District district = mapToObject.mapToDistrictForEditing(request, domainId);
			log.debug("Saving district domain record ");
			district = districtService.updateDistrict(district);
			if (district != null) {
				log.debug("Saved district domain record ");
				success &= Boolean.TRUE;
			}

			if (!success) {
				throw new Exception("Unable to edit domain " + request.getName() + " with domain id "
						+ domainId + " one or more steps failed rolling back");
			}

			log.debug("Completed domain updation for domain name " + request.getName() + " with domain id " + domainId + "successfully");

		} catch (DomainNameDuplicateException e) {
			log.error("Error updating District domain with duplicate domain name  " + request.getName() + " "
					+ e);
			throw e;
		} catch (Exception e) {
			log.error("Error updating District domain  " + request.getName() + " " + e);
			e.printStackTrace();
			success = Boolean.FALSE;
		}
		return success;
	}
	
	/**
	 * Creates a new School domain
	 * 
	 * @param token
	 *            DLAP authentication token
	 * @param request
	 *            Create School domain request pojo
	 * @throws DomainNameDuplicateException
	 * @throws DomainDuplicateException
	 * @throws AvailabilityLicenseException
	 */
	@Override
	public boolean createSchool(String token, SchoolDomainRequest request)
			throws DomainDuplicateException, DomainNameDuplicateException, AvailabilityLicenseException {

		log.debug("Starts createSchool ");

		boolean success = Boolean.TRUE;
		List<Domain> domains = mapToObject.mapToDomains(request);

		List<Integer> domainIds = null;

		try {
			log.debug("Getting availability of license of " + request.getName());
			success &= domainService.getAvailabilityOfLicense(token, request, "0");
			if (!success) {
				throw new AvailabilityLicenseException(
						"Unable to create domain " + request.getName() + " of district domain id "
								+ request.getDistrictDomainId() + " because of unavailability of license.");
			}

			// step 1 create domain with null data
			log.debug("Creating new school domain " + request.getName());
			String domainId = null;
			/*List<String> catalogIds = getSubscriptionsForDomain(token, request.getDistrictDomainId().toString())
					.get(Constants.CATALOG_DOMAIN_LIST);*/

			// create EdivateLearn object with form Data
            EdivateLearn edivateLearn = createEdivateLearnObject(request, "school", request.isFullSubscription(), null, null);
            domainIds = domainService.createDomain(token, domains, edivateLearn, request.getCourseCatalogs(), DomainType.SCHOOL);

			if ( CollectionUtils.isEmpty( domainIds ) ) {
				log.debug("Failed to create new school domain " + request.getName() );
				success = Boolean.FALSE;
				return success;
			}

			domainId = domainIds.get(0).toString();

			// step 2 create admin user for newly created District
			log.debug("Creating administrator user for domain " + request.getName() + " with domain id " + domainId);
			success &= persistSchool(token, request, domainId);
			log.debug("Created new administrator user for domain " + request.getName() + " with domain id " + domainId);

			// step 3 copy resources from district template to newly created
			// domain
			log.debug("Copy resources to new domain " + request.getName() + " with domain id " + domainId);
			success &= resourceService.copyResources(token, properties.getTemplateSchoolDomainId(), domainId);
			log.debug("Copied resource to new domain " + request.getName() + " with domain id " + domainId);

			// step 4 copy courses from one domain to another
			log.debug("Copy courses to new domain " + request.getName());
			success &= courseService.copyCourses(token, request, domainId);
			log.debug("Copied courses to new domain " + request.getName() + " with domain id " + domainId);

			// Step 5: subscribes to courses or provider domains
			log.debug("Creating subscriptions to new domain " + request.getName());
			success &= subscriptionService.createSchoolDomainSubscription(token, request, domainId);
			log.debug("Creating subscriptions to new domain " + request.getName() + " with domain id " + domainId);

			if (!success) {
				throw new Exception("Unable to create domain " + request.getName() + " with domain id " + domainId
						+ " one or more steps failed rolling back");
			}

			log.debug("Completed createSchoolDomain ");

		} catch (DomainDuplicateException  | DomainNameDuplicateException e) {
			log.error("Error creating School domain with duplicate domain login prefix or name  "  + request.getLoginPrefix() , e);
			throw e;
		} catch (UserDuplicateException | Exception e) {
			log.error("Error creating School domain with user " + request.getName(), e);
			rollbackDistrictDomain(token, domainIds.get(0).toString());
			success = Boolean.FALSE;
		}

		log.debug("Exiting createSchool ");
		return success;
	}

	/**
	 * Updates School domains
	 * 
	 * @param token
	 *            DLAP authentication token
	 * @param request
	 *            Edit School domain request pojo
	 * @throws DomainNameDuplicateException
	 * @throws AvailabilityLicenseException
	 */
	@Override
	public boolean editSchool(String token, EditSchoolDomainRequest request)
			throws DomainNameDuplicateException, AvailabilityLicenseException {

		log.debug("Starts editSchool ");

		boolean success = Boolean.TRUE;
		try {
			DomainResponse response = domainService.findDomain(token, request.getDomainId());
			Customization customization = domainService.getDomainData(token, request.getDomainId());
			EdivateLearn edivateLearn = null;
			boolean domainChanged = false;
//			List<String> catalogIds = null;
			/* If district domain has not been changed */
			if (request.getDistrictDomainId() == Long.parseLong(response.getParentid())) {
				if (request.getNumbersOfLicense() != Integer
						.parseInt(customization.getEdivatelearn().getNooflicense() != null
								? customization.getEdivatelearn().getNooflicense()
								: "0")) { /*
											 * If "number of license" has not
											 * been changed
											 */
					log.debug("Getting availability of license of " + request.getName());
					success &= domainService.getAvailabilityOfLicense(token, request, request.getDomainId().toString());
					if (!success) {
						throw new AvailabilityLicenseException(
								"Unable to edit domain " + request.getName() + " with domain id "
										+ request.getDomainId() + " because of unavailability of license.");
					}
				}
			} else {
				log.debug("Getting availability of license of " + request.getName());
				success &= domainService.getAvailabilityOfLicense(token, request, request.getDomainId().toString());

				if (!success) {
					throw new AvailabilityLicenseException("Unable to edit domain " + request.getName()
							+ " with domain id " + request.getDomainId() + " because of unavailability of license.");
				}
				domainChanged = true;
			}

			// step 1 create domain with null data
			log.debug("Updating school domain " + request.getName());
			/*catalogIds = getSubscriptionsForDomain(token, request.getDistrictDomainId().toString())
					.get(Constants.CATALOG_DOMAIN_LIST);*/
			
			String subscriptionStartDate = dateUtil.toMDYFormat();
			String subscriptionEndDate = dateUtil.toMDYFormat( dateUtil.addDays( subscriptionStartDate, properties.getDefaultSubscriptionInDays() ) );
			
			if( customization != null && customization.getEdivatelearn() != null ) {
				subscriptionStartDate = customization.getEdivatelearn().subscriptionstartdate;
				subscriptionEndDate = customization.getEdivatelearn().getSubscriptionenddate();
			}
			// create EdivateLearn object with form Data
		    edivateLearn = createEdivateLearnObject(request,"school", false, subscriptionStartDate, subscriptionEndDate);

			// step 2 update the domain
			List<Domain> domains = mapToObject.mapToDomains(request);
			domains.get(0).setDomainId(String.valueOf(request.getDomainId()));
			log.debug("Updating the domain with name " + request.getName() + " with domainId " + request.getDomainId());
            success &= domainService.updateDomain(token, domains, edivateLearn, request.getCourseCatalogs(), DomainType.SCHOOL );
			log.debug("Updated the domain with name " + request.getName() + " with domainId " + request.getDomainId());

			// step 3 update courses from one domain to another
			/*log.debug("Deleting the courses for domain with name " + request.getName() + " with domainId " + request.getDomainId());
			success &= courseService.deleteAllCoursesOfDomain(token, request.getDomainId().toString());
			log.debug("Deleted the courses for domain with name " + request.getName() + " with domainId " + request.getDomainId());*/
			
			log.debug("Copy courses to new domain " + request.getName());
			success &= courseService.copyCourses(token, request, request.getDomainId().toString());
			log.debug("Copied courses to new domain " + request.getName() + " with domain id " + request.getDomainId());

			// step 4 update subscription
			// creating new subscription for the new district and
			// remove the old district subscription 
			// if the school is moved to a different domain
			if ( domainChanged ) {
				success &= subscriptionService.deleteSubscription(token, request.getDomainId().toString() );
				log.debug("Deleted subscriptions for school domain with name " + request.getName() + " with old domainId " + response.getParentid() );
				
				log.debug("Creating subscriptions to new domain " + request.getName());
				success &= subscriptionService.createSchoolDomainSubscription(token, request, request.getDomainId().toString());
				log.debug("Updated subscriptions for domain name " + request.getName() + " with domain id " + request.getDomainId());
			}

			School school = mapToObject.mapToSchoolForEditing(request);
			log.debug("Saving district domain record ");
			school = districtService.updateSchool(request.getDistrictDomainId(), school);
			if (school != null) {
				log.debug("Saved school domain record ");
				success &= Boolean.TRUE;
			}

			if (!success) {
				throw new Exception("Unable to edit domain " + request.getName() + " with domain id " + request.getDomainId() + " one or more steps failed.");
			}

			log.debug("Completed domain updation for domain name " + request.getName() + " with domain id " + request.getDomainId() + "successfully");
		} catch (DomainNameDuplicateException e) {
			log.error("Error updating School domain with duplicate domain name  " + request.getName() + " " + e);
			throw e;
		} catch (Exception e) {
			log.error("Error editing School domain  " + request.getName() + " " + e);
			success = Boolean.FALSE;
		}

		log.debug("Exiting editSchool ");
		return success;
	}

	/**
	 * @param token
	 * @param domainId
	 * @return
	 */
	@Override
	public Map<String, List<String>> getSubscriptionsForDomain(String token, String domainId) {
		log.debug("Fetching  subscription for domain with id " + domainId);

		Map<String, List<String>> providerMap = new HashMap<>();
		List<String> providerList = new ArrayList<>();
		List<String> courseList = new ArrayList<>();
		List<CourseResponse> courseResponseList = new ArrayList<>();
		
		courseResponseList=courseService.getAllCoursesFromDomain(token, domainId);
		
		for (CourseResponse courseResponse : courseResponseList) {
			courseList.add(courseResponse.getBaseid());
		}
		
		List<String> allProviderIDList = domainService.findProviderIdsList(token);
		for (String providerId : allProviderIDList) {
			List<String> cList = courseService.getAllCourseIdsForProvider(token, providerId);
			if (!Collections.disjoint(cList, courseList))
				providerList.add(providerId);
		}
		providerList = providerList.stream().distinct().collect(Collectors.toList());

		providerMap.put(Constants.CATALOG_DOMAIN_LIST, providerList);
		providerMap.put(Constants.COURSE_LIST, courseList);

		return providerMap;
	}
	
	/**
	 * @param token
	 * @param domainId
	 * @return
	 */
	@Override
	public Map<String, List<String>> getSubscriptionsForSchool(String token, String domainId) {
		log.debug("Fetching  subscription for domain with id " + domainId);

		Map<String, List<String>> providerMap = new HashMap<>();
		List<String> providerList = new ArrayList<>();
		List<String> schoolCourseList = new ArrayList<>();
		List<CourseResponse>  districtCoursesResponse = new ArrayList<>();
		List<String> courseList = new ArrayList<>();

		List<SubscriptionResponse> subResponse = subscriptionService.getSubscriptionsForDomain(token, domainId);

		if (subResponse.size() == 0) {
			return providerMap;
		}

		for (SubscriptionResponse s : subResponse) {
			districtCoursesResponse = courseService.getAllCoursesFromDomain(token, s.getEntityid());
		}
		
		schoolCourseList = courseService.getAllCoursesFromDomain(token, domainId).stream().map(CourseResponse::getBaseid).collect(Collectors.toList());
		
		for (CourseResponse response : districtCoursesResponse) {
			if (schoolCourseList.contains(response.getId())) {
				courseList.add(response.getBaseid());			
			} 
		}
		
		List<String> allProviderIDList = domainService.findProviderIdsList(token);
		for (String providerId : allProviderIDList) {
			List<String> cList = courseService.getAllCourseIdsForProvider(token, providerId);
			if (!Collections.disjoint(cList, courseList))
				providerList.add(providerId);
		}

		providerMap.put(Constants.CATALOG_DOMAIN_LIST, providerList);
		providerMap.put(Constants.COURSE_LIST, courseList);

		return providerMap;
	}

}
