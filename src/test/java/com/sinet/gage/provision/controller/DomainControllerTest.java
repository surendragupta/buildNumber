package com.sinet.gage.provision.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinet.gage.dlap.entities.Customization;
import com.sinet.gage.dlap.entities.Customization.AllowViewInGpeerCompletion;
import com.sinet.gage.dlap.entities.Customization.Files;
import com.sinet.gage.dlap.entities.Customization.Files.File;
import com.sinet.gage.dlap.entities.Customization.LearningObjectives;
import com.sinet.gage.dlap.entities.Customization.MenuItems;
import com.sinet.gage.dlap.entities.Customization.MenuItems.MenuItem;
import com.sinet.gage.dlap.entities.Customization.ResourceBase;
import com.sinet.gage.dlap.entities.Customization.Securehash;
import com.sinet.gage.dlap.entities.Customization.Strings;
import com.sinet.gage.dlap.entities.DomainResponse;
import com.sinet.gage.dlap.entities.EdivateLearn;
import com.sinet.gage.dlap.exception.AvailabilityLicenseException;
import com.sinet.gage.dlap.exception.DomainDuplicateException;
import com.sinet.gage.dlap.exception.DomainNameDuplicateException;
import com.sinet.gage.provision.Application;
import com.sinet.gage.provision.model.DistrictDomainRequest;
import com.sinet.gage.provision.model.EditSchoolDomainRequest;
import com.sinet.gage.provision.model.Message;
import com.sinet.gage.provision.model.MessageType;
import com.sinet.gage.provision.model.MessageUtil;
import com.sinet.gage.provision.model.SchoolDomainRequest;
import com.sinet.gage.provision.service.DomainFacade;
import com.sinet.gage.provision.service.DomainService;
import com.sinet.gage.provision.util.Constants;
import com.sinet.gage.provision.util.MessageConstants;
import com.sinet.gage.provision.util.TestUtil;

