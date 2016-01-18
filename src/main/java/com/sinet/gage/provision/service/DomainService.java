package com.sinet.gage.provision.service;

import java.util.List;

import com.sinet.gage.dlap.entities.Customization;
import com.sinet.gage.dlap.entities.DomainResponse;
import com.sinet.gage.dlap.entities.EdivateLearn;
import com.sinet.gage.dlap.exception.DomainDuplicateException;
import com.sinet.gage.dlap.exception.DomainNameDuplicateException;
import com.sinet.gage.provision.model.Domain;
import com.sinet.gage.provision.model.DomainType;
import com.sinet.gage.provision.model.SchoolDomainRequest;

/**
 * Domain service interface 
 * 
 * @author Team Gage
 *
 */
public interface DomainService {
	
	/**
	 * Get domain details searched by domainId
	 * 
	 * @param token authentication token for dlap api
	 * @param domainId domain id for searching domain
	 * @return domain response
	 */
	public DomainResponse findDomain( String token, Long domainId );
	
	/**
	 * Get domain customization data searched by domainId
	 * 
	 * @param token authentication token for dlap api
	 * @param domainId domain id for searching domain
	 * @return domain customization data
	 */
	public Customization getDomainData( String token, Long domainId );
	
	/**
	 * List all state domains under customer domain
	 * 
	 * @return List of domains
	 */
	public List<DomainResponse> findAllStateDomains(String token, int limit);
	
	/**
	 * List all content domains under course catalog
	 * 
	 * @return List of domains
	 */
	public List<DomainResponse> findAllContentDomains(String token, int limit);
	
	/**
	 * List all child domains under state domain
	 * @param domainId Parent domain Id
	 * @return List of domains
	 */
	public List<DomainResponse> findAllChildDomains(String token, String domainId, String queryString, String searchText, int limit);

	/**
	 * List all child domains under state domain
	 * @param domainId Parent domain Id
	 * @return List of domains
	 */
	public List<DomainResponse> findAllChildDomainsWithDomainData(String token, String domainId, String queryString, String searchText, int limit);

	
	/**
	 * Create domain
	 * 
	 * @return boolean
	 * @throws DomainNameDuplicateException 
	 */
	public List<Integer> createDomain(String token, List<Domain> domainRequest, EdivateLearn edivateLearn, List<String> domainIds, DomainType domainType ) throws DomainDuplicateException, DomainNameDuplicateException;
	
	/**
	 * Delete domain
	 * 
	 * @return boolean
	 */
	public boolean deleteDomain(String token, String domainId);
	
	/**
	 * Update domain
	 * 
	 * @return boolean
	 * @throws DomainNameDuplicateException 
	 */
	public boolean updateDomain(String token, List<Domain> domainUpdateList, EdivateLearn edivateLearn, List<String> catalogIds, DomainType domainType ) throws DomainNameDuplicateException;

	public boolean getAvailabilityOfLicense(String token, SchoolDomainRequest request, String existingDomainId);

	public List<String> findProviderIdsList(String token);
	
}
