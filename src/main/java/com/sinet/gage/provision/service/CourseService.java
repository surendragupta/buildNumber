package com.sinet.gage.provision.service;

import java.util.List;

import com.sinet.gage.dlap.entities.CourseResponse;
import com.sinet.gage.provision.model.DistrictDomainRequest;
import com.sinet.gage.provision.model.EditSchoolDomainRequest;
import com.sinet.gage.provision.model.SchoolDomainRequest;

/**
 * @author Team Gage
 *
 */
public interface CourseService {
	
	/**
	 * Returns all courses for domain
	 * 
	 * @return List<CourseResponse>
	 */
	public List<CourseResponse> getAllCoursesFromDomain(String token, String domianId);
	
	/**
	 * Returns all course names for domain
	 * 
	 * @return List<String>
	 */
	public List<String> getAllCourseNamesForDomain(String token, String domianId);
	
	/**
	 * Copy all courses for one domain to anther
	 * 
	 * @return Boolean
	 */
	public boolean copyAllCourses(String token, String sourceDomianId, String destinationDomainId, String action, String startDate, String endDate);

	public boolean copySpecificCourses(String token, List<String> courseIds, String domainId, String action);
	
	public boolean deleteCourses(String token, List<String> courcesIds);

	public boolean deleteAllCoursesOfDomain(String token, String domainId);

	public List<String> getAllCourseIdsForProvider(String token, String providerId);

	public boolean copyCourses(String token, DistrictDomainRequest domainData, String domainId);

	public boolean copyCourses(String token, SchoolDomainRequest domainData, String domainId);
	

}
