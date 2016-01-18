/**
 * 
 */
package com.sinet.gage.provision.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.sinet.gage.dlap.entities.Customization;
import com.sinet.gage.dlap.entities.DomainResponse;
import com.sinet.gage.dlap.entities.EdivateLearn;
import com.sinet.gage.dlap.entities.SubscriptionResponse;
import com.sinet.gage.dlap.entities.UserRequest;
import com.sinet.gage.dlap.exception.AvailabilityLicenseException;
import com.sinet.gage.dlap.exception.DomainDuplicateException;
import com.sinet.gage.dlap.exception.DomainNameDuplicateException;
import com.sinet.gage.dlap.exception.UserDuplicateException;
import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.data.model.District;
import com.sinet.gage.provision.data.model.School;
import com.sinet.gage.provision.model.DistrictDomainRequest;
import com.sinet.gage.provision.model.Domain;
import com.sinet.gage.provision.model.DomainType;
import com.sinet.gage.provision.model.EditSchoolDomainRequest;
import com.sinet.gage.provision.model.SchoolDomainRequest;
import com.sinet.gage.provision.service.impl.CourseServiceImpl;
import com.sinet.gage.provision.service.impl.DistrictServiceImpl;
import com.sinet.gage.provision.service.impl.DomainFacadeImpl;
import com.sinet.gage.provision.service.impl.DomainServiceImpl;
import com.sinet.gage.provision.service.impl.ResourceServiceImpl;
import com.sinet.gage.provision.service.impl.SubscriptionServiceImpl;
import com.sinet.gage.provision.service.impl.UserServiceImpl;
import com.sinet.gage.provision.util.DateUtil;
import com.sinet.gage.provision.util.PojoMapper;

