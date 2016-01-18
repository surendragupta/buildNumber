package com.sinet.gage.provision.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sinet.gage.dlap.clients.DlapSubscriptionClient;
import com.sinet.gage.dlap.entities.SubscriptionRequest;
import com.sinet.gage.dlap.entities.SubscriptionResponse;
import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.model.DistrictDomainRequest;
import com.sinet.gage.provision.model.SchoolDomainRequest;
import com.sinet.gage.provision.service.impl.SubscriptionServiceImpl;

/**
 * Test class for {@link com.sinet.gage.provision.service.SubscriptionService}
 * 
 * @author Team Gage
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SubscriptionServiceTest {

	private static final String FLAGS = "0";

	private static final String SUBSCRIPTION_END_DATE = "2016-09-22T00:00:02Z";

	private static final String SUBSCRIPTION_START_DATE = "2015-09-22T00:00:02Z";

	private static final String ENTITY_ID = "253647";

	private static final String DOMAIN_ID = "9101260";
	
	private static final String DOMAIN_ENTITY_TYPE = "D";
	
	private static final String TOKEN = "~SYC-ihGJ|ML0jX11juQd8d8BtTPAuWC2";

	private static final Long DISTRICT_DOMAIN_ID = 12345L;
	
	@InjectMocks
	private SubscriptionService mockSubscriptionService = new SubscriptionServiceImpl();
	
	@Mock
	private DlapSubscriptionClient mockSubscriptionClient;
	
	@Mock 
	private AppProperties mockAppProperties;
	
	private SubscriptionService spiedSubscriptionService; 
	
	/**
	 * setup test method data
	 */
	@Before
	public void setUp () {
		spiedSubscriptionService = spy(mockSubscriptionService);
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.impl.SubscriptionServiceImpl#createDistrictDomainSubscription(String, com.sinet.gage.provision.model.CreateDistrictRequest, String)}
	 */
	@Test
	public void testCreateSubscription() {
		List<Boolean> statusList = new ArrayList<>();
		statusList.add(Boolean.TRUE);
		
		when(mockSubscriptionClient.createSubscriptions(anyString(), anyListOf(SubscriptionRequest.class))).thenReturn(statusList);
		List<Boolean> resultList = mockSubscriptionService.createSubscription(TOKEN, createSubscriptionRequest());
		
		assertThat(resultList,everyItem(equalTo(Boolean.TRUE)));
		verify(mockSubscriptionClient, times(1)).createSubscriptions(anyString(), anyListOf(SubscriptionRequest.class));
		verifyZeroInteractions( mockSubscriptionClient );
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.impl.SubscriptionServiceImpl#createDistrictDomainSubscription(String, com.sinet.gage.provision.model.CreateDistrictRequest, String)}
	 *  
	 */
	@Test
	public void testCreateSubscriptionFailed () {
		List<Boolean> statusList = new ArrayList<>();
		statusList.add(Boolean.FALSE);
		
		when(mockSubscriptionClient.createSubscriptions(anyString(), anyListOf(SubscriptionRequest.class))).thenReturn(statusList);
		List<Boolean> resultList = mockSubscriptionService.createSubscription(TOKEN, createSubscriptionRequest());
	
		assertThat(resultList,everyItem(equalTo(Boolean.FALSE)));
		verify(mockSubscriptionClient, times(1)).createSubscriptions(anyString(), anyListOf(SubscriptionRequest.class));
		verifyZeroInteractions( mockSubscriptionClient );
	}

	/**
	 *  Test method for {@link com.sinet.gage.provision.service.impl.SubscriptionServiceImpl#deleteSubscription(String, String)}
	 *  
	 */
	@Test
	public void testDeleteSubscriptions () {
		when(mockSubscriptionClient.deleteSubscription(anyString(), anyListOf(SubscriptionRequest.class))).thenReturn(Boolean.TRUE);
		Boolean status = mockSubscriptionService.deleteSubscriptions(TOKEN, createSubscriptionRequest());
		
		assertTrue(status);
		verify(mockSubscriptionClient,times(1)).deleteSubscription(anyString(), anyListOf(SubscriptionRequest.class));
		verifyZeroInteractions( mockSubscriptionClient );
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.impl.SubscriptionServiceImpl#deleteSubscription(String, String)}
	 *  
	 */
	@Test
	public void testDeleteSubscriptionsFailed () {
		when(mockSubscriptionClient.deleteSubscription(anyString(), anyListOf(SubscriptionRequest.class))).thenReturn(Boolean.FALSE);
		Boolean status = mockSubscriptionService.deleteSubscriptions(TOKEN, createSubscriptionRequest());
		
		assertFalse(status);
		verify(mockSubscriptionClient,times(1)).deleteSubscription(anyString(), anyListOf(SubscriptionRequest.class));
		verifyZeroInteractions( mockSubscriptionClient );
	}

	/**
	 *  Test method for {@link com.sinet.gage.provision.service.impl.SubscriptionServiceImpl#deleteSubscription(String, String)}
	 *  
	 */
	@Test
	public void testDeleteSubscription () {
		List<SubscriptionResponse> subscriptionList = new ArrayList<>();
		SubscriptionResponse response = new SubscriptionResponse(DOMAIN_ID, ENTITY_ID, DOMAIN_ENTITY_TYPE, SUBSCRIPTION_START_DATE, SUBSCRIPTION_END_DATE, FLAGS, FLAGS, null, null, null, null, null);
		subscriptionList.add(response);
		
		doReturn(subscriptionList).when( spiedSubscriptionService ).getSubscriptionsForDomain(anyString(), anyString());
		doReturn(Boolean.TRUE).when( spiedSubscriptionService ).deleteSubscriptions(anyString(), anyListOf(SubscriptionRequest.class));
		
		boolean result = spiedSubscriptionService.deleteSubscription(TOKEN, DOMAIN_ID);
		
		assertTrue(result);
		verify( spiedSubscriptionService, times(1)).getSubscriptionsForDomain(anyString(), anyString());
		verify( spiedSubscriptionService, times(1)).deleteSubscriptions(anyString(), anyListOf(SubscriptionRequest.class));
	}	
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.impl.SubscriptionServiceImpl#deleteSubscription(String, String)}
	 *  
	 */
	@Test
	public void testDeleteSubscriptionNoSubscriptions () {
		List<SubscriptionResponse> subscriptionList = new ArrayList<>();
		
		doReturn(subscriptionList).when( spiedSubscriptionService ).getSubscriptionsForDomain(anyString(), anyString());
		doReturn(Boolean.TRUE).when( spiedSubscriptionService ).deleteSubscriptions(anyString(), anyListOf(SubscriptionRequest.class));
		
		boolean result = spiedSubscriptionService.deleteSubscription(TOKEN, DOMAIN_ID);
		
		assertTrue(result);
		verify( spiedSubscriptionService, times(1)).getSubscriptionsForDomain(anyString(), anyString());
		verify( spiedSubscriptionService, times(1)).deleteSubscriptions(anyString(), anyListOf(SubscriptionRequest.class));
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.impl.SubscriptionServiceImpl#getSubscriptionsForDomain(String, String)}
	 *  
	 */
	@Test
	public void testGetSubscriptionsForDomain () {
		List<SubscriptionResponse> subscriptionList = new ArrayList<>();
		SubscriptionResponse response = new SubscriptionResponse(DOMAIN_ID, ENTITY_ID, DOMAIN_ENTITY_TYPE, SUBSCRIPTION_START_DATE, SUBSCRIPTION_END_DATE, FLAGS, FLAGS, null, null, null, null, null);
		subscriptionList.add(response);
		when(mockSubscriptionClient.getAllSubscriptionFromASubscriber(anyString(), anyString())).thenReturn(subscriptionList);
		
		List<SubscriptionResponse> resultList = mockSubscriptionService.getSubscriptionsForDomain(TOKEN, DOMAIN_ID);
		
		assertThat(resultList, everyItem(hasProperty("subscriberid",is(DOMAIN_ID))));
		verify(mockSubscriptionClient,times(1)).getAllSubscriptionFromASubscriber(anyString(), anyString());
		verifyZeroInteractions( mockSubscriptionClient );
	}

	/**
	 *  Test method for {@link com.sinet.gage.provision.service.impl.SubscriptionServiceImpl#getSubscriptionsForDomain(String, String)}
	 *  
	 */
	@Test
	public void testGetSubscriptionsForDomainEmptyResponse () {
		List<SubscriptionResponse> subscriptionList = new ArrayList<>();
		when(mockSubscriptionClient.getAllSubscriptionFromASubscriber(anyString(), anyString())).thenReturn(subscriptionList);
		
		List<SubscriptionResponse> resultList = mockSubscriptionService.getSubscriptionsForDomain(TOKEN, DOMAIN_ID);
		
		assertThat(resultList, is(empty()));
		verify(mockSubscriptionClient,times(1)).getAllSubscriptionFromASubscriber(anyString(), anyString());
		verifyZeroInteractions( mockSubscriptionClient );
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.impl.SubscriptionServiceImpl#createSchoolDomainSubscription(String, com.sinet.gage.provision.model.SchoolDomainRequest, String)}
	 *  
	 */
	@Test
	public void testCreateSchoolSubscription () {
				
		List<Boolean> dlapResultList = new ArrayList<>();
		dlapResultList.add(Boolean.TRUE);		
		doReturn(dlapResultList).when(spiedSubscriptionService).createSubscription(anyString(), anyListOf(SubscriptionRequest.class));
		
		boolean result = spiedSubscriptionService.createSchoolDomainSubscription(TOKEN, createSchoolDomainRequest( DISTRICT_DOMAIN_ID ), ENTITY_ID);
		
		assertTrue(result);
		verify( spiedSubscriptionService,times(1)).createSubscription(anyString(), anyListOf(SubscriptionRequest.class));
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.impl.SubscriptionServiceImpl#createSchoolDomainSubscription(String, com.sinet.gage.provision.model.SchoolDomainRequest, String)}
	 *  
	 */
	@Test
	public void testCreateSchoolSubscriptionFailed () {
		
		List<Boolean> dlapResultList = new ArrayList<>();
		doReturn(dlapResultList).when(spiedSubscriptionService).createSubscription(anyString(), anyListOf(SubscriptionRequest.class));
		
		boolean result = spiedSubscriptionService.createSchoolDomainSubscription(TOKEN, createSchoolDomainRequest( DISTRICT_DOMAIN_ID ), ENTITY_ID);
		
		assertFalse(result);
		verify( spiedSubscriptionService,times(1)).createSubscription(anyString(), anyListOf(SubscriptionRequest.class));
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.impl.SubscriptionServiceImpl#createDistrictDomainSubscription(String, com.sinet.gage.provision.model.CreateDistrictRequest, String)}
	 *  
	 */
	@Test
	public void testCreateDistrictDomainSubscriptionForCatalogs () {
				
		List<Boolean> dlapResultList = new ArrayList<>();
		dlapResultList.add(Boolean.TRUE);		
		doReturn(dlapResultList).when(spiedSubscriptionService).createSubscription(anyString(), anyListOf(SubscriptionRequest.class));
		
		List<String> courseCatalogs = Arrays.asList("123456","1234567");
		List<String> courseSelection = new ArrayList<>();
		boolean result = spiedSubscriptionService.createDistrictDomainSubscription(TOKEN, createDistrictDomainRequest( DISTRICT_DOMAIN_ID, Boolean.TRUE, courseCatalogs, courseSelection ), ENTITY_ID);
		
		assertTrue(result);
		verify( spiedSubscriptionService,times(1)).createSubscription(anyString(), anyListOf(SubscriptionRequest.class));
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.impl.SubscriptionServiceImpl#createDistrictDomainSubscription(String, com.sinet.gage.provision.model.CreateDistrictRequest, String)}
	 *  
	 */
	@Ignore
	public void testCreateDistrictDomainSubscriptionForEmptyCatalogs () {
				
		List<Boolean> dlapResultList = new ArrayList<>();
		doReturn(dlapResultList).when(spiedSubscriptionService).createSubscription(anyString(), anyListOf(SubscriptionRequest.class));
		
		List<String> courseCatalogs = new ArrayList<>();
		List<String> courseSelection = new ArrayList<>();
		boolean result = spiedSubscriptionService.createDistrictDomainSubscription(TOKEN, createDistrictDomainRequest( DISTRICT_DOMAIN_ID, Boolean.TRUE, courseCatalogs, courseSelection ), ENTITY_ID);
		
		assertFalse(result);
		verify( spiedSubscriptionService,times(1)).createSubscription(anyString(), anyListOf(SubscriptionRequest.class));
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.impl.SubscriptionServiceImpl#createDistrictDomainSubscription(String, com.sinet.gage.provision.model.CreateDistrictRequest, String)}
	 *  
	 */
	@Test
	public void testCreateDistrictDomainSubscriptionForCourses () {
				
		List<Boolean> dlapResultList = new ArrayList<>();
		dlapResultList.add(Boolean.TRUE);		
		doReturn(dlapResultList).when(spiedSubscriptionService).createSubscription(anyString(), anyListOf(SubscriptionRequest.class));
		
		List<String> courseCatalogs = new ArrayList<>();
		List<String> courseSelection = Arrays.asList("34123456","341234567");
		boolean result = spiedSubscriptionService.createDistrictDomainSubscription(TOKEN, createDistrictDomainRequest( DISTRICT_DOMAIN_ID, Boolean.FALSE, courseCatalogs, courseSelection ), ENTITY_ID);
		
		assertTrue(result);
		verify( spiedSubscriptionService,times(1)).createSubscription(anyString(), anyListOf(SubscriptionRequest.class));
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.impl.SubscriptionServiceImpl#createDistrictDomainSubscription(String, com.sinet.gage.provision.model.CreateDistrictRequest, String)}
	 *  
	 */
	@Ignore
	public void testCreateDistrictDomainSubscriptionForEmptyCourses () {
				
		List<Boolean> dlapResultList = new ArrayList<>();
		doReturn(dlapResultList).when(spiedSubscriptionService).createSubscription(anyString(), anyListOf(SubscriptionRequest.class));
		
		List<String> courseCatalogs = new ArrayList<>();
		List<String> courseSelection = new ArrayList<>();
		boolean result = spiedSubscriptionService.createDistrictDomainSubscription(TOKEN, createDistrictDomainRequest( DISTRICT_DOMAIN_ID, Boolean.FALSE, courseCatalogs, courseSelection ), ENTITY_ID);
		
		assertFalse(result);
		verify( spiedSubscriptionService,times(1)).createSubscription(anyString(), anyListOf(SubscriptionRequest.class));
	}
	
	private DistrictDomainRequest createDistrictDomainRequest(Long districtDomainId, boolean fullSubscription, List<String> courseCatalogs, List<String> courseSelection) {
		DistrictDomainRequest request = new DistrictDomainRequest();
		request.setFullSubscription(fullSubscription);
		request.setCourseCatalogs(courseCatalogs);
		request.setCourseSelection(courseSelection);
		request.setSubscriptionStartDate("11-14-2015");
		request.setSubscriptionStartDate("11-15-2035");
		return request;
	}

	private SchoolDomainRequest createSchoolDomainRequest ( Long districtDomainId ) {
		SchoolDomainRequest request = new SchoolDomainRequest();
		request.setDistrictDomainId( districtDomainId );
		return request;
	}

	private List<SubscriptionRequest> createSubscriptionRequest() {
		List<SubscriptionRequest> subscriptionList = new ArrayList<>();
		SubscriptionRequest subscriptionRequest = new SubscriptionRequest(DOMAIN_ID, ENTITY_ID, SUBSCRIPTION_START_DATE, SUBSCRIPTION_END_DATE, FLAGS, FLAGS);
		subscriptionList.add(subscriptionRequest);
		return subscriptionList;
	}

}
