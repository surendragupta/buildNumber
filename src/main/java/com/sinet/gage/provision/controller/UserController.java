package com.sinet.gage.provision.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinet.gage.dlap.entities.CourseResponse;
import com.sinet.gage.dlap.entities.DomainRightsResponse;
import com.sinet.gage.dlap.entities.LoginResponse;
import com.sinet.gage.provision.GetToken;
import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.model.LoginRequest;
import com.sinet.gage.provision.model.Message;
import com.sinet.gage.provision.model.MessageUtil;
import com.sinet.gage.provision.model.User;
import com.sinet.gage.provision.service.AuthService;
import com.sinet.gage.provision.service.RightsService;
import com.sinet.gage.provision.service.UserService;
import com.sinet.gage.provision.util.CollectorUtils;
import com.sinet.gage.provision.util.MessageConstants;

/**
 * Rest controller for User resource
 * 
 * @author Team Gage
 *
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	MessageUtil messageUtil;

	@Autowired
	UserService userService;

	@Autowired
	AuthService authService;

	@Autowired
	RightsService rightsService;
	
	@Autowired
	AppProperties properties;

	/**
	 * Authenticate a User to the DLAP API
	 * 
	 * @param User
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Message loginUser(@Valid @RequestBody LoginRequest jsonUser) {
		LOGGER.debug("Enter into [loginUser]");
		LoginResponse loginResponse = authService.authencateUser(jsonUser);

		if (loginResponse != null) {
			LOGGER.debug("User " + jsonUser.getUserName() + " authenticated");
			User user = new User();
			user.setUserId(Long.parseLong(loginResponse.getUserid()));
			user.setEntities("D");
			user.setUsername(jsonUser.getUserName());
			user.setPassword(jsonUser.getUserPassword());
		
			String token=	new GetToken().getToken(jsonUser.getUserName());
//			DomainRightsResponse matchedDomainRight = rightsService.getUserRightForDomain(user , Long.parseLong(loginResponse.getDomainid()));
			List<DomainRightsResponse> domainRightsList = rightsService.getUserRightsForDomains(user);
			DomainRightsResponse matchedDomainRight = domainRightsList.stream()
					.filter( right -> right.domainid.equals(loginResponse.getDomainid().toString()))
					.collect(CollectorUtils.single());
			if( matchedDomainRight != null) {
				List<String> domainRightsIds = domainRightsList.stream().map(DomainRightsResponse::getDomainid).collect(Collectors.toList());
				if (domainRightsIds.contains(properties.getCustomerDomainId()) && 
						domainRightsIds.contains(properties.getCourseCatalogDomainId())) {	
					// replacing the multiple data default properties names
					Map<String, String> renameMap = new HashMap<String, String>();
					renameMap.put("data1", "user");
					renameMap.put("data2", "domain");
					renameMap.put("data4", "token");
					return messageUtil.createMessageWithPayload(MessageConstants.LOGIN_AUTHENTICATED, loginResponse, matchedDomainRight, token, renameMap, LoginResponse.class);
				} else {
					LOGGER.debug("User " + jsonUser.getUserName() + " not authorized");
					return messageUtil.createMessage(MessageConstants.LOGIN_UNAVAILABLE_RIGHTS, Boolean.FALSE, jsonUser.getUserName());
				}
			} else {
				LOGGER.debug("User " + jsonUser.getUserName() + " not authorized");
				return messageUtil.createMessage(MessageConstants.LOGIN_UNAUTHORIZED, Boolean.FALSE, jsonUser.getUserName());				
			}
		} else {
			LOGGER.debug("User " + jsonUser.getUserName() + " authentication failed");
			return messageUtil.createMessage(MessageConstants.LOGIN_INVALID, Boolean.FALSE);			
		}
	}

	/**
	 * Logout current login user and ends Brianhoney session
	 * 
	 * @return Message the response of logout api call
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public Message logoutUser() {
		LOGGER.info("Enter into [loginUser]");
		boolean success = authService.logoutUser();
		String code = success ? MessageConstants.LOGOUT_SUCCESS : MessageConstants.LOGOUT_FAIL;
		return messageUtil.createMessage(code, success);
	}
}
