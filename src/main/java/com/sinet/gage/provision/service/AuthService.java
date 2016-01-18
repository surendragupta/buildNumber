package com.sinet.gage.provision.service;

import com.sinet.gage.dlap.entities.LoginResponse;
import com.sinet.gage.provision.model.LoginRequest;

/**
 * Authenticate service interface
 * 
 * @author Team Gage
 *
 */
public interface AuthService {

	/**
	 * Authenticate the specified user
	 * 
	 * @param request The login request 
	 * @return The login response
	 */
	LoginResponse authencateUser(LoginRequest request);
	
	/**
	 * Logout the currently logged in user
	 * 
	 * @return The outcome of api call
	 */
	boolean logoutUser();
}
