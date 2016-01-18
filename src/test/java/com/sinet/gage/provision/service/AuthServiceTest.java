package com.sinet.gage.provision.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sinet.gage.dlap.clients.DlapAuthenticationClient;
import com.sinet.gage.dlap.entities.LoginResponse;
import com.sinet.gage.provision.model.LoginRequest;
import com.sinet.gage.provision.service.impl.AuthServiceImpl;

/**
 * Test class for {@link com.sinet.gage.provision.service.AuthService}
 * 
 * @author Team Gage
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

	private LoginRequest request = new LoginRequest();
	
	@InjectMocks
	private AuthService mockAuthService = new AuthServiceImpl();
	
	@Mock
	private DlapAuthenticationClient mockAuthenticationClient;
	
	@Before
	public void setUp() {
		request.setUserName("administrator");
		request.setUserPassword("password");
	}
	

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.AuthServiceImpl#authencateUser(com.sinet.gage.provision.model.LoginRequest)}.
	 */
	@Test
	public final void testAuthencateUser() {
		
		LoginResponse response = new LoginResponse("1234", "administrator", "Admin", "User", "admin@canyonelementary.com", "4321", "Canyon Elementary", "ce-1234", "SYC-ihGJ|ML0jX11juQd8d8BtTPAuWC", "14");
		when(mockAuthenticationClient.loginUser(anyString(), anyString())).thenReturn(response );
		
		LoginResponse result = mockAuthService.authencateUser(request);

		assertEquals("administrator",result.getUsername());
		verify(mockAuthenticationClient,times(1)).loginUser(anyString(), anyString());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.AuthServiceImpl#authencateUser(com.sinet.gage.provision.model.LoginRequest)}.
	 */
	@Test
	public final void testAuthencateUserFailed () {
		when(mockAuthenticationClient.loginUser(anyString(), anyString())).thenReturn(null );
		
		LoginResponse result = mockAuthService.authencateUser(request);

		assertNull(result);
		verify(mockAuthenticationClient,times(1)).loginUser(anyString(), anyString());
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.AuthServiceImpl#logoutUser()}.
	 */
	@Test
	public final void testLogoutUser() {
		when( mockAuthenticationClient.logoutUser()).thenReturn(Boolean.TRUE);
		assertTrue(mockAuthService.logoutUser());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.AuthServiceImpl#logoutUser()}.
	 */
	@Test
	public final void testLogoutUserFailed () {
		when( mockAuthenticationClient.logoutUser()).thenReturn(Boolean.FALSE);
		assertFalse(mockAuthService.logoutUser());
	}

}
