package com.sinet.gage.provision.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sinet.gage.dlap.clients.DlapRightsClient;
import com.sinet.gage.dlap.entities.DomainRightsResponse;
import com.sinet.gage.provision.model.User;
import com.sinet.gage.provision.service.impl.RightsServiceImpl;

/**
 * Test class for {@link com.sinet.gage.provision.service.RightsService}
 *  
 * @author Team Gage
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RightsServiceTest {
	
	private static final String USER_SPACE = "ces-1233";

	private static final String DOMAIN_NAME = "Canyon Elementary School";

	private static final Long DOMAIN_ID = 9101265L;
	private static final Long ANOTHER_DOMAIN_ID = 9101267L;

	@InjectMocks
	private RightsService mockRightService = new RightsServiceImpl();
	
	@Mock
	private DlapRightsClient mockRightsClient;
	
	private RightsService spiedRightService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		spiedRightService = spy( mockRightService );
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.RightsServiceImpl#getUserRightsForDomains(com.sinet.gage.provision.model.User)}.
	 */
	@Test
	public void testGetUserRightsForDomains() {
		List<DomainRightsResponse> rights = new ArrayList<>();
		rights.add(createRight(DOMAIN_ID));
		doReturn(rights).when( spiedRightService).getUserRightsForDomains(any(User.class));
		
		DomainRightsResponse result = spiedRightService.getUserRightForDomain(createUser(), DOMAIN_ID);
		
		assertThat( result, hasProperty("domainid",equalTo(DOMAIN_ID.toString())));
		verify( spiedRightService ).getUserRightsForDomains(any(User.class));		
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.RightsServiceImpl#getUserRightsForDomains(com.sinet.gage.provision.model.User)}.
	 */
	@Test
	public void testGetUserRightsForDomainsNotFound () {
		List<DomainRightsResponse> rights = new ArrayList<>();
		rights.add(createRight(DOMAIN_ID));
		doReturn(rights).when( spiedRightService).getUserRightsForDomains(any(User.class));
		
		DomainRightsResponse result = spiedRightService.getUserRightForDomain(createUser(), DOMAIN_ID);
		
		assertThat( result, hasProperty("domainid",not(equalTo(ANOTHER_DOMAIN_ID.toString()))));
		verify( spiedRightService ).getUserRightsForDomains(any(User.class));		
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.RightsServiceImpl#getUserRightForDomain(com.sinet.gage.provision.model.User, java.lang.Long)}.
	 */
	@Test
	public void testGetUserRightForDomain() {
		List<DomainRightsResponse> rights = new ArrayList<>();
		rights.add(createRight(DOMAIN_ID));
		when( mockRightsClient.getActorRightsForDomain(anyString(), anyString(), anyString(), anyString())).thenReturn(rights );
		
		List<DomainRightsResponse> result = mockRightService.getUserRightsForDomains(createUser());
		
		assertThat(result,is(not(empty())));
		verify( mockRightsClient ).getActorRightsForDomain(anyString(), anyString(), anyString(), anyString());
	}
	
	private DomainRightsResponse createRight(Long domainId) {
		return new DomainRightsResponse(domainId.toString(), DOMAIN_NAME, USER_SPACE, USER_SPACE, null, null, 0);
	}

	private User createUser() {
		User user = new User();
		user.setUsername("administrator");
		user.setPassword("password");
		user.setUserId(123567L);
		user.setEntities("D");
		return user;
	}

}
