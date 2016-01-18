package com.sinet.gage.provision.controller;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sinet.gage.provision.service.CourseService;
import com.sinet.gage.provision.service.WelcomeLetterService;

/**
 * Rest controller for Welcome letter
 * 
 * @author Team Gage
 *
 */
@RestController
@RequestMapping(value="/welcome")
public class WelcomeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeController.class);
	
	@Autowired
	WelcomeLetterService welcomeLetterService;
	
	@Autowired
	CourseService courseService;
	
	/**
	 * Will generate Welcome Letter in .doc format
	 * 
	 * @return Binary content of welcome letter
	 * @throws Exception 
	 */	
	@RequestMapping(value="/letter/{domainId}/{username}", method=RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.wordprocessingml.document", consumes=MediaType.ALL_VALUE)
	public ResponseEntity<InputStreamResource> generateWelcomeLetter(@RequestHeader(value= "token", required=false)String token, @PathVariable("domainId") Long domainId, @PathVariable("username") String userName) throws Exception{
		
		LOGGER.debug("Enter into [generateWelcomeLetter]");
		
		List<String> courses = courseService.getAllCourseNamesForDomain( token, domainId.toString());
		
		InputStream contentStream = welcomeLetterService.createWelcomeLetter( domainId, userName, courses );
		
		return ResponseEntity
	            .ok()
	            .contentLength(contentStream.available())
	            .header("Content-Disposition", "attachment; filename=welcome_letter.doc").contentType(
	                    MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
	            .body(new InputStreamResource(contentStream));
	}
	
}
