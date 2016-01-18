package com.sinet.gage.provision.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinet.gage.dlap.clients.DlapResourceClient;
import com.sinet.gage.provision.service.ResourceService;

/**
 * The Resource service implementation for copying resources between 
 * domains
 * @author Team Gage
 *
 */
@Service
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	DlapResourceClient resourceClient;
	
	/**
	 * Copies all resources from source to destination domain 
	 * 
	 * @param token Dlap authentication toekn
	 * @param sourceDomainId Source domain id from where resources to be copied
	 * @param destinationDomainId Destination domain id to where resources will be copied
	 * @return This return the outcome of the operation
	 */
	@Override
	public boolean copyResources(String token, String sourceDomainId, String destinationDomainId) {
		return resourceClient.copyResource(token, sourceDomainId, destinationDomainId);
	}

}
