package com.sinet.gage.provision.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinet.gage.dlap.clients.DlapRightsClient;
import com.sinet.gage.dlap.entities.DomainRightsResponse;
import com.sinet.gage.provision.model.User;
import com.sinet.gage.provision.service.RightsService;
import com.sinet.gage.provision.util.CollectorUtils;

/**
 * Actor, Entity right service on Domain, Course, and Section
 *  
 * @author Team Gage
 *
 */
@Service("rightsService")
public class RightsServiceImpl implements RightsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RightsServiceImpl.class);

	@Autowired
	private DlapRightsClient rightsClient;


	/**
	 * Get all rights for domain of the current User by its id
	 * 
	 * @param user
	 * @return Message
	 */
	@Override
	public List<DomainRightsResponse> getUserRightsForDomains(User user) {
		LOGGER.debug("Searching domain rights for user " + user.getUserId());
		return rightsClient.getActorRightsForDomain(user.getUsername(),
				user.getPassword(), String.valueOf(user.getUserId()), user.getEntities());
	}
	
	/**
	 * Find the domain right for user by domain id
	 * 
	 * @param user 
	 * @param domainId 
	 * @return The domain rights of the user
	 */
	@Override
	public DomainRightsResponse getUserRightForDomain( User user, Long domainId ) {
		LOGGER.debug("Searching domain rights for user " + user.getUserId() + " with domainId "+ domainId);
		List<DomainRightsResponse> listOfDomainRights = getUserRightsForDomains(user);
		return listOfDomainRights.stream()
		.filter( right -> right.domainid.equals(domainId.toString()))
		.collect(CollectorUtils.single());		
	}
}
