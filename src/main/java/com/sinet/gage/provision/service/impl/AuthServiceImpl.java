package com.sinet.gage.provision.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinet.gage.dlap.clients.DlapAuthenticationClient;
import com.sinet.gage.dlap.entities.LoginResponse;
import com.sinet.gage.provision.model.LoginRequest;
import com.sinet.gage.provision.service.AuthService;

/**
 * Authentication service to authenticate user and logout the 
 * current login user Brainhoney session by integration with 
 * DLAP API client  
 * 
 * @author Team Gage
 *
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	@Autowired
	private DlapAuthenticationClient authenticationClient;
	
	/**
	 * Authenticates the login user request against DLAP API
	 * 
	 * @param request This is the login request
	 * @return Returns the login response from DLAP client
	 */
	@Override
	public LoginResponse authencateUser(LoginRequest request) {
		LOGGER.debug("Authenticating user " + request.getUserName());
		return authenticationClient.loginUser(request.getUserName(), request.getUserPassword());
	}

	/**
	 * Logout the current User via DLAP API call
	 * 
	 * @return The outcome of the DLAP API call
	 */
	@Override
	public boolean logoutUser() {
		LOGGER.debug("Logging out currently login user");
		return authenticationClient.logoutUser();
	}

	public void setAuthenticationClient(DlapAuthenticationClient authenticationClient) {
		this.authenticationClient = authenticationClient;
	}

}
