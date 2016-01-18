package com.sinet.gage.provision.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sinet.gage.dlap.entities.DomainRightsResponse;
import com.sinet.gage.dlap.entities.LoginResponse;
import com.sinet.gage.provision.Application;
import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.model.LoginRequest;
import com.sinet.gage.provision.model.Message;
import com.sinet.gage.provision.model.MessageType;
import com.sinet.gage.provision.model.MessageUtil;
import com.sinet.gage.provision.model.User;
import com.sinet.gage.provision.service.AuthService;
import com.sinet.gage.provision.service.RightsService;
import com.sinet.gage.provision.service.UserService;
import com.sinet.gage.provision.util.TestUtil;

/**
 * Test class for {@link com.sinet.gage.provision.controller.UserController}
 * @author Team Gage
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
public class UserControllerTest {

	private static final String THE_USER_PASSWORD_IS_A_REQUIRED_FIELD = "The user password is a required field";

	private static final String BASE_URI = "/user";
	
	private static final String EMPTY = StringUtils.EMPTY;
	
	private static final String USER_ID = "2347";
	
	private static final String DOMAIN_ID = "2348";
	
	private static final String CATALOG_DOMAIN_ID = "34816125";

	private static final String CUSTOMER_DOMAIN_ID = "34863804";
	
	private MockMvc mockMvc;

	@InjectMocks
	private UserController mockUserController;

	@Mock
	private UserService mockUserService;
	
	@Mock
	private AuthService mockAuthSerivce;
	
	@Mock
	private RightsService mockRightsService;
	
	@Mock
	private MessageSource mockMessageSource;
	
	@Mock
	private MessageUtil mockMessageUtil;
	
	@Mock
	AppProperties mockProperties;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when( mockProperties.getCustomerDomainId()).thenReturn(CUSTOMER_DOMAIN_ID);
		when( mockProperties.getCourseCatalogDomainId()).thenReturn(CATALOG_DOMAIN_ID);
		mockMvc = MockMvcBuilders.standaloneSetup(mockUserController)
				.setHandlerExceptionResolvers(TestUtil.createExceptionResolver(mockMessageSource))
				.build();
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.UserController#loginUser(LoginRequest)}
	 * @throws Exception
	 */
	@Test
	public void testLoginUser() throws Exception {
		String uri = BASE_URI + "/login";
		
		LoginResponse response = new LoginResponse(USER_ID, EMPTY, EMPTY, EMPTY, EMPTY, DOMAIN_ID, EMPTY, EMPTY, EMPTY, EMPTY);
		List<DomainRightsResponse> domainRightsList = new ArrayList<>();
		DomainRightsResponse domainResponse1 = new DomainRightsResponse(DOMAIN_ID, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, 0);
		DomainRightsResponse domainResponse2 = new DomainRightsResponse(mockProperties.getCustomerDomainId(), EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, 0);
		DomainRightsResponse domainResponse3 = new DomainRightsResponse(mockProperties.getCourseCatalogDomainId(), EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, 0);
		domainRightsList.add(domainResponse1);
		domainRightsList.add(domainResponse2);
		domainRightsList.add(domainResponse3);
		Message msg = new Message();
		msg.setMessageType(MessageType.SUCCESS);
		
		when(mockAuthSerivce.authencateUser(any(LoginRequest.class))).thenReturn(response);
		when(mockRightsService.getUserRightsForDomains(any(User.class))).thenReturn(domainRightsList);
		when(mockMessageUtil.createMessageWithPayload(any(String.class), any(LoginResponse.class), any(DomainRightsResponse.class), any(String.class), anyMapOf(String.class,String.class), eq(LoginResponse.class))).thenReturn(msg);
		
		mockMvc.perform(post(uri)
				.content(
						"{ \"userName\" : \"admin1\", \"userPassword\" : \"admin1\" }")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is("SUCCESS")));
		
		verify(mockAuthSerivce,times(1)).authencateUser(any(LoginRequest.class));
		verify(mockRightsService,times(1)).getUserRightsForDomains(any(User.class));
		verifyZeroInteractions(mockAuthSerivce);
		verifyZeroInteractions(mockRightsService);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.UserController#loginUser(LoginRequest)}
	 * @throws Exception
	 */
	@Test
	public void testLoginUserNotAuthenticated() throws Exception {
		String uri = BASE_URI + "/login";
		Message msg = new Message();
		msg.setMessageType(MessageType.ERROR);
		
		when(mockAuthSerivce.authencateUser(any(LoginRequest.class))).thenReturn(null);
		when(mockMessageUtil.createMessage(any(String.class), any(Boolean.class))).thenReturn(msg);
		
		mockMvc.perform(post(uri)
				.content(
						"{ \"userName\" : \"administrator1\", \"userPassword\" : \"g#c9=WW91\" }")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is("ERROR")));
		
		verify(mockAuthSerivce,times(1)).authencateUser(any(LoginRequest.class));
		verify(mockRightsService,times(0)).getUserRightsForDomains(any(User.class));
		verifyZeroInteractions(mockAuthSerivce);
		verifyZeroInteractions(mockRightsService);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.UserController#loginUser(LoginRequest)}
	 * @throws Exception
	 */
	@Test
	public void testLoginUserNotAuthorized() throws Exception {
		String uri = BASE_URI + "/login";
		
		LoginResponse response = new LoginResponse(USER_ID, EMPTY, EMPTY, EMPTY, EMPTY, DOMAIN_ID, EMPTY, EMPTY, EMPTY, EMPTY);
		List<DomainRightsResponse> domainRightsList = new ArrayList<>();
		DomainRightsResponse domainResponse = new DomainRightsResponse(DOMAIN_ID, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, 0);
		domainRightsList.add(domainResponse);
		Message msg = new Message();
		msg.setMessageType(MessageType.ERROR);
		when(mockAuthSerivce.authencateUser(any(LoginRequest.class))).thenReturn(response);
		when(mockRightsService.getUserRightsForDomains(any(User.class))).thenReturn(domainRightsList);
		when(mockMessageUtil.createMessage(any(String.class), any(Boolean.class), any(String.class))).thenReturn(msg);
		
		mockMvc.perform(post(uri)
				.content(
						"{ \"userName\" : \"administrator\", \"userPassword\" : \"g#c9=WW91\" }")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is("ERROR")));
		
		verify(mockAuthSerivce,times(1)).authencateUser(any(LoginRequest.class));
		verify(mockRightsService,times(1)).getUserRightsForDomains(any(User.class));
		verifyZeroInteractions(mockAuthSerivce);
		verifyZeroInteractions(mockRightsService);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provisioning.tool.controller.ControllerValidationHandler#processValidationError(org.springframework.web.bind.MethodArgumentNotValidException)}.
	 * @throws Exception 
	 */
	@Test
	public void testProcessWithMissingRequiredElement() throws Exception {
		String uri = BASE_URI + "/login";
		when(mockMessageSource.getMessage(any(String.class), Mockito.anyObject(), any(Locale.class))).thenReturn(THE_USER_PASSWORD_IS_A_REQUIRED_FIELD);
		mockMvc.perform(post(uri)
				.content(
						"{ \"userName\" : \"sdsdfd/administrator\", \"userPassword\" : null }")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
				.andExpect(jsonPath("$.messageType", is("ERROR")))
				.andExpect(jsonPath("$.message", is(THE_USER_PASSWORD_IS_A_REQUIRED_FIELD)));				
		
		verify(mockAuthSerivce,times(0)).authencateUser(any(LoginRequest.class));
		verifyZeroInteractions(mockAuthSerivce);
		verifyZeroInteractions(mockRightsService);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.UserController#logoutUser()}
	 * @throws Exception
	 */
	@Test
	public void testLogoutUser() throws Exception {
		String uri = BASE_URI + "/logout";
		Message msg = new Message();
		msg.setMessageType(MessageType.SUCCESS);
		msg.setMessage("User successfully logged out");
		when(mockAuthSerivce.logoutUser()).thenReturn(Boolean.TRUE);
		when(mockMessageUtil.createMessage(any(String.class), any(Boolean.class))).thenReturn(msg);
		
		mockMvc.perform(get(uri)				
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is("SUCCESS")))
				.andExpect(jsonPath("$.message", is("User successfully logged out")));
		
		verify(mockAuthSerivce,times(1)).logoutUser();
		verifyZeroInteractions(mockAuthSerivce);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.UserController#logoutUser()}
	 * @throws Exception
	 */	
	@Test
	public void testLogoutUserFailed() throws Exception {
		String uri = BASE_URI + "/logout";
		Message msg = new Message();
		msg.setMessageType(MessageType.ERROR);
		msg.setMessage("Unable to logged out login user");
		
		when(mockAuthSerivce.logoutUser()).thenReturn(Boolean.FALSE);
		when(mockMessageUtil.createMessage(any(String.class), any(Boolean.class))).thenReturn(msg);
		
		mockMvc.perform(get(uri)				
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is("ERROR")))
				.andExpect(jsonPath("$.message", is("Unable to logged out login user")));
		
		verify(mockAuthSerivce,times(1)).logoutUser();
		verifyZeroInteractions(mockAuthSerivce);
	}
	
}
