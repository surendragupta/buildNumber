package com.sinet.gage.provision.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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

import com.sinet.gage.dlap.entities.CourseResponse;
import com.sinet.gage.provision.Application;
import com.sinet.gage.provision.model.Message;
import com.sinet.gage.provision.model.MessageType;
import com.sinet.gage.provision.model.MessageUtil;
import com.sinet.gage.provision.service.CourseService;
import com.sinet.gage.provision.util.TestUtil;

/**
 * @author Team.Gage
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
public class CourseControllerTest {

	private static final String DOMAIN_ID = "43905064";
	private static final String PROVIDER_DOMAIN_ID = "34816161";
	private static final String COURSE_LIST_ERROR_MESSAGE = "Unable to find any courses for domain 2348";
	private static final String ERROR = "ERROR";
	private static final String SUCCESS = "SUCCESS";
	private static final String COURSE_LIST_SUCCESS_MESSAGE = "Found courses for domain";
	private static final String COURSE_TITLE = "Language Arts 1 A - Sinet 14-15 2015-2016";
	private static final String COURSE_ID_ONE = "34865496";
	private static final String COURSE_ID_TWO = "44865496";
	private static final String COURSE_COPY_ERROR_MESSAGE = "Unable to copy courses from one domain to another";
	private static final String BASE_URI = "/course";	
	private static final String HEADER_NAME = "token";	
	private static final String EMPTY = StringUtils.EMPTY;	
	private static final long FLAG = 0;
	private static final String SOURCE_DOMAIN_ID = "2348";
	private static final String DESTINATION_DOMAIN_ID = "23485";
	private static final String LIST_URI = BASE_URI + "/list/" + SOURCE_DOMAIN_ID;
	private static final String LIST_URI_DOMAIN = BASE_URI + "/list/" + PROVIDER_DOMAIN_ID + "/" + DOMAIN_ID;
	private static final String COPY_URI = BASE_URI + "/copycourse/" + SOURCE_DOMAIN_ID + "/" + DESTINATION_DOMAIN_ID;
	private static final String TOKEN = "~FbT1BAAAAAgCs_Qrz0st2B.ZDHtJ2z4Q_ZDvW47c3zyPA";
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private CourseController mockCourseController;
	
	@Mock
	private CourseService mockCourseService;
	
	@Mock
	private MessageSource mockMessageSource;
	
	@Mock
	private MessageUtil mockMessageUtil;
		
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(mockCourseController)
				.setHandlerExceptionResolvers(TestUtil.createExceptionResolver(mockMessageSource))
				.build();
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.CourseController#getAllCoursesForDomains(java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetAllCoursesForDomains() throws Exception {
		List<CourseResponse> listOfCourseResponse = new ArrayList<>();
		listOfCourseResponse.add(new CourseResponse(COURSE_ID_ONE, COURSE_TITLE, EMPTY, EMPTY, EMPTY, FLAG, EMPTY, EMPTY, EMPTY, EMPTY, FLAG, EMPTY, FLAG, FLAG, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));
		when(mockCourseService.getAllCoursesFromDomain(any(String.class), any(String.class))).thenReturn(listOfCourseResponse );
		when(mockMessageUtil.createMessageWithPayload(any(String.class), anyListOf(CourseResponse.class), anyMapOf(String.class, String.class), eq(CourseResponse.class))).thenReturn(createMessage(MessageType.SUCCESS, COURSE_LIST_SUCCESS_MESSAGE, listOfCourseResponse));
		
		mockMvc.perform(get(LIST_URI)
				.header(HEADER_NAME, "")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(SUCCESS)))
				.andExpect(jsonPath("$.message", is(COURSE_LIST_SUCCESS_MESSAGE)))
				.andExpect(jsonPath("$.data[0].id", is(COURSE_ID_ONE)))
				.andExpect(jsonPath("$.data[0].title", is(COURSE_TITLE)));
		
		verify(mockCourseService,times(1)).getAllCoursesFromDomain(any(String.class), any(String.class));
		verifyZeroInteractions(mockCourseService);
	}
	
	@Test
	public void testGetAllCoursesForDistrictDomainsNotFound() throws Exception{
		List<CourseResponse> providerListOfCourseResponse = new ArrayList<>();
		providerListOfCourseResponse.add(new CourseResponse(COURSE_ID_ONE, COURSE_TITLE, PROVIDER_DOMAIN_ID, EMPTY, EMPTY, FLAG, EMPTY, EMPTY, EMPTY, EMPTY, FLAG, EMPTY, FLAG, FLAG, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));
		
		List<CourseResponse> domainListOfCourseResponse = new ArrayList<>();
		domainListOfCourseResponse.add(new CourseResponse(COURSE_ID_TWO, COURSE_TITLE, DOMAIN_ID, EMPTY, EMPTY, FLAG, EMPTY, EMPTY, EMPTY, EMPTY, FLAG, EMPTY, FLAG, FLAG, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));

		when( mockCourseService.getAllCoursesFromDomain( TOKEN, PROVIDER_DOMAIN_ID ) ).thenReturn( providerListOfCourseResponse );
		when( mockCourseService.getAllCoursesFromDomain( TOKEN, DOMAIN_ID ) ).thenReturn( domainListOfCourseResponse );
		when(mockMessageUtil.createMessage(any(String.class), any(Boolean.class), any(String.class))).thenReturn( createMessage(MessageType.ERROR,COURSE_LIST_ERROR_MESSAGE,null) );
		
		mockMvc.perform(get( LIST_URI_DOMAIN )
				.header(HEADER_NAME, TOKEN )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is(COURSE_LIST_ERROR_MESSAGE)));
		
		verify( mockCourseService ).getAllCoursesFromDomain( TOKEN, PROVIDER_DOMAIN_ID );
		verify( mockCourseService ).getAllCoursesFromDomain( TOKEN, DOMAIN_ID );
		verifyZeroInteractions(mockCourseService);
	}
	
	
	@Test
	public void testGetAllCoursesForDistrictDomains() throws Exception{
		List<CourseResponse> providerListOfCourseResponse = new ArrayList<>();
		providerListOfCourseResponse.add(new CourseResponse(COURSE_ID_ONE, COURSE_TITLE, PROVIDER_DOMAIN_ID, EMPTY, EMPTY, FLAG, EMPTY, EMPTY, EMPTY, EMPTY, FLAG, EMPTY, FLAG, FLAG, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));
		
		List<CourseResponse> domainListOfCourseResponse = new ArrayList<>();
		domainListOfCourseResponse.add(new CourseResponse(COURSE_ID_TWO, COURSE_TITLE, DOMAIN_ID, EMPTY, EMPTY, FLAG, COURSE_ID_ONE, EMPTY, EMPTY, EMPTY, FLAG, EMPTY, FLAG, FLAG, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));

		when( mockCourseService.getAllCoursesFromDomain( TOKEN, PROVIDER_DOMAIN_ID ) ).thenReturn( providerListOfCourseResponse );
		when( mockCourseService.getAllCoursesFromDomain( TOKEN, DOMAIN_ID ) ).thenReturn( domainListOfCourseResponse );
		when(mockMessageUtil.createMessageWithPayload(any(String.class), anyListOf(CourseResponse.class), anyMapOf(String.class, String.class), eq(CourseResponse.class))).thenReturn(createMessage(MessageType.SUCCESS, COURSE_LIST_SUCCESS_MESSAGE, domainListOfCourseResponse));
		
		mockMvc.perform(get( LIST_URI_DOMAIN )
				.header(HEADER_NAME, TOKEN )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(SUCCESS)))
				.andExpect(jsonPath("$.message", is(COURSE_LIST_SUCCESS_MESSAGE)));
		
		verify( mockCourseService ).getAllCoursesFromDomain( TOKEN, PROVIDER_DOMAIN_ID );
		verify( mockCourseService ).getAllCoursesFromDomain( TOKEN, DOMAIN_ID );
		verifyZeroInteractions(mockCourseService);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.CourseController#getAllCoursesForDomains(java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetAllCoursesForDomainsNoCoursesFound() throws Exception {
		List<CourseResponse> listOfCourseResponse = new ArrayList<>();
		when(mockCourseService.getAllCoursesFromDomain(any(String.class), any(String.class))).thenReturn(listOfCourseResponse );
		when(mockMessageUtil.createMessage(any(String.class), any(Boolean.class), any(String.class))).thenReturn( createMessage(MessageType.ERROR,COURSE_LIST_ERROR_MESSAGE,null) );
		
		mockMvc.perform(get(LIST_URI)
				.header(HEADER_NAME, "")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is(COURSE_LIST_ERROR_MESSAGE)));
		
		verify(mockCourseService,times(1)).getAllCoursesFromDomain(any(String.class), any(String.class));
		verifyZeroInteractions(mockCourseService);
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.controller.CourseController#copyAllCoursesFromOneDomainToAnother(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testCopyAllCoursesFromOneDomainToAnother() throws Exception {
		when(mockCourseService.copyAllCourses(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn( Boolean.TRUE );
		when(mockMessageUtil.createMessage(any(String.class), any(Boolean.class), any(String.class))).thenReturn( createMessage(MessageType.SUCCESS,"Copied Courses from one domain to another",null) );
		
		mockMvc.perform(get(COPY_URI)
				.header(HEADER_NAME, "")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(SUCCESS)))
				.andExpect(jsonPath("$.message", is("Copied Courses from one domain to another")));
		
		verify(mockCourseService,times(1)).copyAllCourses(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class));
		verifyZeroInteractions(mockCourseService);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.controller.CourseController#copyAllCoursesFromOneDomainToAnother(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testCopyAllCoursesFromOneDomainToAnotherFailed() throws Exception {
		when(mockCourseService.copyAllCourses(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn( Boolean.FALSE );
		when(mockMessageUtil.createMessage(any(String.class), any(Boolean.class), any(String.class))).thenReturn( createMessage(MessageType.ERROR,COURSE_COPY_ERROR_MESSAGE,null) );
		
		mockMvc.perform(get(COPY_URI)
				.header(HEADER_NAME, "")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageType", is(ERROR)))
				.andExpect(jsonPath("$.message", is(COURSE_COPY_ERROR_MESSAGE)));
		
		verify(mockCourseService,times(1)).copyAllCourses(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class));
		verifyZeroInteractions(mockCourseService);
	}
	
	private Message createMessage( MessageType messageType, String desc, Object data ) {
		Message message = new Message();
		message.setMessageType( messageType );
		message.setMessage(desc);
		message.setData(data);
		return message;
	}

}
