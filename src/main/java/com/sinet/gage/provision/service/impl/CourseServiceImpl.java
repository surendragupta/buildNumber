package com.sinet.gage.provision.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sinet.gage.dlap.clients.DlapCoursesClient;
import com.sinet.gage.dlap.entities.CopyCourseRequest;
import com.sinet.gage.dlap.entities.CourseResponse;
import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.model.DistrictDomainRequest;
import com.sinet.gage.provision.model.SchoolDomainRequest;
import com.sinet.gage.provision.service.CourseService;

/**
 * Course Service implementation for listing of course, copying courses between
 * domains
 * 
 * @author Team Gage
 */
@Service
public class CourseServiceImpl implements CourseService {

	public static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);

	@Autowired
	DlapCoursesClient coursesClient;
	
	@Autowired
	AppProperties properties;

	/**
	 * Return Message which will contain all courses specified for Domian ID
	 * 
	 * @param domainId
	 *            The domain Id for which courses are searched
	 * @return The list of courses
	 */
	@Override
	public List<CourseResponse> getAllCoursesFromDomain( String token, String domainId ) {
		return coursesClient.getAllCoursesFromADomain( token, domainId );
	}

	/**
	 * Search all course names for Domian ID
	 * 
	 * @param domainId
	 *            The domain Id for which courses are searched
	 * @return The list of course names
	 */
	@Override
	public List<String> getAllCourseNamesForDomain( String token, String domainId ) {
		List<CourseResponse> courseList = getAllCoursesFromDomain(token, domainId);
		return courseList.stream().map(CourseResponse::getTitle)
				.collect(Collectors.toList());
	}
	
	/**
	 * Copy all courses from one domain to another domain
	 * 
	 * @param token,
	 * @param sourceDomainId
	 * @param destinationDomainId
	 * @param action
	 * @param startDate
	 * @param endDate
	 */
	@Override
	public boolean copyAllCourses(String token, String sourceDomianId, String destinationDomainId, String action, String startDate, String endDate) {
		LOGGER.debug("Calling copyAllCoursesFromOneDomainToAnotherDomian Course Client");
		String numberOfDays = properties.getDefaultCourseSubscriptionInDays();
		String type = "Continuous";
		
		return coursesClient.copyAllCoursesFromOneDomainToAnother(token, sourceDomianId, destinationDomainId, action, type, null, null, numberOfDays);
	}

	/**
	 * 
	 * @param token,
	 * @param courseIds
	 * @param domainId
	 * @param action      
	 */
	@Override
	public boolean copySpecificCourses( String token, List<String> courseIds, String domainId, String action ) {
		LOGGER.debug("Calling copySpecificCoursesFromOneDomainToAnotherDomian Course Client");
		String numberOfDays = properties.getDefaultCourseSubscriptionInDays();
		String type = "Continuous";
		
		CopyCourseRequest[] copyCourseRequests = new CopyCourseRequest[courseIds.size()];
		courseIds.stream()
		.map(courseId -> new CopyCourseRequest( domainId, courseId, action, type, null, null, null, numberOfDays ))
		.collect(Collectors.toList()).toArray( copyCourseRequests );

		return coursesClient.copySpecificCoursesFromOneDomainToAnother(token, copyCourseRequests);
	}

	/**
	 * 
	 * @param token
	 * @param courseIds
	 */
	@Override
	public boolean deleteCourses(String token, List<String> courcesIds) {
		return coursesClient.deleteCources(token, courcesIds);
	}
	
	/**
	 * 
	 * @param token
	 * @param domainId
	 * @return
	 */
	@Override
	public boolean deleteAllCoursesOfDomain(String token, String domainId) {
		List<CourseResponse> courseList = getAllCoursesFromDomain(token, domainId);
		List<String> courseIds = courseList.stream().map(CourseResponse::getId).collect(Collectors.toList());
		LOGGER.debug( "Found courses " + courseList.size() + " to be deleted for domain with id " + domainId );
		return deleteCourses(token, courseIds);
	}
	
	/**
	 * 
	 * @param token
	 * @param providerId
	 * @return
	 */
	@Override
	public List<String> getAllCourseIdsForProvider(String token, String providerId) {
		List<CourseResponse> courseResponseList = getAllCoursesFromDomain(token, providerId);
		return courseResponseList.stream().map(CourseResponse::getId).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param token
	 * @param domainData
	 * @param domainId
	 * @return
	 */
	@Override
	public boolean copyCourses(String token, DistrictDomainRequest domainData, String domainId) {
		boolean success = Boolean.TRUE;
		String action = "DerivativeChildCopy";

		List<CourseResponse> listOfCourse = getAllCoursesFromDomain(token,domainId);
		List<String> requestBaseIdList = new ArrayList<>();
		
		requestBaseIdList.addAll(domainData.getCourseSelection());
		
		for (CourseResponse response : listOfCourse) {
			if (requestBaseIdList.contains(response.getBaseid())) {
				// Preparing for only insertion
				requestBaseIdList.remove(response.getBaseid());
			}
		}		
		
		LOGGER.debug("Copying courses of " + domainData.getCourseSelection().size() + "  providers ");
		if (!CollectionUtils.isEmpty(requestBaseIdList)) {
			success = copySpecificCourses(token, requestBaseIdList, domainId, action);
		}		
		
		return success;
	}
	
	/**
	 * 
	 * @param token
	 * @param request
	 * @param domainId
	 * @return
	 */
	@Override
	public boolean copyCourses(String token, SchoolDomainRequest domainData, String domainId) {		
		boolean success = Boolean.TRUE;
		String action = "DerivativeChildCopy";
		
		// copy all course from one domain to another
		if (!CollectionUtils.isEmpty(domainData.getCourseCatalogs())) {
			LOGGER.debug("Copying courses of " + domainData.getCourseCatalogs().size() + "  providers ");
			LOGGER.debug("Copying all courses of domains " + domainData.getCourseCatalogs() + " with mode "
					+ action + " and end date ");
			
			List<CourseResponse> districtCourseList = districtAllCourses(token, domainData.getDistrictDomainId().toString());
			List<CourseResponse> schoolCourseList = districtAllCourses(token, domainId);
			List<String> requestedCoursesId = new ArrayList<>();
			
			for (CourseResponse response : districtCourseList) {
				if (domainData.getCourseSelection().contains(response.getBaseid())) {
					requestedCoursesId.add(response.getId());
				}
			}
			
			for (CourseResponse response : schoolCourseList) {
				if (requestedCoursesId.contains(response.getBaseid())) {
					// Preparing for only insertion
					requestedCoursesId.remove(response.getBaseid());
				}
			}
			
			LOGGER.debug("Copying courses of " + domainData.getCourseSelection().size() + "  providers ");
			if (!CollectionUtils.isEmpty(requestedCoursesId)) {
				success = copySpecificCourses(token, requestedCoursesId, domainId, action);
			}
		}
		
		
		return success;
	}
	
	/*private List<String> courseToAddInSchool(List<String> districtCoursesBaseId,
			List<CourseResponse> catalogsCoursesId) {
		
		List<String> coursesToAddInSchool = new ArrayList<>();
		for (CourseResponse c : catalogsCoursesId)
			if (!districtCoursesBaseId.stream().anyMatch(
					str -> str.trim().equalsIgnoreCase(c.getId()))) {
				coursesToAddInSchool.add(c.getId());
			}

		return coursesToAddInSchool;
	}*/

	private List<CourseResponse> districtAllCourses(String token, String domainId) {
		List<CourseResponse> courses = getAllCoursesFromDomain(token, domainId);
		return courses;
	}
	

}
