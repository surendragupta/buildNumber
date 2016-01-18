package com.sinet.gage.provision.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.sinet.gage.dlap.clients.DlapUsersClient;
import com.sinet.gage.dlap.entities.UserRequest;
import com.sinet.gage.dlap.exception.UserDuplicateException;
import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.service.impl.UserServiceImpl;

/**
 * Test class for {@link com.sinet.gage.provision.service.UserService}
 * 
 * @author Team Gage
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	private static final String DOMAIN_ID = "9101260";
	private static final String USER_ID = "1234";
	
	@InjectMocks
	private UserService mockUserService = new UserServiceImpl();
	
	@Mock
	private DlapUsersClient mockDlapUsersClient;
	
	@Mock
	AppProperties properties;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.UserServiceImpl#createAdminUser(String, String)} 
	 * 
	 * @throws UserDuplicateException
	 */
	@Test
	public void testCreateAdminUser() throws UserDuplicateException {
		when(mockDlapUsersClient.createAdminUserInADomain(anyString(), any(UserRequest.class))).thenReturn(USER_ID);
		UserRequest user = mockUserService.createAdminUser(null, DOMAIN_ID);
		assertEquals( USER_ID, user.getUserId() );
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.UserServiceImpl#createAdminUser(String, String)} 
	 * 
	 * @throws UserDuplicateException
	 */
	@Test( expected=UserDuplicateException.class )
	public void testCreateAdminUserException () throws UserDuplicateException {
		when(mockDlapUsersClient.createAdminUserInADomain( anyString(), any(UserRequest.class))).thenThrow(new UserDuplicateException("Duplicate user found with name administrator") );
		mockUserService.createAdminUser( null, DOMAIN_ID );
	}
}
