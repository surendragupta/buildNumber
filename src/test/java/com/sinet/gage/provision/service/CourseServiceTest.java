package com.sinet.gage.provision.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sinet.gage.dlap.clients.DlapCoursesClient;
import com.sinet.gage.dlap.entities.CopyCourseRequest;
import com.sinet.gage.dlap.entities.CourseResponse;
import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.model.DistrictDomainRequest;
import com.sinet.gage.provision.model.SchoolDomainRequest;
import com.sinet.gage.provision.service.impl.CourseServiceImpl;

/**
 * 
 * @author Team Gage
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {
	
	private static final int ONE = 1;
	private static final int ZERO = 0;
	private static final String COURSE_COPY_MODE = "DerivativeCopy";
	private static final String COURSE_MODE = "Continuous";
	private static final String COURSE_REFERENCE = "LA1B";
	private static final String COURSE_CATALOG_ID = "2938123";
	private static final String COURSE_ID = "34865498";
	private static final String COURSE_GUID = "1ea00d2e-26c7-4a64-96bd-58761c9f4d3a";
	private static final String COURE_TITLE = "Language Arts 1 B - Sinet 14-15 2015-2016";
	private static final String TOKEN = "~SYC-ihGJ|ML0jX11juQd8d8BtTPAuWC3";
	private static final String DOMAIN_ID = "9101264";
	private static final String DESINATION_DOMAIN_ID = "9101262";
	private static final String EMPTY = StringUtils.EMPTY;
	private static final long NUMERIC = 0;
	
	private static final String LOGIN_PREFIX = "ces-123";
	private static final String EXTERNAL_ID = "ces-123";
	private static final String PROVIDER_DOMAIN_ID = "763829233";
	private static final String LICENSE_TYPE = "Per Seat";
	private static final String POOL_TYPE = "Fixed";
	private static final int NUMBER_OF_LICENSE = 0;
	private static final boolean PILOT_DOMAIN = Boolean.FALSE;
	private static final String PILOT_DATE = "12-14-2015";
	private static final String DOMAIN_NAME_ONE = "Canayon Elementary School";
	private static final String DISTRICT_DOMAIN_ID = "763829238";
	private static final boolean FLAG = Boolean.FALSE;

	@InjectMocks 
	private CourseService mockCourseService = new CourseServiceImpl();
	
	@Mock
	private DlapCoursesClient mockCoursesClient;
	
	@Mock
	private AppProperties mockAppProperties;
	
	private CourseService spiedCourseService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		spiedCourseService = spy( mockCourseService );
		when( mockAppProperties.getDefaultCourseSubscriptionInDays() ).thenReturn("7300");
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.CourseServiceImpl#getAllCoursesFromDomain(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetAllCoursesFromDomain() {
		List<CourseResponse> courseList = createCourseResponse();
		when( mockCoursesClient.getAllCoursesFromADomain(TOKEN, DOMAIN_ID)).thenReturn( courseList );
		
		List<CourseResponse> result = mockCourseService.getAllCoursesFromDomain(TOKEN, DOMAIN_ID);
		
		assertEquals( 1, result.size() );
		verify( mockCoursesClient, times(ONE)).getAllCoursesFromADomain(TOKEN, DOMAIN_ID);
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.CourseServiceImpl#getAllCourseNamesForDomain(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetAllCourseNamesForDomain() {
		List<CourseResponse> courseList = createCourseResponse();
		doReturn(courseList).when(spiedCourseService).getAllCoursesFromDomain(TOKEN, DOMAIN_ID);
		
		List<String> result = spiedCourseService.getAllCourseNamesForDomain(TOKEN, DOMAIN_ID);
		
		assertEquals(COURE_TITLE, result.get(ZERO));
		verify( spiedCourseService, times(ONE)).getAllCoursesFromDomain(TOKEN, DOMAIN_ID);
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.CourseServiceImpl#copyAllCourses(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCopyAllCourses () {
		when( mockCoursesClient.copyAllCoursesFromOneDomainToAnother(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(Boolean.TRUE);
		
		boolean result = mockCourseService.copyAllCourses(TOKEN, DOMAIN_ID, DESINATION_DOMAIN_ID, COURSE_COPY_MODE, null, null);
		
		assertTrue(result);
		verify(mockCoursesClient, times(ONE)).copyAllCoursesFromOneDomainToAnother(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.CourseServiceImpl#copySpecificCourses(java.lang.String, com.sinet.gage.provision.model.DistrictDomainRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCopySpecificCourses () {
		when( mockCoursesClient.copySpecificCoursesFromOneDomainToAnother(anyString(), any(CopyCourseRequest[].class))).thenReturn(Boolean.TRUE);
		
		boolean result = mockCourseService.copySpecificCourses(TOKEN, Arrays.asList(COURSE_ID), DOMAIN_ID, COURSE_COPY_MODE);
		
		assertTrue( result );
		verify( mockCoursesClient,times(ONE)).copySpecificCoursesFromOneDomainToAnother(anyString(), any(CopyCourseRequest[].class));
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.CourseServiceImpl#deleteCourses(java.lang.String, java.util.List)}.
	 */
	@Test
	public void testDeleteCourses() {
		List<String> courcesIds = Arrays.asList(COURSE_ID);
		when(mockCoursesClient.deleteCources(anyString(), anyListOf(String.class))).thenReturn(Boolean.TRUE);
		
		boolean result = mockCourseService.deleteCourses(TOKEN, courcesIds);
		
		assertTrue(result);
		verify(mockCoursesClient, times(ONE)).deleteCources(anyString(), anyListOf(String.class));
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.CourseServiceImpl#deleteAllCoursesOfDomain(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testDeleteAllCoursesOfDomain() {
		doReturn(createCourseResponse()).when( spiedCourseService).getAllCoursesFromDomain(anyString(), anyString());
		doReturn(Boolean.TRUE).when( spiedCourseService).deleteCourses(anyString(), anyListOf(String.class));
		
		boolean result = spiedCourseService.deleteAllCoursesOfDomain(TOKEN, DOMAIN_ID);
		
		assertTrue( result );
		verify( spiedCourseService, times(ONE)).getAllCoursesFromDomain(anyString(), anyString());
		verify( spiedCourseService, times(ONE)).deleteCourses(anyString(), anyListOf(String.class));
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.CourseServiceImpl#getAllCourseIdsForProvider(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetAllCourseIdsForProvider() {
		List<CourseResponse> courseList = createCourseResponse();
		when( spiedCourseService.getAllCoursesFromDomain(TOKEN,DOMAIN_ID)).thenReturn(courseList);
		
		List<String> result = mockCourseService.getAllCourseIdsForProvider(TOKEN, DOMAIN_ID);
		
		assertEquals(COURSE_ID, result.get(ZERO));
		verify( spiedCourseService, times(ONE)).getAllCoursesFromDomain(TOKEN, DOMAIN_ID);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.CourseServiceImpl#copyCourses(java.lang.String, com.sinet.gage.provision.model.DistrictDomainRequest, java.lang.String)}.
	 */
	@Ignore
	public void testCopyCoursesForDistrictDomainAllCourses () {
		DistrictDomainRequest request = new DistrictDomainRequest();
		request.setFullSubscription(Boolean.TRUE);
		request.setCourseCatalogs(Arrays.asList(COURSE_CATALOG_ID));
		
		doReturn(Boolean.TRUE).when( spiedCourseService).copySpecificCourses(anyString(), anyListOf(String.class), anyString(), anyString());
		
		boolean result = spiedCourseService.copyCourses(TOKEN, request, DOMAIN_ID);
		
		assertTrue( result );
		verify( spiedCourseService, times(ONE)).copySpecificCourses(anyString(), anyListOf(String.class), anyString(), anyString());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.CourseServiceImpl#copyCourses(java.lang.String, com.sinet.gage.provision.model.DistrictDomainRequest, java.lang.String)}.
	 */
	@Test
	public void testCopyCoursesForDistrictDomainEmptyCatalog () {
		DistrictDomainRequest request = new DistrictDomainRequest();
		request.setFullSubscription(Boolean.TRUE);
		request.setCourseCatalogs(new ArrayList<String>());
		request.setCourseSelection(new ArrayList<String>());
		
		doReturn(Boolean.TRUE).when( spiedCourseService).copySpecificCourses(anyString(), anyListOf(String.class), anyString(), anyString());
		
		boolean result = spiedCourseService.copyCourses(TOKEN, request, DOMAIN_ID);
		
		assertTrue( result );
		verify( spiedCourseService, times(ZERO)).copySpecificCourses(anyString(), anyListOf(String.class), anyString(), anyString());
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.CourseServiceImpl#copyCourses(java.lang.String, com.sinet.gage.provision.model.DistrictDomainRequest, java.lang.String)}.
	 */
	@Test
	public void testCopyCoursesForDistrictDomainSpecificCourses () {
		DistrictDomainRequest request = new DistrictDomainRequest();
		request.setFullSubscription(Boolean.FALSE);
		request.setCourseSelection(Arrays.asList(DOMAIN_ID, COURSE_ID));
		List<CourseResponse> listOfCourse = new ArrayList<>();
		CourseResponse courseResponse = new CourseResponse(COURSE_ID, EMPTY, EMPTY, EMPTY, EMPTY, NUMERIC, DOMAIN_ID, EMPTY, EMPTY, EMPTY, NUMERIC, EMPTY, NUMERIC, NUMERIC, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY);
		listOfCourse.add(courseResponse);		
		
		doReturn(listOfCourse).when(spiedCourseService).getAllCoursesFromDomain(anyString(), anyString());
		
		doReturn(Boolean.TRUE).when( spiedCourseService).copySpecificCourses(anyString(), anyListOf(String.class), anyString(), anyString());
		
		boolean result = spiedCourseService.copyCourses(TOKEN, request, DOMAIN_ID);
		
		assertTrue( result );
		verify( spiedCourseService, times(ONE)).copySpecificCourses(anyString(), anyListOf(String.class), anyString(), anyString());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.CourseServiceImpl#copyCourses(java.lang.String, com.sinet.gage.provision.model.DistrictDomainRequest, java.lang.String)}.
	 */
	@Test
	public void testCopyCoursesForDistrictDomainZeroCourses () {
		DistrictDomainRequest request = new DistrictDomainRequest();
		request.setFullSubscription(Boolean.FALSE);
		request.setCourseSelection(new ArrayList<String>());
		
		doReturn(Boolean.TRUE).when( spiedCourseService).copySpecificCourses(anyString(), anyListOf(String.class), anyString(), anyString());
		
		boolean result = spiedCourseService.copyCourses(TOKEN, request, DOMAIN_ID);
		
		assertTrue( result );
		verify( spiedCourseService, times(ZERO)).copySpecificCourses(anyString(), anyListOf(String.class), anyString(), anyString());
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.CourseServiceImpl#copyCourses(String, String, String)}.
	 */
	@Ignore
	public void testCopyCoursesForSchoolDomainAllCourses () {
		SchoolDomainRequest request = new SchoolDomainRequest();
		request.setFullSubscription(Boolean.TRUE);
		request.setCourseCatalogs(Arrays.asList(COURSE_CATALOG_ID));
		
		doReturn(Boolean.TRUE).when( spiedCourseService).copyAllCourses(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
		
		boolean result = spiedCourseService.copyCourses(TOKEN, request, DOMAIN_ID);
		
		assertTrue( result );
		verify( spiedCourseService,times(ONE)).copyAllCourses(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.CourseServiceImpl#copyCourses(java.lang.String, com.sinet.gage.provision.model.DistrictDomainRequest, java.lang.String)}.
	 */
	@Test
	public void testCopyCoursesForSchoolDomainEmptyCatalog () {
		SchoolDomainRequest request = new SchoolDomainRequest();
		request.setFullSubscription(Boolean.TRUE);
		request.setCourseCatalogs(new ArrayList<String>());
		
		doReturn(Boolean.TRUE).when( spiedCourseService).copySpecificCourses(anyString(), anyListOf(String.class), anyString(), anyString());
		
		boolean result = spiedCourseService.copyCourses(TOKEN, request, DOMAIN_ID);
		
		assertTrue( result );
		verify( spiedCourseService, times(ZERO)).copySpecificCourses(anyString(), anyListOf(String.class), anyString(), anyString());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.CourseServiceImpl#copyCourses(java.lang.String, com.sinet.gage.provision.model.DistrictDomainRequest, java.lang.String)}.
	 */
	@Test
	public void testCopyCoursesForSchoolDomainSpecificCourses () {
		SchoolDomainRequest request = new SchoolDomainRequest();
		request.setFullSubscription(Boolean.FALSE);
		request.setDistrictDomainId(Long.parseLong(DOMAIN_ID));
		request.setCourseSelection(Arrays.asList(DOMAIN_ID, COURSE_ID));
		request.setCourseCatalogs(Arrays.asList(DOMAIN_ID));
		List<CourseResponse> listOfCourse = new ArrayList<>();
		CourseResponse courseResponse = new CourseResponse(COURSE_ID, EMPTY, EMPTY, EMPTY, EMPTY, NUMERIC, DOMAIN_ID, EMPTY, EMPTY, EMPTY, NUMERIC, EMPTY, NUMERIC, NUMERIC, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY);
		listOfCourse.add(courseResponse);		
		
		doReturn(listOfCourse).when(spiedCourseService).getAllCoursesFromDomain(anyString(), anyString());
		doReturn(Boolean.TRUE).when( spiedCourseService).copySpecificCourses(anyString(), anyListOf(String.class), anyString(), anyString());
		
		boolean result = spiedCourseService.copyCourses(TOKEN, request, DOMAIN_ID);
		
		assertTrue( result );
	}
	
	private List<CourseResponse> createCourseResponse() {
		List<CourseResponse> courseList = new ArrayList<>();
		CourseResponse courseResponse = new CourseResponse(COURSE_ID, COURE_TITLE, COURSE_CATALOG_ID, COURSE_REFERENCE, COURSE_GUID, ZERO, null, COURSE_MODE, null, null, 365, null, ZERO, ZERO, null, null, null, null, null);
		courseList.add(courseResponse);
		return courseList;
	}
}
