package com.sinet.gage.provision.service;

/**
 * The Resource service for copying resources between 
 * domains 
 * 
 * @author Team Gage
 *
 */
public interface ResourceService {
	
	/**
	 * Copies all resources from source to destination domain 
	 * 
	 * @param token Dlap authentication toekn
	 * @param sourceDomainId Source domain id from where resources to be copied
	 * @param destinationDomainId Destination domain id to where resources will be copied
	 * @return This return the outcome of the operation
	 */
	public boolean copyResources(String token, String sourceDomainId, String destinationDomainId );

}