/**
 * Test class for {@link com.sinet.gage.provision.controller.DomainController}
 * @author Team Gage
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
public class DomainControllerTest {

	private static final String ERROR = "ERROR";
	private static final String SUCCESS = "SUCCESS";
	private static final String TOKEN_HEADER = "token";
	private static final String DOMAIN_ID = "2348";
	private static final String PARENT_DOMAIN_ID = "22348";
	private static final long FLAG = 0;
	private static final String EMPTY = StringUtils.EMPTY;
	
	private static final String BASE_URI = "/domain/";
	private static final String GET_DOMAIN_URI = BASE_URI + DOMAIN_ID;
	private static final String COURSE_CATALOG_URI = BASE_URI + "list/providers";
	private static final String STATE_URI = BASE_URI + "list/state";
	private static final String CHLD_DOMAIN_URI = BASE_URI + "list/" + DOMAIN_ID;  
	private static final String CHILD_DOMAIN_DATA_URI = BASE_URI + "/list/school/2348";
	private static final String EDIT_SCHOOL_URI = BASE_URI + "/edit/school";
	private static final String CREATE_SCHOOL_URI = BASE_URI + "/create/school";
	private static final String CREATE_DISTRICT_URI = BASE_URI + "/create/district";
	private static final String EDIT_DISTRICT_URI = BASE_URI + "/edit/district/2348";
		
	private MockMvc mockMvc;
	
	@InjectMocks
	private DomainController mockDomainController;
	
	@Mock
	private DomainService mockDomainService;
	
	@Mock
	private DomainFacade mockDomainFacade;
	
	@Mock
	private MessageSource mockMessageSource;
	
	@Mock
	private MessageUtil mockMessageUtil;
		
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(mockDomainController)
				.setHandlerExceptionResolvers(TestUtil.createExceptionResolver(mockMessageSource))
				.build();
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#getAllStates()}.
	 * @throws Exception 
	 */
	@Test
	public void testGetAllStates() throws Exception {
		Message msg = new Message();
		msg.setMessageType(MessageType.SUCCESS);
		List<DomainResponse> listOfDomainResponse = new ArrayList<>();
		listOfDomainResponse.add(new DomainResponse(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, FLAG, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null));
		when(mockDomainService.findAllStateDomains(any(String.class), any(Integer.class))).thenReturn(listOfDomainResponse );
		when(mockMessageUtil.createMessageWithPayload(any(String.class), anyListOf(DomainResponse.class), anyMapOf(String.class, String.class), eq(DomainResponse.class))).thenReturn(msg);
		
		mockMvc.perform(get(STATE_URI)
				.header(TOKEN_HEADER, "")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(SUCCESS)));
		
		verify(mockDomainService,times(1)).findAllStateDomains(any(String.class), any(Integer.class));
		verifyZeroInteractions(mockDomainService);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#getAllStates()}.
	 * @throws Exception 
	 */
	@Test
	public void testGetAllStatesFailed() throws Exception {
		Message msg = new Message();
		msg.setMessageType(MessageType.ERROR);
		when(mockDomainService.findAllStateDomains(any(String.class), any(Integer.class))).thenReturn(Collections.<DomainResponse>emptyList() );
		when(mockMessageUtil.createMessage(any(String.class), any(Boolean.class))).thenReturn(msg);
		
		mockMvc.perform(get(STATE_URI)
				.header(TOKEN_HEADER, "")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)));
		
		verify(mockDomainService,times(1)).findAllStateDomains(any(String.class), any(Integer.class));
		verifyZeroInteractions(mockDomainService);
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#getAllContentProviderDomains()}.
	 * @throws Exception 
	 */
	@Test
	public void testGetAllContentProviderDomains() throws Exception {
		Message msg = new Message();
		msg.setMessageType(MessageType.SUCCESS);
		List<DomainResponse> listOfDomainResponse = new ArrayList<>();
		listOfDomainResponse.add(new DomainResponse(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, FLAG, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null));
		when(mockDomainService.findAllContentDomains(any(String.class), any(Integer.class))).thenReturn(listOfDomainResponse );
		when(mockMessageUtil.createMessageWithPayload(any(String.class), anyListOf(DomainResponse.class), anyMapOf(String.class, String.class), eq(DomainResponse.class))).thenReturn(msg);
		
		mockMvc.perform(get(COURSE_CATALOG_URI)
				.header(TOKEN_HEADER, "")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(SUCCESS)));
		
		verify(mockDomainService,times(1)).findAllContentDomains(any(String.class), any(Integer.class));
		verifyZeroInteractions(mockDomainService);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#getAllContentProviderDomains()}.
	 * @throws Exception 
	 */
	@Test
	public void testGetAllContentProviderDomainsFailed() throws Exception {
		Message msg = new Message();
		msg.setMessageType(MessageType.ERROR);
		when(mockDomainService.findAllContentDomains(any(String.class), any(Integer.class))).thenReturn(Collections.<DomainResponse>emptyList());
		when(mockMessageUtil.createMessage(any(String.class), any(Boolean.class))).thenReturn(msg);
		
		mockMvc.perform(get(COURSE_CATALOG_URI)
				.header(TOKEN_HEADER, "")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)));
		
		verify(mockDomainService,times(1)).findAllContentDomains(any(String.class), any(Integer.class));
		verifyZeroInteractions(mockDomainService);
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#getAllChildDomains(java.lang.String, java.lang.String, java.lang.Integer)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetAllChildDomains() throws Exception {
		Message msg = new Message();
		msg.setMessageType(MessageType.SUCCESS);
		List<DomainResponse> listOfDomainResponse = new ArrayList<>();
		listOfDomainResponse.add(new DomainResponse(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, FLAG, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null));
		when(mockDomainService.findAllChildDomains(any(String.class), any(String.class), any(String.class), any(String.class), any(Integer.class))).thenReturn(listOfDomainResponse );
		when(mockMessageUtil.createMessageWithPayload(any(String.class), anyListOf(DomainResponse.class), anyMapOf(String.class, String.class), eq(DomainResponse.class))).thenReturn(msg);
		
		mockMvc.perform(get(CHLD_DOMAIN_URI)
				.header(TOKEN_HEADER, "")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(SUCCESS)));
		
		verify(mockDomainService,times(1)).findAllChildDomains(any(String.class), any(String.class), any(String.class), any(String.class), any(Integer.class));
		verifyZeroInteractions(mockDomainService);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#getAllChildDomains(java.lang.String, java.lang.String, java.lang.Integer)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetAllChildDomainsFailed() throws Exception {
		Message msg = new Message();
		msg.setMessageType(MessageType.ERROR);
		when(mockDomainService.findAllChildDomains(any(String.class), any(String.class), any(String.class), any(String.class), any(Integer.class))).thenReturn(Collections.<DomainResponse>emptyList() );
		when(mockMessageUtil.createMessage(any(String.class), any(Boolean.class))).thenReturn(msg);
		
		mockMvc.perform(get(CHLD_DOMAIN_URI)
				.header(TOKEN_HEADER, "")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)));
		
		verify(mockDomainService,times(1)).findAllChildDomains(any(String.class), any(String.class), any(String.class), any(String.class), any(Integer.class));
		verifyZeroInteractions(mockDomainService);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#getDomain(String, Long)
	 * @throws Exception
	 */
	@Test
	public void testGetDomain () throws Exception {
		DomainResponse domainResponse = new DomainResponse("1234", "Academy School District", "ed-academy", "4321", EMPTY, EMPTY, 0, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null);
		EdivateLearn edivateLearn = new EdivateLearn("district", "Per Seat", "100", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");

		when(mockDomainService.findDomain(anyString(),anyLong())).thenReturn(domainResponse );
		when(mockDomainService.getDomainData(anyString(),anyLong())).thenReturn(createCustomization(edivateLearn));
		when(mockDomainFacade.getSubscriptionsForDomain(anyString(), anyString())).thenReturn(createProviderMap());
		when(mockMessageUtil.createMessageWithPayload(anyString(),any(DomainResponse.class), any(Customization.class),anyListOf(String.class),anyListOf(String.class),anyMapOf(String.class,String.class),any(Class.class))).thenReturn(createMessage(MessageType.SUCCESS, "Domain found",domainResponse));
		
		mockMvc.perform(get(GET_DOMAIN_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(SUCCESS)))
				.andExpect(jsonPath("$.message", is("Domain found")));
		
		verify(mockDomainService,times(1)).findDomain(anyString(),anyLong());
		verify(mockDomainService,times(1)).getDomainData(anyString(),anyLong());
		verify(mockDomainFacade,times(1)).getSubscriptionsForDomain(anyString(), anyString());
		verifyZeroInteractions(mockDomainService, mockDomainFacade);
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#getDomain(String, Long)
	 * @throws Exception
	 */
	@Test
	public void testGetDomainCustomizationNotFound () throws Exception {
		DomainResponse domainResponse = new DomainResponse("1234", "Academy School District", "ed-academy", "4321", EMPTY, EMPTY, 0, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null);

		when(mockDomainService.findDomain(anyString(),anyLong())).thenReturn(domainResponse );
		when(mockDomainService.getDomainData(anyString(),anyLong())).thenReturn(null);
		when(mockDomainFacade.getSubscriptionsForDomain(anyString(), anyString())).thenReturn(createProviderMap());
		when(mockMessageUtil.createMessageWithPayload(anyString(),any(DomainResponse.class), any(Customization.class),anyListOf(String.class),anyListOf(String.class),anyMapOf(String.class,String.class),any(Class.class))).thenReturn(createMessage(MessageType.SUCCESS, "Domain found",domainResponse));
		
		mockMvc.perform(get(GET_DOMAIN_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(SUCCESS)))
				.andExpect(jsonPath("$.message", is("Domain found")));
		
		verify(mockDomainService,times(1)).findDomain(anyString(),anyLong());
		verify(mockDomainService,times(1)).getDomainData(anyString(),anyLong());
		verify(mockDomainFacade,times(0)).getSubscriptionsForDomain(anyString(), anyString());
		verifyZeroInteractions(mockDomainService, mockDomainFacade);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#getDomain(String, Long)
	 * @throws Exception
	 */
	@Test
	public void testGetDomainEdivateLearnNotFound () throws Exception {
		DomainResponse domainResponse = new DomainResponse("1234", "Academy School District", "ed-academy", "4321", EMPTY, EMPTY, 0, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null);

		when(mockDomainService.findDomain(anyString(),anyLong())).thenReturn(domainResponse );
		when(mockDomainService.getDomainData(anyString(),anyLong())).thenReturn(createCustomization(null));
		when(mockDomainFacade.getSubscriptionsForDomain(anyString(), anyString())).thenReturn(createProviderMap());
		when(mockMessageUtil.createMessageWithPayload(anyString(),any(DomainResponse.class), any(Customization.class),anyListOf(String.class),anyListOf(String.class),anyMapOf(String.class,String.class),any(Class.class))).thenReturn(createMessage(MessageType.SUCCESS, "Domain found",domainResponse));
		
		mockMvc.perform(get(GET_DOMAIN_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(SUCCESS)))
				.andExpect(jsonPath("$.message", is("Domain found")));
		
		verify(mockDomainService,times(1)).findDomain(anyString(),anyLong());
		verify(mockDomainService,times(1)).getDomainData(anyString(),anyLong());
		verify(mockDomainFacade,times(0)).getSubscriptionsForDomain(anyString(), anyString());
		verifyZeroInteractions(mockDomainService, mockDomainFacade);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#getDomain(String, Long)
	 * @throws Exception
	 */
	@Test
	public void testGetDomainNotFound () throws Exception {
		when(mockDomainService.findDomain(anyString(),anyLong())).thenReturn(null );
		when(mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg())).thenReturn(createMessage(MessageType.ERROR,"Domain not found with domain id 1234",null));
		
		mockMvc.perform(get(GET_DOMAIN_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is("Domain not found with domain id 1234")));
		
		verify(mockDomainService,times(1)).findDomain(anyString(),anyLong());
		verify(mockDomainService,times(0)).getDomainData(anyString(),anyLong());
		verify(mockDomainFacade,times(0)).getSubscriptionsForDomain(anyString(), anyString());
		verifyZeroInteractions(mockDomainService, mockDomainFacade);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#getAllChildDomainsWithDomainData(String, String, String, Integer)}
	 * @throws Exception 
	 */
	@Test
	public void testGetAllChildDomainsWithDomainData () throws Exception {
		DomainResponse domainResponse = new DomainResponse("1234", "Academy School District", "ed-academy", "4321", EMPTY, EMPTY, 0, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null);
		List<DomainResponse> domainResponseList = Arrays.asList( domainResponse );
		when( mockDomainService.findAllChildDomainsWithDomainData(anyString(), anyString(), anyString(), anyString(), anyInt())).thenReturn( domainResponseList);
		when(mockMessageUtil.createMessageWithPayload(MessageConstants.DOMAINS_FOUND, domainResponseList, Collections.singletonMap("dataModels", "domains"), DomainResponse.class)).thenReturn(createMessage(MessageType.SUCCESS,"Domains found",null));
		
		mockMvc.perform(get(CHILD_DOMAIN_DATA_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(SUCCESS)))
				.andExpect(jsonPath("$.message", is("Domains found")));
		
		verify( mockDomainService).findAllChildDomainsWithDomainData(anyString(), anyString(), anyString(), anyString(), anyInt());
		verify ( mockMessageUtil ).createMessageWithPayload(MessageConstants.DOMAINS_FOUND, domainResponseList, Collections.singletonMap("dataModels", "domains"), DomainResponse.class);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#getAllChildDomainsWithDomainData(String, String, String, Integer)}
	 * @throws Exception 
	 */
	@Test
	public void testGetAllChildDomainsWithDomainDataNotFounnd () throws Exception {
		List<DomainResponse> domainResponseList = new ArrayList<>();
		when( mockDomainService.findAllChildDomainsWithDomainData(anyString(), anyString(), anyString(), anyString(), anyInt())).thenReturn( domainResponseList);
		when(mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg())).thenReturn(createMessage(MessageType.ERROR,"Domains not found",null));
		
		mockMvc.perform(get(CHILD_DOMAIN_DATA_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is("Domains not found")));
		
		verify( mockDomainService).findAllChildDomainsWithDomainData(anyString(), anyString(), anyString(), anyString(), anyInt());
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#createDistrictDomain(String, com.sinet.gage.provision.model.DistrictDomainRequest)}
	 * @throws DomainNameDuplicateException 
	 * @throws DomainDuplicateException 
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 */
	@Test
	public void testCreateDistrictDomain () throws DomainDuplicateException, DomainNameDuplicateException, JsonProcessingException, Exception {
		DistrictDomainRequest request = createDistrictDomainRequest();
		when( mockDomainFacade.createDistrictDomain(anyString(), any(DistrictDomainRequest.class)) ).thenReturn(Boolean.TRUE);
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.SUCCESS,"Domain created successfully",null));
		
		mockMvc.perform(post(CREATE_DISTRICT_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(SUCCESS)))
				.andExpect(jsonPath("$.message", is("Domain created successfully")));
		
		verify( mockDomainFacade ).createDistrictDomain(anyString(), any(DistrictDomainRequest.class));
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#createDistrictDomain(String, com.sinet.gage.provision.model.DistrictDomainRequest)}
	 * @throws DomainNameDuplicateException 
	 * @throws DomainDuplicateException 
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 */
	@Test
	public void testCreateDistrictDomainFailed () throws DomainDuplicateException, DomainNameDuplicateException, JsonProcessingException, Exception {
		DistrictDomainRequest request = createDistrictDomainRequest();
		when( mockDomainFacade.createDistrictDomain(anyString(), any(DistrictDomainRequest.class)) ).thenReturn(Boolean.FALSE);
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.ERROR,"Domain creation Failed",null));
		
		mockMvc.perform(post(CREATE_DISTRICT_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is("Domain creation Failed")));
		
		verify( mockDomainFacade ).createDistrictDomain(anyString(), any(DistrictDomainRequest.class));
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#createDistrictDomain(String, com.sinet.gage.provision.model.DistrictDomainRequest)}
	 * @throws DomainNameDuplicateException 
	 * @throws DomainDuplicateException 
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 */
	@Test
	public void testCreateDistrictDomainFailedDuplicateDomain () throws DomainDuplicateException, DomainNameDuplicateException, JsonProcessingException, Exception {
		DistrictDomainRequest request = createDistrictDomainRequest();
		when( mockDomainFacade.createDistrictDomain(anyString(), any(DistrictDomainRequest.class)) ).thenThrow( new DomainDuplicateException("Duplicate domain login prefix exists with name Academy School District"));
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.ERROR,"Duplicate domain login prefix exists with name Academy School District",null));
		
		mockMvc.perform(post(CREATE_DISTRICT_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is("Duplicate domain login prefix exists with name Academy School District")));
		
		verify( mockDomainFacade ).createDistrictDomain(anyString(), any(DistrictDomainRequest.class));
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#createDistrictDomain(String, com.sinet.gage.provision.model.DistrictDomainRequest)}
	 * @throws DomainNameDuplicateException 
	 * @throws DomainDuplicateException 
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 */
	@Test
	public void testCreateDistrictDomainFailedDuplicateDomainName () throws DomainDuplicateException, DomainNameDuplicateException, JsonProcessingException, Exception {
		DistrictDomainRequest request = createDistrictDomainRequest();
		when( mockDomainFacade.createDistrictDomain(anyString(), any(DistrictDomainRequest.class)) ).thenThrow( new DomainNameDuplicateException("Duplicate domain name exists with name Academy School District"));
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.ERROR,"Duplicate domain name exists with name Academy School District",null));
		
		mockMvc.perform(post(CREATE_DISTRICT_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is("Duplicate domain name exists with name Academy School District")));
		
		verify( mockDomainFacade ).createDistrictDomain(anyString(), any(DistrictDomainRequest.class));
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#editDistrictDomain(String, DistrictDomainRequest, String)}
	 * @throws DomainNameDuplicateException 
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 */
	@Test
	public void testEditDistrictDomain () throws DomainNameDuplicateException, JsonProcessingException, Exception {
		DistrictDomainRequest request = createDistrictDomainRequest();
		when( mockDomainFacade.editDistrictDomain(anyString(), any(DistrictDomainRequest.class), anyString()) ).thenReturn(Boolean.TRUE);
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.SUCCESS,"Domain updated successfully",null));
		
		mockMvc.perform(post(EDIT_DISTRICT_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(SUCCESS)))
				.andExpect(jsonPath("$.message", is("Domain updated successfully")));
		
		verify( mockDomainFacade ).editDistrictDomain(anyString(), any(DistrictDomainRequest.class), anyString());
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());		
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#editDistrictDomain(String, DistrictDomainRequest, String)}
	 * @throws DomainNameDuplicateException 
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 */
	@Test
	public void testEditDistrictDomainFailed () throws DomainNameDuplicateException, JsonProcessingException, Exception {
		DistrictDomainRequest request = createDistrictDomainRequest();
		when( mockDomainFacade.editDistrictDomain(anyString(), any(DistrictDomainRequest.class), anyString()) ).thenReturn(Boolean.FALSE);
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.ERROR,"Domain not updated",null));
		
		mockMvc.perform(post(EDIT_DISTRICT_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is("Domain not updated")));
		
		verify( mockDomainFacade ).editDistrictDomain(anyString(), any(DistrictDomainRequest.class), anyString());
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());		
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#editDistrictDomain(String, DistrictDomainRequest, String)}
	 * @throws DomainNameDuplicateException 
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 */
	@Test
	public void testEditDistrictDomainFailedDuplicateDomainName () throws DomainNameDuplicateException, JsonProcessingException, Exception {
		DistrictDomainRequest request = createDistrictDomainRequest();
		when( mockDomainFacade.editDistrictDomain(anyString(), any(DistrictDomainRequest.class), anyString()) ).thenThrow(new DomainNameDuplicateException("Duplicate domain name exists with name Academy School District"));
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.ERROR,"Duplicate domain name exists with name Academy School District",null));
		
		mockMvc.perform(post(EDIT_DISTRICT_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is("Duplicate domain name exists with name Academy School District")));
		
		verify( mockDomainFacade ).editDistrictDomain(anyString(), any(DistrictDomainRequest.class), anyString());
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());		
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#createSchoolDomain(String, com.sinet.gage.provision.model.SchoolDomainRequest)} 
	 * @throws AvailabilityLicenseException 
	 * @throws DomainNameDuplicateException 
	 * @throws DomainDuplicateException 
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 */
	@Test
	public void testCreateSchoolDomain () throws DomainDuplicateException, DomainNameDuplicateException, AvailabilityLicenseException, JsonProcessingException, Exception {
		SchoolDomainRequest request = createSchoolDomainRequest();
		
		when( mockDomainFacade.createSchool(anyString(), any(EditSchoolDomainRequest.class)) ).thenReturn(Boolean.TRUE);
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.SUCCESS,"Domain created successfully",null));
		
		mockMvc.perform(post(CREATE_SCHOOL_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(SUCCESS)))
				.andExpect(jsonPath("$.message", is("Domain created successfully")));
		
		verify( mockDomainFacade ).createSchool(anyString(), any(EditSchoolDomainRequest.class));
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#createSchoolDomain(String, com.sinet.gage.provision.model.SchoolDomainRequest)} 
	 * @throws AvailabilityLicenseException 
	 * @throws DomainNameDuplicateException 
	 * @throws DomainDuplicateException 
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 */
	@Test
	public void testCreateSchoolDomainFailed () throws DomainDuplicateException, DomainNameDuplicateException, AvailabilityLicenseException, JsonProcessingException, Exception {
		SchoolDomainRequest request = createSchoolDomainRequest();
		
		when( mockDomainFacade.createSchool(anyString(), any(EditSchoolDomainRequest.class)) ).thenReturn(Boolean.FALSE);
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.ERROR,"Domain creation Failed",null));
		
		mockMvc.perform(post(CREATE_SCHOOL_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is("Domain creation Failed")));
		
		verify( mockDomainFacade ).createSchool(anyString(), any(EditSchoolDomainRequest.class));
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#createSchoolDomain(String, com.sinet.gage.provision.model.SchoolDomainRequest)} 
	 * @throws AvailabilityLicenseException 
	 * @throws DomainNameDuplicateException 
	 * @throws DomainDuplicateException 
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 */
	@Test
	public void testCreateSchoolDomainNoLicenseAvailable () throws DomainDuplicateException, DomainNameDuplicateException, AvailabilityLicenseException, JsonProcessingException, Exception {
		SchoolDomainRequest request = createSchoolDomainRequest();
		
		when( mockDomainFacade.createSchool(anyString(), any(EditSchoolDomainRequest.class)) ).thenThrow(new AvailabilityLicenseException("License is unavailable"));
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.ERROR,"License is unavailable",null));
		
		mockMvc.perform(post(CREATE_SCHOOL_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is("License is unavailable")));
		
		verify( mockDomainFacade ).createSchool(anyString(), any(EditSchoolDomainRequest.class));
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#createSchoolDomain(String, com.sinet.gage.provision.model.SchoolDomainRequest)} 
	 * @throws AvailabilityLicenseException 
	 * @throws DomainNameDuplicateException 
	 * @throws DomainDuplicateException 
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 */
	@Test
	public void testCreateSchoolDomainDuplicateDomain () throws DomainDuplicateException, DomainNameDuplicateException, AvailabilityLicenseException, JsonProcessingException, Exception {
		SchoolDomainRequest request = createSchoolDomainRequest();
		
		when( mockDomainFacade.createSchool(anyString(), any(EditSchoolDomainRequest.class)) ).thenThrow(new DomainDuplicateException("Duplicate domain login prefix exists with name Villa High"));
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.ERROR,"Duplicate domain login prefix exists with name Villa High",null));
		
		mockMvc.perform(post(CREATE_SCHOOL_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is("Duplicate domain login prefix exists with name Villa High")));
		
		verify( mockDomainFacade ).createSchool(anyString(), any(EditSchoolDomainRequest.class));
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.DomainController#createSchoolDomain(String, com.sinet.gage.provision.model.SchoolDomainRequest)} 
	 * @throws AvailabilityLicenseException 
	 * @throws DomainNameDuplicateException 
	 * @throws DomainDuplicateException 
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 */
	@Test
	public void testCreateSchoolDomainDuplicateDomainName () throws DomainDuplicateException, DomainNameDuplicateException, AvailabilityLicenseException, JsonProcessingException, Exception {
		SchoolDomainRequest request = createSchoolDomainRequest();
		
		when( mockDomainFacade.createSchool(anyString(), any(EditSchoolDomainRequest.class)) ).thenThrow(new DomainNameDuplicateException("Duplicate domain name exists with name Villa High"));
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.ERROR,"Duplicate domain name exists with name Villa High",null));
		
		mockMvc.perform(post(CREATE_SCHOOL_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is("Duplicate domain name exists with name Villa High")));
		
		verify( mockDomainFacade ).createSchool(anyString(), any(EditSchoolDomainRequest.class));
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());
	}

	/**
	 *  Test method for {@link com.sinet.gage.provision.controller.DomainController#editSchoolDomain(String, com.sinet.gage.provision.model.EditSchoolDomainRequest)}
	 * @throws AvailabilityLicenseException 
	 * @throws DomainNameDuplicateException 
	 * @throws Exception 
	 */
	@Test
	public void testEditSchoolDomain () throws DomainNameDuplicateException, AvailabilityLicenseException, Exception {
		EditSchoolDomainRequest request = createEditSchoolDomainRequest();
		
		when( mockDomainFacade.editSchool(anyString(), any(EditSchoolDomainRequest.class)) ).thenReturn(Boolean.TRUE);
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.SUCCESS,"Domain updated successfully",null));
		
		mockMvc.perform(post(EDIT_SCHOOL_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(SUCCESS)))
				.andExpect(jsonPath("$.message", is("Domain updated successfully")));
		
		verify( mockDomainFacade ).editSchool(anyString(), any(EditSchoolDomainRequest.class));
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.controller.DomainController#editSchoolDomain(String, com.sinet.gage.provision.model.EditSchoolDomainRequest)}
	 * @throws AvailabilityLicenseException 
	 * @throws DomainNameDuplicateException 
	 * @throws Exception 
	 */
	@Test
	public void testEditSchoolDomainFailed () throws DomainNameDuplicateException, AvailabilityLicenseException, Exception {
		EditSchoolDomainRequest request = createEditSchoolDomainRequest();
		
		when( mockDomainFacade.editSchool(anyString(), any(EditSchoolDomainRequest.class)) ).thenReturn(Boolean.FALSE);
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.ERROR,"Domain not updated",null));
		
		mockMvc.perform(post(EDIT_SCHOOL_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is("Domain not updated")));
		
		verify( mockDomainFacade ).editSchool(anyString(), any(EditSchoolDomainRequest.class));
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.controller.DomainController#editSchoolDomain(String, com.sinet.gage.provision.model.EditSchoolDomainRequest)}
	 * @throws AvailabilityLicenseException 
	 * @throws DomainNameDuplicateException 
	 * @throws Exception 
	 */
	@Test
	public void testEditSchoolDomainDuplicateDomainName () throws DomainNameDuplicateException, AvailabilityLicenseException, Exception {
		EditSchoolDomainRequest request = createEditSchoolDomainRequest();
		
		when( mockDomainFacade.editSchool(anyString(), any(EditSchoolDomainRequest.class)) ).thenThrow(new DomainNameDuplicateException("Domain name duplicated"));
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.ERROR,"Duplicate domain name exists with name Villa High",null));
		
		mockMvc.perform(post(EDIT_SCHOOL_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is("Duplicate domain name exists with name Villa High")));
		
		verify( mockDomainFacade ).editSchool(anyString(), any(EditSchoolDomainRequest.class));
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.controller.DomainController#editSchoolDomain(String, com.sinet.gage.provision.model.EditSchoolDomainRequest)}
	 * @throws AvailabilityLicenseException 
	 * @throws DomainNameDuplicateException 
	 * @throws Exception 
	 */
	@Test
	public void testEditSchoolDomainLicenseNotAvailable () throws DomainNameDuplicateException, AvailabilityLicenseException, Exception {
		EditSchoolDomainRequest request = createEditSchoolDomainRequest();
		
		when( mockDomainFacade.editSchool(anyString(), any(EditSchoolDomainRequest.class)) ).thenThrow(new AvailabilityLicenseException("License is unavailable"));
		when( mockMessageUtil.createMessage(anyString(), anyBoolean(), anyVararg()) ).thenReturn(createMessage(MessageType.ERROR,"License is unavailable",null));
		
		mockMvc.perform(post(EDIT_SCHOOL_URI)
				.header(TOKEN_HEADER, EMPTY)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonify(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is("License is unavailable")));
		
		verify( mockDomainFacade ).editSchool(anyString(), any(EditSchoolDomainRequest.class));
		verify ( mockMessageUtil ).createMessage(anyString(), anyBoolean(), anyVararg());
	}

	/**
	 * @return
	 */
	private DistrictDomainRequest createDistrictDomainRequest() {
		DistrictDomainRequest request = new DistrictDomainRequest();
		request.setState(PARENT_DOMAIN_ID);
		request.setName("Academy School District");
		request.setLoginPrefix("asd-1234");
		request.setExternalId("asd-1234");
		request.setLicenseType("Per Seat");
		request.setLicensePool("Fixed");
		request.setNumbersOfLicense(100);
		request.setPilotDomain(false);
		request.setCourseCatalogs(Arrays.asList("12345"));
		request.setCourseSelection(Arrays.asList("123450"));
		request.setFullSubscription(Boolean.TRUE);
		request.setSubscriptionStartDate("11-15-2015");
		request.setSubscriptionEndDate("12-15-2035");
		return request;
	}
	
	/**
	 * @return
	 */
	private SchoolDomainRequest createSchoolDomainRequest() {
		SchoolDomainRequest request = new SchoolDomainRequest ();
		request.setDistrictDomainId(Long.valueOf(PARENT_DOMAIN_ID));
		request.setName("Villa High");
		request.setLoginPrefix("vi-1234");
		request.setExternalId("vi-1234");
		request.setNumbersOfLicense(100);
		request.setPilotDomain(false);
		return request;
	}
	
	/**
	 * @return
	 */
	private EditSchoolDomainRequest createEditSchoolDomainRequest() {
		EditSchoolDomainRequest request = new EditSchoolDomainRequest();
		request.setDomainId(Long.valueOf(DOMAIN_ID));
		request.setDistrictDomainId(Long.valueOf(PARENT_DOMAIN_ID));
		request.setName("Villa High");
		request.setLoginPrefix("vi-1234");
		request.setExternalId("vi-1234");
		request.setNumbersOfLicense(100);
		request.setPilotDomain(false);
		return request;
	}
	
	private Message createMessage( MessageType messageType, String desc, Object data ) {
		Message message = new Message();
		message.setMessageType( messageType );
		message.setMessage(desc);
		message.setData(data);
		return message;
	}

	private Map<String, List<String>> createProviderMap() {
		Map<String,List<String>> providerMap = new HashMap<>();
		List<String> domainList = Arrays.asList("11234");
		List<String> courseList = Arrays.asList("112345");
		providerMap.put(Constants.CATALOG_DOMAIN_LIST, domainList );
		providerMap.put(Constants.COURSE_LIST, courseList);
		return providerMap;
	}

	private Customization createCustomization(EdivateLearn edivateLearn){
		List<Securehash> secureHashs = new ArrayList<>();
		List<File> file = new ArrayList<>();
		Files files = new Files(file );
		ResourceBase resourceBase = new ResourceBase("7789/web");
		AllowViewInGpeerCompletion allowViewInGpeerCompletion = new AllowViewInGpeerCompletion("true");
		LearningObjectives los = new LearningObjectives("HA", "10", "12"); 
		List<MenuItem> menuItem = new ArrayList<>();
		MenuItems menuItems = new MenuItems(menuItem );
		com.sinet.gage.dlap.entities.Customization.Strings.String string = new com.sinet.gage.dlap.entities.Customization.Strings.String("key", "value");
		Strings strings = new Strings(string );
	
		Customization customization = new Customization(edivateLearn , secureHashs , files , resourceBase, allowViewInGpeerCompletion, los, menuItems , strings , null, null );
		return customization;
	}
	
	private <T> String jsonify( T pojo ) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(pojo);
	}
}