/**
 * @author Team.Gage
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DomainFacadeTest {
	private static final String LOGIN_PREFIX = "ces-123";
	private static final String EXTERNAL_ID = "ces-123";
	private static final String COURSE_ID = "76374643";
	private static final String PROVIDER_DOMAIN_ID = "763829233";
	private static final String STATE_DOMAIN_NAME_ONE = "Arizona";
	private static final boolean FULL_SUBSCRIPTION = Boolean.FALSE;
	private static final String LICENSE_TYPE = "Per Seat";
	private static final String POOL_TYPE = "Fixed";
	private static final int NUMBER_OF_LICENSE = 0;
	private static final boolean PILOT_DOMAIN = Boolean.TRUE;
	private static final String SUBSCRIPTION_DATE = "11-14-2015";
	private static final String PILOT_DATE = "12-14-2015";
	private static final String DOMAIN_NAME_ONE = "Canayon Elementary School";
	private static final String TOKEN = "~SYC-jihGJ|ML0jX11juQd8d8BtTPAuWC2";
	private static final String BLANK = "";
	private static final Long FLAG = -1l;
	private static final String USER_ID = "1234";
	private static final String DOMAIN_ID = "125609";
	private static final String NUMBER = "10";
	private static final String DISTRICT_DOMAIN_ID = "763829238";
	private static final String PARENT_DOMAIN_ID = "236286";
	
	@InjectMocks
	private DomainFacade mockDomainFacade = new DomainFacadeImpl();
	
	@Mock
	private DomainService mockDomainService = new DomainServiceImpl();
	
	@Mock
	private ResourceService mockResourceService = new ResourceServiceImpl();
	
	@Mock
	private CourseService mockCourseService = new CourseServiceImpl();
	
	@Mock
	private SubscriptionService mockSubscriptionService = new SubscriptionServiceImpl();
	
	@Mock
	private UserService mockUserService = new UserServiceImpl();
	
	@Mock
	private DistrictService mockDistrictService = new DistrictServiceImpl();
	
	@Mock
	private AppProperties mockProperties;	

	@Mock
	DateUtil mockDateUtil;
	
	@Mock
	PojoMapper mockMapToObject;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainFacadeImpl#createDistrictDomain(java.lang.String, com.sinet.gage.provision.model.DistrictDomainRequest)}.
	 */
	@Test
	public void testCreateDistrictDomain() throws DomainDuplicateException, DomainNameDuplicateException, UserDuplicateException {
		List<Integer> domainIds = new ArrayList<>();
		domainIds.add(Integer.parseInt(DOMAIN_ID));
		DistrictDomainRequest districtDomainRequest = createDistrictDomain();
		UserRequest user = new UserRequest(BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK);
		user.setUserId(USER_ID);
		District district = new District();	
		
		mockBeforeCreateDistrictDomainCall(domainIds, user, district);
		
		boolean result = mockDomainFacade.createDistrictDomain(TOKEN, districtDomainRequest);
		
		assertTrue( result );		

		verify(mockMapToObject).mapToDistrict(any(DistrictDomainRequest.class), any(UserRequest.class), anyString());
		verify(mockDomainService).createDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class));
		verify(mockUserService).createAdminUser(anyString(), anyString());		
		verify(mockDistrictService).insertDistrict(any(District.class));
		verify(mockResourceService).copyResources(anyString(), anyString(), anyString());
		verify(mockCourseService).copyCourses(anyString(), any(DistrictDomainRequest.class), anyString());
		verify(mockSubscriptionService).createDistrictDomainSubscription(anyString(), any(DistrictDomainRequest.class), anyString());
	}	

	@Test
	public void testCreateDistrictOfAnyUnsuccessfulAction() throws DomainDuplicateException, DomainNameDuplicateException, UserDuplicateException, AvailabilityLicenseException {
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);
		List<Integer> domainIds = new ArrayList<>();
		domainIds.add(Integer.parseInt(DOMAIN_ID));
		DistrictDomainRequest districtDomainRequest = createDistrictDomain();
		UserRequest user = new UserRequest(BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK);
		user.setUserId(USER_ID);
		District district = new District();
		
		when(mockMapToObject.mapToDistrict(any(DistrictDomainRequest.class), any(UserRequest.class), anyString())).thenReturn(district);
		when(mockDomainService.getAvailabilityOfLicense(anyString(), any(SchoolDomainRequest.class), anyString())).thenReturn(Boolean.TRUE);
		when(mockDomainService.createDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenReturn(domainIds);
		when(mockUserService.createAdminUser(anyString(), anyString())).thenReturn(user);		
		when(mockDistrictService.insertDistrict(any(District.class))).thenReturn(district);
		when(mockResourceService.copyResources(anyString(), anyString(), anyString())).thenReturn( Boolean.FALSE );
		
		boolean result = mockDomainFacade.createDistrictDomain(TOKEN, districtDomainRequest);
		
		assertFalse( result );
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#createDomain(java.lang.String, java.util.List, com.sinet.gage.dlap.entities.EdivateLearn, java.util.List)}.
	 * @throws DomainDuplicateException 
	 * @throws DomainNameDuplicateException 
	 * @throws UserDuplicateException 
	 */
	@SuppressWarnings("unchecked")
	@Test(expected=DomainNameDuplicateException.class)
	public void testCreateDomainFailedDomainDuplicateName () throws DomainDuplicateException, DomainNameDuplicateException, UserDuplicateException {
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);
		List<Integer> domainIds = new ArrayList<>();
		domainIds.add(Integer.parseInt(DOMAIN_ID));
		DistrictDomainRequest districtDomainRequest = createDistrictDomain();
		UserRequest user = new UserRequest(BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK);
		user.setUserId(USER_ID);
		District district = new District();
		
		when(mockMapToObject.mapToDistrict(any(DistrictDomainRequest.class), any(UserRequest.class), anyString())).thenReturn(district);
		when(mockDomainService.getAvailabilityOfLicense(anyString(), any(SchoolDomainRequest.class), anyString())).thenReturn(Boolean.TRUE);
		when(mockDomainService.createDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenThrow(DomainNameDuplicateException.class);
		
		mockDomainFacade.createDistrictDomain(TOKEN, districtDomainRequest);
		
	}
	
	/**
	 * Test case : This test case id for testCreateDistrictDomain when the passed list of domain ids list is empty
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainFacadeImpl#createDistrictDomain(java.lang.String, com.sinet.gage.provision.model.DistrictDomainRequest)}.
	 */
	@Test
	public void testCreateDistrictDomainForEmptyDomain() throws DomainDuplicateException, DomainNameDuplicateException, UserDuplicateException {
		List<Integer> domainIds = new ArrayList<>();
		DistrictDomainRequest districtDomainRequest = createDistrictDomain();
		UserRequest user = new UserRequest(BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK);
		user.setUserId(USER_ID);
		District district = new District();	
		
		mockBeforeCreateDistrictDomainCall(domainIds, user, district);
		
		boolean result = mockDomainFacade.createDistrictDomain(TOKEN, districtDomainRequest);
		
		assertFalse( result );		
	}
	
	@Test
	public void testCreateDistrictDomainForUserDuplicateException() throws DomainDuplicateException, DomainNameDuplicateException, UserDuplicateException{
		List<Integer> domainIds = new ArrayList<>();
		domainIds.add( Integer.parseInt(DOMAIN_ID) );
		DistrictDomainRequest districtDomainRequest = createDistrictDomain();
		UserRequest user = new UserRequest(BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK);
		user.setUserId(USER_ID);
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);	
		
		when(mockMapToObject.mapToDomainList(any(DistrictDomainRequest.class))).thenReturn(domainList);
		when(mockDomainService.createDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenReturn(domainIds);
		when(mockUserService.createAdminUser(anyString(), anyString())).thenThrow(new UserDuplicateException(""));
		
		
		boolean success = mockDomainFacade.createDistrictDomain(TOKEN, districtDomainRequest);
		
		assertFalse( success );
		
		verify( mockMapToObject ).mapToDomainList(any(DistrictDomainRequest.class));
		verify( mockDomainService ).createDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class));
		verify( mockUserService ).createAdminUser(anyString(), anyString());
	}
	
	@Test
	public void testCreateDistrictDomainFailToPersistAdminUser() throws DomainDuplicateException, DomainNameDuplicateException, UserDuplicateException{
		List<Integer> domainIds = new ArrayList<>();
		domainIds.add( Integer.parseInt(DOMAIN_ID) );
		DistrictDomainRequest districtDomainRequest = createDistrictDomain();
		UserRequest user = new UserRequest(BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK);
		user.setUserId(USER_ID);
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);	
		
		when(mockMapToObject.mapToDomainList(any(DistrictDomainRequest.class))).thenReturn(domainList);
		when(mockDomainService.createDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenReturn(domainIds);
		when(mockUserService.createAdminUser(anyString(), anyString())).thenReturn(null);	
		
		
		boolean success = mockDomainFacade.createDistrictDomain(TOKEN, districtDomainRequest);
		
		assertFalse( success );
		
		verify( mockMapToObject ).mapToDomainList(any(DistrictDomainRequest.class));
		verify( mockDomainService ).createDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class));
		verify( mockUserService ).createAdminUser(anyString(), anyString());
	}
	

	private void mockBeforeCreateDistrictDomainCall(List<Integer> domainIds, UserRequest user, District district)
			throws DomainDuplicateException, DomainNameDuplicateException, UserDuplicateException {
		when(mockMapToObject.mapToDistrict(any(DistrictDomainRequest.class), any(UserRequest.class), anyString())).thenReturn(district);
		when(mockDomainService.createDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenReturn(domainIds);
		when(mockUserService.createAdminUser(anyString(), anyString())).thenReturn(user);		
		when(mockDistrictService.insertDistrict(any(District.class))).thenReturn(district);
		when(mockResourceService.copyResources(anyString(), anyString(), anyString())).thenReturn( Boolean.TRUE );
		when(mockCourseService.copyCourses(anyString(), any(DistrictDomainRequest.class), anyString())).thenReturn( Boolean.TRUE );
		when(mockSubscriptionService.createDistrictDomainSubscription(anyString(), any(DistrictDomainRequest.class), anyString())).thenReturn( Boolean.TRUE );
	}

		/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainFacadeImpl#editDistrictDomain(java.lang.String, com.sinet.gage.provision.model.DistrictDomainRequest, java.lang.String)}.
	 */
	@Test
	public void testEditDistrictDomain() throws DomainNameDuplicateException {		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);
		DistrictDomainRequest districtDomainRequest = createDistrictDomain();
		districtDomainRequest.setSubscriptionEndDate(null);
		District district = new District();
		
		when(mockMapToObject.mapToDomainList(any(DistrictDomainRequest.class))).thenReturn(domainList);
		when(mockDomainService.updateDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenReturn(Boolean.TRUE);
		when(mockCourseService.copyCourses(anyString(), any(DistrictDomainRequest.class), anyString())).thenReturn(Boolean.TRUE);
		when(mockSubscriptionService.createDistrictDomainSubscription(anyString(), any(DistrictDomainRequest.class), anyString())).thenReturn( Boolean.TRUE );
		when(mockMapToObject.mapToDistrictForEditing(any(DistrictDomainRequest.class), anyString())).thenReturn(district);
		when(mockDistrictService.updateDistrict(any(District.class))).thenReturn(district);
		
		boolean result = mockDomainFacade.editDistrictDomain(TOKEN, districtDomainRequest, DOMAIN_ID);
		
		assertTrue( result );
		
		verify(mockMapToObject).mapToDomainList(any(DistrictDomainRequest.class));
		verify(mockDomainService).updateDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class));
		verify(mockCourseService).copyCourses(anyString(), any(DistrictDomainRequest.class), anyString());
		verify(mockSubscriptionService).createDistrictDomainSubscription(anyString(), any(DistrictDomainRequest.class), anyString());
		verify(mockMapToObject).mapToDistrictForEditing(any(DistrictDomainRequest.class), anyString());
		verify(mockDistrictService).updateDistrict(any(District.class));
	}

	@Test
	public void testEditDistrictOfAnyUnsuccessfulAction() throws DomainNameDuplicateException, AvailabilityLicenseException {
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);
		DistrictDomainRequest districtDomainRequest = createDistrictDomain();
		
		when(mockMapToObject.mapToDomainList(any(DistrictDomainRequest.class))).thenReturn(domainList);
		when(mockDomainService.updateDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenReturn(Boolean.FALSE);
		
		boolean result = mockDomainFacade.editDistrictDomain(TOKEN, districtDomainRequest, DOMAIN_ID);
		
		assertFalse( result );
	}
	
	@Test
	public void testEditDistrictForSubscriptionEndDate() throws DomainNameDuplicateException, AvailabilityLicenseException {
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);
		DistrictDomainRequest districtDomainRequest = createDistrictDomain();
		districtDomainRequest.setSubscriptionEndDate("");
		
		when(mockMapToObject.mapToDomainList(any(DistrictDomainRequest.class))).thenReturn(domainList);
		when(mockDomainService.updateDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenReturn(Boolean.FALSE);
		
		boolean result = mockDomainFacade.editDistrictDomain(TOKEN, districtDomainRequest, DOMAIN_ID);
		
		assertFalse( result );
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#editDomain(java.lang.String, DistrictDomainRequest, java.lang.String)}.
	 * @throws DomainDuplicateException 
	 * @throws DomainNameDuplicateException 
	 * @throws UserDuplicateException 
	 */
	@SuppressWarnings("unchecked")
	@Test(expected=DomainNameDuplicateException.class)
	public void testEditDomainFailedDomainDuplicateName () throws DomainDuplicateException, DomainNameDuplicateException, UserDuplicateException {
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);
		DistrictDomainRequest districtDomainRequest = createDistrictDomain();
		
		when(mockMapToObject.mapToDomainList(any(DistrictDomainRequest.class))).thenReturn(domainList);
		when(mockDomainService.updateDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenThrow(DomainNameDuplicateException.class);

		mockDomainFacade.editDistrictDomain(TOKEN, districtDomainRequest, DOMAIN_ID);
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainFacadeImpl#createSchool(java.lang.String, com.sinet.gage.provision.model.SchoolDomainRequest)}.
	 */
	@Test
	public void testCreateSchool() throws DomainDuplicateException, DomainNameDuplicateException, AvailabilityLicenseException, UserDuplicateException {
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);
		List<Integer> domainIds = new ArrayList<>();
		domainIds.add(Integer.parseInt(DOMAIN_ID));
		SchoolDomainRequest schoolDomainRequest = createSchoolDomain();
		UserRequest user = new UserRequest(BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK);
		user.setUserId(USER_ID);
		School school = new School();
		
		when(mockMapToObject.mapToDomainList(any(DistrictDomainRequest.class))).thenReturn(domainList);
		when(mockDomainService.getAvailabilityOfLicense(anyString(), any(SchoolDomainRequest.class), anyString())).thenReturn(Boolean.TRUE);
		when(mockDomainService.createDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenReturn(domainIds);
		when(mockUserService.createAdminUser(anyString(), anyString())).thenReturn(user);		
		when(mockDistrictService.insertSchool(any(Long.class), any(School.class))).thenReturn(school);
		when(mockResourceService.copyResources(anyString(), anyString(), anyString())).thenReturn( Boolean.TRUE );
		when(mockCourseService.copyCourses(anyString(), any(SchoolDomainRequest.class), anyString())).thenReturn( Boolean.TRUE );
		when(mockSubscriptionService.createSchoolDomainSubscription(anyString(), any(SchoolDomainRequest.class), anyString())).thenReturn( Boolean.TRUE );
		
		boolean result = mockDomainFacade.createSchool(TOKEN, schoolDomainRequest);
		
		assertTrue( result );
		
		verify(mockDomainService).getAvailabilityOfLicense(anyString(), any(SchoolDomainRequest.class), anyString());
		verify(mockDomainService).createDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class));
		verify(mockUserService).createAdminUser(anyString(), anyString());	
		verify(mockDistrictService).insertSchool(any(Long.class), any(School.class));
		verify(mockResourceService).copyResources(anyString(), anyString(), anyString());
		verify(mockCourseService).copyCourses(anyString(), any(SchoolDomainRequest.class), anyString());
		verify(mockSubscriptionService).createSchoolDomainSubscription(anyString(), any(SchoolDomainRequest.class), anyString());
	}	
	
	@Test(expected = AvailabilityLicenseException.class)
	public void testCreateSchoolNonAvailabilityLicense() throws DomainNameDuplicateException, AvailabilityLicenseException, DomainDuplicateException {
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);
		SchoolDomainRequest schoolDomainRequest = createSchoolDomain();
		
		when(mockMapToObject.mapToDomainList(any(DistrictDomainRequest.class))).thenReturn(domainList);
		when(mockDomainService.getAvailabilityOfLicense(anyString(), any(EditSchoolDomainRequest.class), anyString())).thenReturn(Boolean.FALSE);
		
		boolean result = mockDomainFacade.createSchool(TOKEN, schoolDomainRequest);
		
		assertFalse( result );
		
		verify(mockDomainService).getAvailabilityOfLicense(anyString(), any(EditSchoolDomainRequest.class), anyString());
	}
	
	@Test(expected = DomainDuplicateException.class)
	public void testCreateSchoolForDomainDuplicateException() throws DomainNameDuplicateException, AvailabilityLicenseException, DomainDuplicateException {
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);
		SchoolDomainRequest schoolDomainRequest = createSchoolDomain();
		
		when(mockMapToObject.mapToDomainList(any(DistrictDomainRequest.class))).thenReturn(domainList);
		when(mockDomainService.getAvailabilityOfLicense(anyString(), any(EditSchoolDomainRequest.class), anyString())).thenReturn(Boolean.TRUE);
		when(mockDomainService.createDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenThrow(new DomainDuplicateException(""));
		
		mockDomainFacade.createSchool(TOKEN, schoolDomainRequest);
		
		}
	
	@Test
	public void testCreateSchoolCreateDomainFail() throws DomainDuplicateException, DomainNameDuplicateException, AvailabilityLicenseException {
		List<Integer> domainIds = new ArrayList<>();
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);
		SchoolDomainRequest schoolDomainRequest = createSchoolDomain();
		
		when(mockMapToObject.mapToDomainList(any(DistrictDomainRequest.class))).thenReturn(domainList);
		when(mockDomainService.getAvailabilityOfLicense(anyString(), any(EditSchoolDomainRequest.class), anyString())).thenReturn(Boolean.TRUE);
		when(mockDomainService.createDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenReturn(domainIds);
		
		boolean result = mockDomainFacade.createSchool(TOKEN, schoolDomainRequest);
		
		assertFalse( result );
		
		verify(mockDomainService).getAvailabilityOfLicense(anyString(), any(EditSchoolDomainRequest.class), anyString());
	}

	@Test
	public void testCreateSchoolOfAnyUnsuccessfulAction() throws DomainDuplicateException, DomainNameDuplicateException, UserDuplicateException, AvailabilityLicenseException {
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);
		List<Integer> domainIds = new ArrayList<>();
		domainIds.add(Integer.parseInt(DOMAIN_ID));
		SchoolDomainRequest schoolDomainRequest = createSchoolDomain();
		UserRequest user = new UserRequest(BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK);
		user.setUserId(USER_ID);
		School school = new School();
		
		when(mockMapToObject.mapToDomainList(any(DistrictDomainRequest.class))).thenReturn(domainList);
		when(mockDomainService.getAvailabilityOfLicense(anyString(), any(SchoolDomainRequest.class), anyString())).thenReturn(Boolean.TRUE);
		when(mockDomainService.createDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenReturn(domainIds);
		when(mockUserService.createAdminUser(anyString(), anyString())).thenReturn(user);		
		when(mockDistrictService.insertSchool(any(Long.class), any(School.class))).thenReturn(school);
		when(mockResourceService.copyResources(anyString(), anyString(), anyString())).thenReturn( Boolean.FALSE );
		
		boolean result = mockDomainFacade.createSchool(TOKEN, schoolDomainRequest);
		
		assertFalse( result );
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainFacadeImpl#editSchool(java.lang.String, com.sinet.gage.provision.model.EditSchoolDomainRequest)}.
	 * @throws DomainNameDuplicateException
	 * @throws AvailabilityLicenseException
	 */
	@Test
	public void testEditSchool() throws DomainNameDuplicateException, AvailabilityLicenseException {
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);
		DomainResponse response = new DomainResponse(DOMAIN_ID, DOMAIN_NAME_ONE, BLANK, DISTRICT_DOMAIN_ID, BLANK, BLANK, FLAG, BLANK, USER_ID, BLANK, USER_ID, BLANK, null);
		EdivateLearn edivateLearn = new EdivateLearn(BLANK, BLANK, NUMBER, BLANK, Boolean.FALSE, BLANK, BLANK, Boolean.FALSE, BLANK, BLANK);
		Customization customization = new Customization(edivateLearn, null, null, null, null, null, null, null, null, null);
		School school = new School();
		EditSchoolDomainRequest editSchoolDomainRequest = createEditSchoolDomainRequest();
		
		when(mockDomainService.findDomain(anyString(), any(Long.class))).thenReturn(response);
		when(mockDomainService.getDomainData(anyString(), any(Long.class))).thenReturn(customization);
		when(mockDomainService.getAvailabilityOfLicense(anyString(), any(EditSchoolDomainRequest.class), anyString())).thenReturn(Boolean.TRUE);
		when(mockMapToObject.mapToDomains(any(EditSchoolDomainRequest.class))).thenReturn(domainList);
		when(mockDomainService.updateDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenReturn(Boolean.TRUE);
		when(mockCourseService.copyCourses(anyString(), any(SchoolDomainRequest.class), anyString())).thenReturn(Boolean.TRUE);
		when(mockSubscriptionService.createSchoolDomainSubscription(anyString(), any(EditSchoolDomainRequest.class), anyString())).thenReturn( Boolean.TRUE );
		when(mockMapToObject.mapToSchoolForEditing(any(EditSchoolDomainRequest.class))).thenReturn(school);
		when(mockDistrictService.updateSchool(any(Long.class), any(School.class))).thenReturn(school);
		
		boolean result = mockDomainFacade.editSchool(TOKEN, editSchoolDomainRequest);
		
		assertTrue( result );
		
		verify(mockDomainService).findDomain(anyString(), any(Long.class));
		verify(mockDomainService).getDomainData(anyString(), any(Long.class));
		verify(mockDomainService).getAvailabilityOfLicense(anyString(), any(EditSchoolDomainRequest.class), anyString());
		verify(mockMapToObject).mapToDomains(any(EditSchoolDomainRequest.class));
		verify(mockDomainService).updateDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class));
		verify(mockCourseService).copyCourses(anyString(), any(SchoolDomainRequest.class), anyString());
		//verify(mockSubscriptionService).createSchoolDomainSubscription(anyString(), any(EditSchoolDomainRequest.class), anyString());
		verify(mockMapToObject).mapToSchoolForEditing(any(EditSchoolDomainRequest.class));
		verify(mockDistrictService).updateSchool(any(Long.class), any(School.class));		
	}
	
	@Test(expected = AvailabilityLicenseException.class)
	public void testEditSchoolNonAvailabilityLicense() throws DomainNameDuplicateException, AvailabilityLicenseException {
		DomainResponse response = new DomainResponse(DOMAIN_ID, DOMAIN_NAME_ONE, BLANK, DISTRICT_DOMAIN_ID, BLANK, BLANK, FLAG, BLANK, USER_ID, BLANK, USER_ID, BLANK, null);
		EdivateLearn edivateLearn = new EdivateLearn(BLANK, BLANK, NUMBER, BLANK, Boolean.FALSE, BLANK, BLANK, Boolean.FALSE, BLANK, BLANK);
		Customization customization = new Customization(edivateLearn, null, null, null, null, null, null, null, null, null);
		EditSchoolDomainRequest editSchoolDomainRequest = createEditSchoolDomainRequest();
		
		when(mockDomainService.findDomain(anyString(), any(Long.class))).thenReturn(response);
		when(mockDomainService.getDomainData(anyString(), any(Long.class))).thenReturn(customization);
		when(mockDomainService.getAvailabilityOfLicense(anyString(), any(EditSchoolDomainRequest.class), anyString())).thenReturn(Boolean.FALSE);
		
		boolean result = mockDomainFacade.editSchool(TOKEN, editSchoolDomainRequest);
		
		assertFalse( result );
		
		verify(mockDomainService).findDomain(anyString(), any(Long.class));
		verify(mockDomainService).getDomainData(anyString(), any(Long.class));
		verify(mockDomainService).getAvailabilityOfLicense(anyString(), any(EditSchoolDomainRequest.class), anyString());
	}

	@Test(expected = AvailabilityLicenseException.class)
	public void testEditSchoolToAnotherDomainWithNonAvailabilityLicense() throws DomainNameDuplicateException, AvailabilityLicenseException {
		DomainResponse response2 = new DomainResponse(DOMAIN_ID, DOMAIN_NAME_ONE, BLANK, PARENT_DOMAIN_ID, BLANK, BLANK, FLAG, BLANK, USER_ID, BLANK, USER_ID, BLANK, null);
		EdivateLearn edivateLearn = new EdivateLearn(BLANK, BLANK, NUMBER, BLANK, Boolean.FALSE, BLANK, BLANK, Boolean.FALSE, BLANK, BLANK);
		Customization customization = new Customization(edivateLearn, null, null, null, null, null, null, null, null, null);
		EditSchoolDomainRequest editSchoolDomainRequest = createEditSchoolDomainRequest();
		
		when(mockDomainService.findDomain(anyString(), any(Long.class))).thenReturn(response2);
		when(mockDomainService.getDomainData(anyString(), any(Long.class))).thenReturn(customization);
		when(mockDomainService.getAvailabilityOfLicense(anyString(), any(EditSchoolDomainRequest.class), anyString())).thenReturn(Boolean.FALSE);
		
		boolean result = mockDomainFacade.editSchool(TOKEN, editSchoolDomainRequest);
		
		assertFalse( result );
	}
	
	@Test(expected = DomainNameDuplicateException.class)
	public void testEditSchoolForDomainNameDuplicateException() throws DomainNameDuplicateException, AvailabilityLicenseException {
		DomainResponse response2 = new DomainResponse(DOMAIN_ID, DOMAIN_NAME_ONE, BLANK, PARENT_DOMAIN_ID, BLANK, BLANK, FLAG, BLANK, USER_ID, BLANK, USER_ID, BLANK, null);
		EdivateLearn edivateLearn = new EdivateLearn(BLANK, BLANK, NUMBER, BLANK, Boolean.FALSE, BLANK, BLANK, Boolean.FALSE, BLANK, BLANK);
		Customization customization = new Customization(edivateLearn, null, null, null, null, null, null, null, null, null);
		EditSchoolDomainRequest editSchoolDomainRequest = createEditSchoolDomainRequest();
		
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);
		when(mockDomainService.findDomain(anyString(), any(Long.class))).thenReturn(response2);
		when(mockDomainService.getDomainData(anyString(), any(Long.class))).thenReturn(customization);
		when(mockDomainService.getAvailabilityOfLicense(anyString(), any(EditSchoolDomainRequest.class), anyString())).thenReturn(Boolean.TRUE);
		when(mockMapToObject.mapToDomains(any(EditSchoolDomainRequest.class))).thenReturn(domainList);
		when(mockDomainService.updateDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenThrow(new DomainNameDuplicateException(""));
		
		 mockDomainFacade.editSchool(TOKEN, editSchoolDomainRequest);
		
	}
	
	@Test
	public void testEditSchoolOfAnyUnsuccessfulAction() throws DomainNameDuplicateException, AvailabilityLicenseException {
		List<Domain> domainList = new ArrayList<>();
		Domain domain = new Domain();
		domainList.add(domain);
		DomainResponse response2 = new DomainResponse(DOMAIN_ID, DOMAIN_NAME_ONE, BLANK, PARENT_DOMAIN_ID, BLANK, BLANK, FLAG, BLANK, USER_ID, BLANK, USER_ID, BLANK, null);
		EdivateLearn edivateLearn = new EdivateLearn(BLANK, BLANK, NUMBER, BLANK, Boolean.FALSE, BLANK, BLANK, Boolean.FALSE, BLANK, BLANK);
		Customization customization = new Customization(edivateLearn, null, null, null, null, null, null, null, null, null);
		EditSchoolDomainRequest editSchoolDomainRequest = createEditSchoolDomainRequest();
		
		when(mockDomainService.findDomain(anyString(), any(Long.class))).thenReturn(response2);
		when(mockDomainService.getDomainData(anyString(), any(Long.class))).thenReturn(customization);
		when(mockDomainService.getAvailabilityOfLicense(anyString(), any(EditSchoolDomainRequest.class), anyString())).thenReturn(Boolean.TRUE);
		when(mockMapToObject.mapToDomains(any(EditSchoolDomainRequest.class))).thenReturn(domainList);
		when(mockDomainService.updateDomain(anyString(), anyListOf(Domain.class), any(EdivateLearn.class), anyListOf(String.class), any(DomainType.class))).thenReturn(Boolean.FALSE);
		
		boolean result = mockDomainFacade.editSchool(TOKEN, editSchoolDomainRequest);
		
		assertFalse( result );
	}	
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainFacadeImpl#getSubscriptionsForDomain(java.lang.String, java.lang.String)}.
	 */
	@Test
 	public void testGetSubscriptionsForDomain() {
		List<SubscriptionResponse> subResponse = new ArrayList<>();
		SubscriptionResponse subscriptionResponse = new SubscriptionResponse("345456", "763703", "D", "11-16-2015", "11-16-2016", "1", "-1", "11-16-2015", "11-16-2015", "", "Subscription Name", "Title");
		subResponse.add(subscriptionResponse);
		List<String> cList = new ArrayList<>();
		cList.add(COURSE_ID);
				
		when(mockSubscriptionService.getSubscriptionsForDomain(anyString(), anyString())).thenReturn(subResponse);
		when(mockCourseService.getAllCourseIdsForProvider(anyString(), anyString())).thenReturn(cList);
		
		Map<String, List<String>> result = mockDomainFacade.getSubscriptionsForDomain(TOKEN, DOMAIN_ID);
		
		assertThat(result.entrySet(), is( not( empty() ) ) );
		
		verify(mockSubscriptionService).getSubscriptionsForDomain(anyString(), anyString());
	}
	
	@Test
	public void testGetSubscriptionsForDomainWhenDomainSubscriptionNull() {
		List<SubscriptionResponse> subResponse = new ArrayList<>();
		
		List<String> cList = new ArrayList<>();
		cList.add(COURSE_ID);
				
		when(mockSubscriptionService.getSubscriptionsForDomain(anyString(), anyString())).thenReturn(subResponse);
		when(mockCourseService.getAllCourseIdsForProvider(anyString(), anyString())).thenReturn(cList);
		
		Map<String, List<String>> result = mockDomainFacade.getSubscriptionsForDomain(TOKEN, DOMAIN_ID);
		
		assertEquals( 0, result.size() );
		
		verify(mockSubscriptionService).getSubscriptionsForDomain(anyString(), anyString());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainFacadeImpl#getSubscriptionsForCourse(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetSubscriptionsForCourse() {
		List<SubscriptionResponse> subResponse = new ArrayList<>();
		SubscriptionResponse subscriptionResponse = new SubscriptionResponse("345456", "76374643", "C", "11-16-2015", "11-16-2016", "1", "-1", "11-16-2015", "11-16-2015", "", "Subscription Name", "Title");
		subResponse.add(subscriptionResponse);
		List<String> allProviderIDList = new ArrayList<>();
		allProviderIDList.add(PROVIDER_DOMAIN_ID);
		List<String> cList = new ArrayList<>();
		cList.add(COURSE_ID);
				
		when(mockSubscriptionService.getSubscriptionsForDomain(anyString(), anyString())).thenReturn(subResponse);
		when(mockDomainService.findProviderIdsList(anyString())).thenReturn(allProviderIDList);
		when(mockCourseService.getAllCourseIdsForProvider(anyString(), anyString())).thenReturn(cList);
		
		Map<String, List<String>> result = mockDomainFacade.getSubscriptionsForDomain(TOKEN, DOMAIN_ID);
		
		assertThat(result.entrySet(), is( not( empty() ) ) );
		
		verify(mockSubscriptionService).getSubscriptionsForDomain(anyString(), anyString());
	}
	
	@Test
 	public void testGetSubscriptionsForSchool() {
		List<SubscriptionResponse> subResponse = new ArrayList<>();
		SubscriptionResponse subscriptionResponse = new SubscriptionResponse("345456", "763703", "D", "11-16-2015", "11-16-2016", "1", "-1", "11-16-2015", "11-16-2015", "", "Subscription Name", "Title");
		subResponse.add(subscriptionResponse);
		List<String> cList = new ArrayList<>();
		cList.add(COURSE_ID);
		List<String> allProviderIDList = new ArrayList<>();
		allProviderIDList.add(PROVIDER_DOMAIN_ID);
				
		when(mockSubscriptionService.getSubscriptionsForDomain(anyString(), anyString())).thenReturn(subResponse);
		when(mockDomainService.findProviderIdsList(anyString())).thenReturn(allProviderIDList);
//		when(mockCourseService.getAllCoursesFromDomain(anyString(), anyString()).stream().map(CourseResponse::getBaseid).collect(Collectors.toList())).thenReturn(cList);
		when(mockCourseService.getAllCourseIdsForProvider(anyString(), anyString())).thenReturn(cList);
		
		Map<String, List<String>> result = mockDomainFacade.getSubscriptionsForSchool(TOKEN, DOMAIN_ID);
		
		assertThat(result.entrySet(), is( not( empty() ) ) );
		
		verify(mockSubscriptionService).getSubscriptionsForDomain(anyString(), anyString());
	}
	
	private DistrictDomainRequest createDistrictDomain() {
		List<String> catalogIds= new ArrayList<>();
		catalogIds.add(PROVIDER_DOMAIN_ID);
		
		List<String> courseIds= new ArrayList<>();
		courseIds.add(COURSE_ID);
		
		DistrictDomainRequest districtDomainRequest = new DistrictDomainRequest();
		districtDomainRequest.setCourseCatalogs(catalogIds);
		districtDomainRequest.setCourseSelection(courseIds);
		districtDomainRequest.setState(STATE_DOMAIN_NAME_ONE);
		districtDomainRequest.setFullSubscription(FULL_SUBSCRIPTION);
		districtDomainRequest.setSubscriptionStartDate(SUBSCRIPTION_DATE);
		districtDomainRequest.setSubscriptionEndDate(SUBSCRIPTION_DATE);
		districtDomainRequest.setName(DOMAIN_NAME_ONE);
		districtDomainRequest.setLoginPrefix(LOGIN_PREFIX);
		districtDomainRequest.setExternalId(EXTERNAL_ID);
		districtDomainRequest.setLicenseType(LICENSE_TYPE);
		districtDomainRequest.setLicensePool(POOL_TYPE);
		districtDomainRequest.setNumbersOfLicense(NUMBER_OF_LICENSE);
		districtDomainRequest.setPilotDomain(PILOT_DOMAIN);
		districtDomainRequest.setPilotEndDate(BLANK);
		
		return districtDomainRequest;
	}
	
	private SchoolDomainRequest createSchoolDomain() {
		List<String> catalogIds= new ArrayList<>();
		catalogIds.add(PROVIDER_DOMAIN_ID);
		
		List<String> courseIds= new ArrayList<>();
		courseIds.add(COURSE_ID);
		
		SchoolDomainRequest schoolDomainRequest = new SchoolDomainRequest();
		schoolDomainRequest.setCourseCatalogs(catalogIds);
		schoolDomainRequest.setCourseSelection(courseIds);
		schoolDomainRequest.setDistrictDomainId(Long.parseLong(DISTRICT_DOMAIN_ID));		
		schoolDomainRequest.setName(DOMAIN_NAME_ONE);
		schoolDomainRequest.setLoginPrefix(LOGIN_PREFIX);
		schoolDomainRequest.setExternalId(EXTERNAL_ID);
		schoolDomainRequest.setLicenseType(LICENSE_TYPE);
		schoolDomainRequest.setLicensePool(POOL_TYPE);
		schoolDomainRequest.setNumbersOfLicense(NUMBER_OF_LICENSE);
		schoolDomainRequest.setPilotDomain(PILOT_DOMAIN);
		schoolDomainRequest.setPilotEndDate(BLANK);
		
		return schoolDomainRequest;
	}

	private EditSchoolDomainRequest createEditSchoolDomainRequest() {		
		EditSchoolDomainRequest editSchoolDomainRequest = new EditSchoolDomainRequest();
		editSchoolDomainRequest.setDomainId(Long.parseLong(DOMAIN_ID));
		editSchoolDomainRequest.setDistrictDomainId(Long.parseLong(DISTRICT_DOMAIN_ID));		
		editSchoolDomainRequest.setName(DOMAIN_NAME_ONE);
		editSchoolDomainRequest.setLoginPrefix(LOGIN_PREFIX);
		editSchoolDomainRequest.setExternalId(EXTERNAL_ID);
		editSchoolDomainRequest.setLicenseType(LICENSE_TYPE);
		editSchoolDomainRequest.setLicensePool(POOL_TYPE);
		editSchoolDomainRequest.setNumbersOfLicense(NUMBER_OF_LICENSE);
		editSchoolDomainRequest.setPilotDomain(PILOT_DOMAIN);
		editSchoolDomainRequest.setPilotEndDate(BLANK);
		
		return editSchoolDomainRequest;
	}
}
