package com.sinet.gage.provision.service;

import java.util.List;

import com.sinet.gage.dlap.entities.SubscriptionRequest;
import com.sinet.gage.dlap.entities.SubscriptionResponse;
import com.sinet.gage.provision.model.DistrictDomainRequest;
import com.sinet.gage.provision.model.SchoolDomainRequest;

/**
 * The Subscription service creates or updates the subscription for the
 * specified subscriber to domains / courses
 * 
 * @author Team Gage
 *
 */
public interface SubscriptionService {

	/**
	 * Creates or updates the subscription for the specified subscriber
	 * 
	 * @param token
	 *            Dlap authentication token
	 * @param subscriberId
	 *            The ID of the user or domain to update a subscription for
	 * @param entityId
	 *            The ID of the domain or course to subscribe to
	 * @return This return the outcome of the operation
	 */
	public List<Boolean> createSubscription(String token, List<SubscriptionRequest> subscriptionList);
	
	/**
	 * Fetch the list of all subscription for a domain
	 * 
	 * @param token Dlap authnetication token
	 * @param domainId domain id for which subscriptions are searched
	 * @return list of subscription response
	 */
	public List<SubscriptionResponse> getSubscriptionsForDomain( String token, String domainId );
	
	/**
	 * Delete subscriptions for a domain 
	 * 
	 * @param token Dlap authentication token 
	 * @param subscriptionRequests list of subscription requests
	 * @return
	 */
	public boolean deleteSubscriptions(String token, List<SubscriptionRequest> subscriptionRequests);

	boolean deleteSubscription(String token, String domainId);

	boolean createDistrictDomainSubscription(String token, DistrictDomainRequest domainData, String domainId);

	boolean createSchoolDomainSubscription(String token, SchoolDomainRequest request, String domainId);
}