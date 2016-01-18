/**
 * 
 */
package com.sinet.gage.provision.service;

import java.util.List;

import com.sinet.gage.dlap.entities.DomainRightsResponse;
import com.sinet.gage.provision.model.User;

/**
 * @author Team Gage
 *
 */
public interface RightsService {

	/**
	 * Returns all user rights for domain
	 * 
	 * @return 
	 */
	List<DomainRightsResponse> getUserRightsForDomains(User user);
	
	/**
	 * 
	 * @param user
	 * @param domainId
	 * @return
	 */
	public DomainRightsResponse getUserRightForDomain(User user, Long domainId);

}
