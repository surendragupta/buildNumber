package com.sinet.gage.provision.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sinet.gage.dlap.clients.DlapSubscriptionClient;
import com.sinet.gage.dlap.entities.CourseResponse;
import com.sinet.gage.dlap.entities.SubscriptionRequest;
import com.sinet.gage.dlap.entities.SubscriptionResponse;
import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.model.DistrictDomainRequest;
import com.sinet.gage.provision.model.SchoolDomainRequest;
import com.sinet.gage.provision.service.SubscriptionService;
import com.sinet.gage.provision.util.DateUtil;

/**
 * @author Team.Gage
 *
 */
@Service("subscriptionService")
public class SubscriptionServiceImpl implements SubscriptionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
	private DateUtil dateUtil = new DateUtil();

	@Autowired
	DlapSubscriptionClient subscriptionClient;
	
	@Autowired
	AppProperties properties;

	/**
	 * Creates or updates the subscription for the specified subscriber
	 * 
	 * @param token
	 *            Dlap authentication token
	 * @param subscriptionList
	 *            containing subscriberId The ID of the user or domain to update
	 *            a subscription for and entityId The ID of the domain or course
	 *            to subscribe to
	 * @return This return the outcome of the operation
	 */
	@Override
	public List<Boolean> createSubscription(String token, List<SubscriptionRequest> subscriptionList) {
		return subscriptionClient.createSubscriptions(token, subscriptionList);
	}

	/**
	 * Delete subscriptions for a domain 
	 * 
	 * @param token Dlap authentication token 
	 * @param subscriptionRequests list of subscription requests
	 * @return
	 */
	@Override
	public boolean deleteSubscriptions(String token, List<SubscriptionRequest> subscriptionEntityIdList) {
		return subscriptionClient.deleteSubscription(token, subscriptionEntityIdList);
	}
	
	/**
	 * 
	 * @param token
	 * @param domainId
	 * @return
	 */
	@Override
	public boolean deleteSubscription(String token, String domainId) {
		LOGGER.debug("Fetching subscription for domain with id " + domainId);
		List<SubscriptionResponse> subscriptionResponse = getSubscriptionsForDomain(token,domainId);
		LOGGER.debug("Found subscriptions " +  subscriptionResponse.size() + " for domain with id " + domainId);

		List<SubscriptionRequest> subscriptionRequests = subscriptionResponse.stream().map(
				subsciptionId -> new SubscriptionRequest(domainId, subsciptionId.getEntityid(), null, null, null, null))
				.collect(Collectors.toList());
		return deleteSubscriptions(token, subscriptionRequests);
	}

	/**
	 * Fetch the list of all subscription for a domain
	 * 
	 * @param token Dlap authnetication token
	 * @param domainId domain id for which subscriptions are searched
	 * @return list of subscription response
	 */
	@Override
	public List<SubscriptionResponse> getSubscriptionsForDomain(String token, String domainId) {
		return subscriptionClient.getAllSubscriptionFromASubscriber(token, domainId);
	}
	
	/**
	 * Creates the domain subscription
	 * 
	 * @param token
	 *            DLAP API authentication token
	 * @param request
	 *            CreateDistrictRequest pojo contains domain details
	 * @param domainId
	 *            Id of the domain
	 * @return Successfully completed subscription API calls
	 */
	@Override
	public boolean createDistrictDomainSubscription(String token, DistrictDomainRequest request, String domainId) {
		boolean success = Boolean.FALSE;
		List<SubscriptionRequest> subscriptionList = new ArrayList<>();
		
		String startDate = dateUtil.subscriptionStartDate(request.getSubscriptionStartDate());
		String endDate = dateUtil.subscriptionEndDateFromStartDate( request.getSubscriptionStartDate(), request.getSubscriptionEndDate(), properties.getDefaultSubscriptionInDays() );
		
		List<SubscriptionResponse> existingSubscriptionList = getSubscriptionsForDomain(token, domainId);
		List<String> requestBaseIdList = new ArrayList<>(); 
		
		if (request.isFullSubscription()) {
			if (!CollectionUtils.isEmpty(request.getCourseCatalogs())) {
				LOGGER.debug("Creating subscption to course catalog domains of " + request.getCourseCatalogs().size() + "  providers ");
				requestBaseIdList.addAll(request.getCourseCatalogs());
			}
		} else {
			if (!CollectionUtils.isEmpty(request.getCourseSelection())) {
				LOGGER.debug("Creating subscption to " + request.getCourseCatalogs().size() + "  courses ");
				requestBaseIdList.addAll(request.getCourseSelection());
			}
		}
		
		for (SubscriptionResponse response : existingSubscriptionList) {
			if (requestBaseIdList.contains(response.getEntityid())) {
				// Preparing for only insertion
				requestBaseIdList.remove(response.getEntityid());
			} 
		}
		
		if (!CollectionUtils.isEmpty(requestBaseIdList)) {
			buildSubscriptionRequest( subscriptionList, domainId, requestBaseIdList, startDate, endDate );
			List<Boolean> results = createSubscription(token, subscriptionList);
			for (Boolean result : results) {
				success = result;
			}
		} else {
			success = true;
		}
		return success;
	}
	
	/**
	 * 
	 * @param token
	 * @param request
	 * @param domainId
	 * @return
	 */
	@Override
	public boolean createSchoolDomainSubscription(String token, SchoolDomainRequest request, String domainId) {
		boolean success = Boolean.FALSE;
		List<SubscriptionRequest> subscriptionList = new ArrayList<>();
		String startDate = dateUtil.toISODateFormat();
		String endDate = dateUtil.subscriptionEndDate( null,properties.getDefaultSubscriptionInDays() );

		SubscriptionRequest subscriptionRequest = new SubscriptionRequest(domainId,request.getDistrictDomainId().toString(), startDate, endDate, null, null);
		subscriptionList.add(subscriptionRequest);

		List<Boolean> results = createSubscription(token, subscriptionList);
		for (Boolean result : results) {
			success = result;
		}
		return success;
	}
	
	private void buildSubscriptionRequest(List<SubscriptionRequest> subscriptionList, String domainId, List<String> entityIds, String startDate, String endDate) {
		for( String entityId : entityIds ) {
			SubscriptionRequest subscriptionRequest = new SubscriptionRequest(domainId, entityId, startDate, endDate, null, null);
			subscriptionList.add(subscriptionRequest);
		}
	}
}
