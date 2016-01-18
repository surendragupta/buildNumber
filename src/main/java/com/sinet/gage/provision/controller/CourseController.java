package com.sinet.gage.provision.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinet.gage.dlap.entities.CourseResponse;
import com.sinet.gage.provision.model.Message;
import com.sinet.gage.provision.model.MessageUtil;
import com.sinet.gage.provision.service.CourseService;
import com.sinet.gage.provision.util.MessageConstants;

/**
 * Rest controller for Course
 * 
 * @author Team Gage
 *
 */
@RestController
@RequestMapping("/course")
public class CourseController {	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
	
	@Autowired
	MessageUtil messageUtil;
	
	@Autowired
	CourseService courseService;
	
	/**
	 * @Param domainId
	 * @return Message
	 */
	@RequestMapping(value = "/list/{domainId}", method = RequestMethod.GET)
	@ResponseBody
	public Message getAllCoursesForDomains(@RequestHeader(value="token", required=false)String token, @PathVariable("domainId") String domainId) {	
		LOGGER.debug("Enter into [getAllCoursesFromDomian]");
		List<CourseResponse> listOfCourse = courseService.getAllCoursesFromDomain(token,domainId);
		if (!listOfCourse.isEmpty()) {
			LOGGER.debug("Domain have list of courses");
			return messageUtil.createMessageWithPayload(MessageConstants.DOMAIN_COURSE_FOUND, listOfCourse,
					Collections.singletonMap("dataModels", "domain"), CourseResponse.class);
		} else {
			LOGGER.debug("No course have found for domains");
			return messageUtil.createMessage(MessageConstants.DOMAIN_COURSE_NOTFOUND, Boolean.FALSE, domainId);			
		}
	}
	
	
	
	/**
	 * @param token
	 * @param domainId
	 * @param districtDomainId
	 * @return
	 */
	@RequestMapping(value = "/list/{domainId}/{districtDomainId}", method = RequestMethod.GET)
	@ResponseBody
	public Message getAllCoursesForDistrictDomains(@RequestHeader(value = "token", required = false) String token,
			@PathVariable("domainId") String domainId, @PathVariable("districtDomainId") String districtDomainId) {
		LOGGER.debug("Enter into [getAllCoursesForDistrictDomains]");
		List<CourseResponse> listOfdistrictCourse = courseService.getAllCoursesFromDomain(token, districtDomainId);
		List<CourseResponse> listOfCourse = courseService.getAllCoursesFromDomain(token, domainId);
		List<CourseResponse> filteredCourse = new ArrayList<CourseResponse>();

		List<String> listOfCourseBaseIds = listOfdistrictCourse.stream()
											.map(CourseResponse::getBaseid)
											.collect(Collectors.toList());
		
		for ( CourseResponse course : listOfCourse ) {
			if ( listOfCourseBaseIds.contains( course.getId() ) ) {
				filteredCourse.add(course);
			}
		}
		
		if ( !CollectionUtils.isEmpty( filteredCourse ) ) {
			LOGGER.debug("Filtered domain courses size : " + filteredCourse.size());
			return messageUtil.createMessageWithPayload(MessageConstants.DOMAIN_COURSE_FOUND, filteredCourse, Collections.singletonMap("dataModels", "domain"), CourseResponse.class);
		}

		LOGGER.debug("No course have found for domains");
		return messageUtil.createMessage(MessageConstants.DOMAIN_COURSE_NOTFOUND, Boolean.FALSE, domainId);
	}

	@RequestMapping(value = "/copycourse/{sourceDomianId}/{destinationDomianId}", method=RequestMethod.GET )
	@ResponseBody
	public Message copyAllCoursesFromOneDomainToAnother(@RequestHeader(value = "token" ,required=false) String token, @PathVariable("sourceDomianId") String sourceDomianId, @PathVariable("destinationDomianId") String destinationDomianId){
		LOGGER.debug("Enter into [copyAllCoursesFromOneDomainToAnother]");
		boolean success = courseService.copyAllCourses(token, sourceDomianId, destinationDomianId,"DerivativeChildCopy",null,null);
		String code = success?MessageConstants.DOMAIN_COURSE_COPIED:MessageConstants.DOMAIN_COURSE_NOTCOPIED;
		return messageUtil.createMessage(code, success, "administrator");
	}
}
