package com.sinet.gage.provision.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinet.gage.dlap.clients.DlapUsersClient;
import com.sinet.gage.dlap.entities.UserRequest;
import com.sinet.gage.dlap.exception.DlapClientExcetion;
import com.sinet.gage.dlap.exception.UserDuplicateException;
import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.service.UserService;
import com.sinet.gage.provision.util.PasswordGenerator;

/**
 * @author Team Gage
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	DlapUsersClient dlapUsersClient;

	@Autowired
	AppProperties properties;


	/**
	 * Creates a new Admin user in the Brainhoney domain
	 * 
	 * @param domainId
	 *            id of the domain where new admin user will be created
	 * @return boolean outcome of the dlap api call
	 * @throws DlapClientExcetion
	 */
	@Override
	public UserRequest createAdminUser( String token, String domainId) throws UserDuplicateException {
		UserRequest user = new UserRequest(properties.getDefaultAdminUserName(), PasswordGenerator.builder().generate(),
				null, null, properties.getDefaultAdminFirstName(), properties.getDefaultAdminLastName(), null, domainId,
				properties.getDefaultAdminUserName(), "0", properties.getDefaultAdminFlags());
		String userId = "";
		try {
			userId = dlapUsersClient.createAdminUserInADomain( token, user);
			user.setUserId(userId);
		} catch (UserDuplicateException e) {
			LOGGER.error("Duplicate user Exception in createAdminUser");
			throw e;
		}
		return user;
	}

}
