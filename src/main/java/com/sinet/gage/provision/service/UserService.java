package com.sinet.gage.provision.service;

import com.sinet.gage.dlap.entities.UserRequest;
import com.sinet.gage.dlap.exception.UserDuplicateException;

/**
 * @author Team Gage
 *
 */
public interface UserService {

	/**
	 * Inserts a new admin user into the Brainhoeny domain
	 * 
	 * @param user
	 */
	UserRequest createAdminUser( String token, String domainId) throws UserDuplicateException;

}
