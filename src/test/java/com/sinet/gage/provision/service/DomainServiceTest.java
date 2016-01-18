package com.sinet.gage.provision.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sinet.gage.dlap.clients.DlapAuthenticationClient;
import com.sinet.gage.dlap.clients.DlapDomainClient;
import com.sinet.gage.dlap.entities.Customization;
import com.sinet.gage.dlap.entities.Customization.AllowViewInGpeerCompletion;
import com.sinet.gage.dlap.entities.Customization.Files;
import com.sinet.gage.dlap.entities.Customization.Files.File;
import com.sinet.gage.dlap.entities.Customization.LearningObjectives;
import com.sinet.gage.dlap.entities.Customization.MenuItems;
import com.sinet.gage.dlap.entities.Customization.MenuItems.MenuItem;
import com.sinet.gage.dlap.entities.Customization.ResourceBase;
import com.sinet.gage.dlap.entities.Customization.Securehash;
import com.sinet.gage.dlap.entities.Customization.Securehash.Secretkey;
import com.sinet.gage.dlap.entities.Customization.Strings;
import com.sinet.gage.dlap.entities.DomainRequest;
import com.sinet.gage.dlap.entities.DomainResponse;
import com.sinet.gage.dlap.entities.DomainUpdate;
import com.sinet.gage.dlap.entities.EdivateLearn;
import com.sinet.gage.dlap.entities.EncryptedData;
import com.sinet.gage.dlap.exception.DomainDuplicateException;
import com.sinet.gage.dlap.exception.DomainNameDuplicateException;
import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.model.Domain;
import com.sinet.gage.provision.model.DomainType;
import com.sinet.gage.provision.model.SchoolDomainRequest;
import com.sinet.gage.provision.service.impl.DomainServiceImpl;

/**
 * Test class for {@link com.sinet.gage.provision.service.DomainService}
 * 
 * @author Team Gage
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DomainServiceTest {

	private static final String USERSPACE_ONE = "ces-123";
	
	private static final String USERSPACE_TWO = "vhi-123";
	
	private static final String STATE_USERSPACE_ONE = "ed-az-1234";
	
	private static final String STATE_USERSPACE_TWO = "ed-co-1234";
	
	private static final String PROVIDER_USERSPACE = "acc-ed-1234";

	private static final String CATALOG_DOMAIN_ID = "34816125";

	private static final String CUSTOMER_DOMAIN_ID = "34863804";

	private static final String DOMAIN_NAME_ONE = "Canayon Elementary School";
	
	private static final String DOMAIN_NAME_TWO = "Vally High";
	
	private static final String STATE_DOMAIN_NAME_ONE = "Arizona";
	
	private static final String STATE_DOMAIN_NAME_TWO = "Collorado";
	
	private static final String PROVIDER_DOMAIN_NAME = "Accelerate Education";

	private static final String TOKEN = "~SYC-jihGJ|ML0jX11juQd8d8BtTPAuWC2";

	private static final String TEMPLATE_DOMAIN_ID = "963829238";
	
	private static final String DISTRICT_DOMAIN_ID = "763829238";
	
	private static final String DOMAIN_ID_ONE = "763829231";
	
	private static final String DOMAIN_ID_TWO = "763829237";
	
	private static final String STATE_DOMAIN_ID_ONE = "763829232";
	
	private static final String STATE_DOMAIN_ID_TWO = "763829239";
	
	private static final String PROVIDER_DOMAIN_ID = "763829233";

	@InjectMocks
	private DomainService mockDomainService = new DomainServiceImpl();
	
	@Mock
	private AppProperties mockProperties;
	
	@Mock
	private DlapDomainClient mockDomainclient;	
	
	@Mock
	private DlapAuthenticationClient mockAuthClient;
	
	private DomainService spiedDomainService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		spiedDomainService = spy( mockDomainService );
		when( mockProperties.getCustomerDomainId()).thenReturn(CUSTOMER_DOMAIN_ID);
		when( mockProperties.getCourseCatalogDomainId()).thenReturn(CATALOG_DOMAIN_ID);
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#findAllStateDomains(java.lang.String, int)}.
	 */
	@Test
	public void testFindAllStateDomains() {
		List<DomainResponse> domainResponses = new ArrayList<>();
		domainResponses.add(createDomainResponse(STATE_DOMAIN_ID_ONE, STATE_DOMAIN_NAME_ONE, STATE_USERSPACE_ONE));
		domainResponses.add(createDomainResponse(STATE_DOMAIN_ID_TWO, STATE_DOMAIN_NAME_TWO, STATE_USERSPACE_TWO));
		domainResponses.add(createDomainResponse(PROVIDER_DOMAIN_ID, PROVIDER_DOMAIN_NAME, PROVIDER_USERSPACE));
		when( mockDomainclient.getDomainReponseList(anyString(), anyInt(), anyString(), anyString(), anyInt())).thenReturn( domainResponses );
		
		List<DomainResponse> result = mockDomainService.findAllStateDomains(TOKEN, 100);
		
		assertThat( result, hasSize(2));
		verify( mockDomainclient ).getDomainReponseList(anyString(), anyInt(), anyString(), anyString(), anyInt());
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#findAllContentDomains(java.lang.String, int)}.
	 */
	@Test
	public void testFindAllContentDomains() {
		List<DomainResponse> domainResponses = new ArrayList<>();
		domainResponses.add(createDomainResponse(PROVIDER_DOMAIN_ID, PROVIDER_DOMAIN_NAME, PROVIDER_USERSPACE));
		when( mockDomainclient.getDomainReponseList(anyString(), anyInt(), anyString(), anyString(), anyInt())).thenReturn( domainResponses );
		
		List<DomainResponse> result = mockDomainService.findAllContentDomains(TOKEN, 10);
		
		assertThat( result.get(0), hasProperty( "name", equalTo( PROVIDER_DOMAIN_NAME )));
		verify( mockDomainclient ).getDomainReponseList(anyString(), anyInt(), anyString(), anyString(), anyInt());
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#findAllChildDomains(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public void testFindAllChildDomains() {
		List<DomainResponse> domainResponses = new ArrayList<>();
		domainResponses.add(createDomainResponse(DOMAIN_ID_ONE, DOMAIN_NAME_ONE, USERSPACE_ONE));
		domainResponses.add(createDomainResponse(DOMAIN_ID_TWO, DOMAIN_NAME_TWO, USERSPACE_TWO));
		when( mockDomainclient.getDomainReponseList(anyString(), anyInt(), anyString(), anyString(), anyInt())).thenReturn( domainResponses );
		
		List<DomainResponse> result = mockDomainService.findAllChildDomains(TOKEN, CUSTOMER_DOMAIN_ID, "", "", 10);
		
		assertThat( result.get(0), hasProperty( "name", equalTo( DOMAIN_NAME_ONE )));
		verify( mockDomainclient ).getDomainReponseList(anyString(), anyInt(), anyString(), anyString(), anyInt());
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#createDomain(java.lang.String, java.util.List, com.sinet.gage.dlap.entities.EdivateLearn, java.util.List)}.
	 * @throws DomainDuplicateException 
	 * @throws DomainNameDuplicateException 
	 */
	@Test
	public void testCreateDomain () throws DomainDuplicateException, DomainNameDuplicateException {
		List<String> catalogIds = new ArrayList<>();
		catalogIds.add(PROVIDER_DOMAIN_ID);

		List<DomainResponse> domainResponses = new ArrayList<>();
		List<Domain> domainUpdateList = new ArrayList<>();
		domainUpdateList.add(createDomain());
		
		EdivateLearn edivateLearn = new EdivateLearn("district", "Per Seat", "100", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		EdivateLearn templateEdivateLearn = new EdivateLearn("district", "Per Seat", "100", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		List<Integer> domainIds = new ArrayList<>();
		domainIds.add(1);
		when( mockDomainclient.createDomains(anyString(), anyListOf(DomainRequest.class))).thenReturn( domainIds );
		when( mockProperties.getTemplateDistrictDomainId()).thenReturn(TEMPLATE_DOMAIN_ID);
		when( mockDomainclient.getDomainReponseList(anyString(), anyInt(), anyString(), anyString(), anyInt())).thenReturn( domainResponses );
		when( mockDomainclient.getDomain2Data(TOKEN, TEMPLATE_DOMAIN_ID)).thenReturn(createCustomization(templateEdivateLearn));
		when( mockDomainclient.getDomain2Data(TOKEN, PROVIDER_DOMAIN_ID)).thenReturn(createCustomization( null ));
		
		List<Integer> result = mockDomainService.createDomain(TOKEN, domainUpdateList, edivateLearn, catalogIds, DomainType.DISTRICT );
		
		assertThat( result, is( not( empty() ) ) );
		verify( mockDomainclient ).createDomains(anyString(), anyListOf(DomainRequest.class));
		verify( mockDomainclient ).getDomain2Data(TOKEN, TEMPLATE_DOMAIN_ID);
		verify( mockDomainclient ).getDomainReponseList(anyString(), anyInt(), anyString(), anyString(), anyInt());
		verify( mockProperties ).getTemplateDistrictDomainId();
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#createDomain(java.lang.String, java.util.List, com.sinet.gage.dlap.entities.EdivateLearn, java.util.List)}.
	 * @throws DomainDuplicateException 
	 * @throws DomainNameDuplicateException 
	 */
	@Test(expected=DomainNameDuplicateException.class)
	public void testCreateDomainFailedDomainDuplicateName () throws DomainDuplicateException, DomainNameDuplicateException {
		List<String> catalogIds = new ArrayList<>();
		catalogIds.add(PROVIDER_DOMAIN_ID);
		Customization providerCustomization = createCustomization( null );
		providerCustomization.getSecurehash().clear();
		
		List<DomainResponse> domainResponses = new ArrayList<>();
		domainResponses.add(createDomainResponse(DOMAIN_ID_TWO, DOMAIN_NAME_ONE, USERSPACE_ONE));
		
		List<Domain> domainUpdateList = new ArrayList<>();
		domainUpdateList.add(createDomain());
		
		EdivateLearn edivateLearn = new EdivateLearn("school", "Per Seat", "100", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		
		when( mockProperties.getTemplateSchoolDomainId()).thenReturn(TEMPLATE_DOMAIN_ID);
		when( mockDomainclient.getDomainReponseList(anyString(), anyInt(), anyString(), anyString(), anyInt())).thenReturn( domainResponses );
		when( mockDomainclient.getDomain2Data(TOKEN, TEMPLATE_DOMAIN_ID)).thenReturn( null );
		when( mockDomainclient.getDomain2Data(TOKEN, PROVIDER_DOMAIN_ID)).thenReturn( providerCustomization );
		
		mockDomainService.createDomain(TOKEN, domainUpdateList, edivateLearn, catalogIds, DomainType.SCHOOL );
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#createDomain(java.lang.String, java.util.List, com.sinet.gage.dlap.entities.EdivateLearn, java.util.List)}.
	 * @throws DomainDuplicateException 
	 * @throws DomainNameDuplicateException 
	 */
	@Test(expected=DomainDuplicateException.class)
	public void testCreateDomainFailedDomainDuplicateDomain () throws DomainDuplicateException, DomainNameDuplicateException {
		List<String> catalogIds = new ArrayList<>();
		List<DomainResponse> domainResponses = new ArrayList<>();
		List<Domain> domainUpdateList = new ArrayList<>();
		domainUpdateList.add(createDomain());
		
		EdivateLearn edivateLearn = new EdivateLearn("school", "Per Seat", "100", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		
		when( mockProperties.getTemplateSchoolDomainId()).thenReturn(TEMPLATE_DOMAIN_ID);
		when( mockDomainclient.getDomainReponseList(anyString(), anyInt(), anyString(), anyString(), anyInt())).thenReturn( domainResponses );
		when( mockDomainclient.getDomain2Data(TOKEN, TEMPLATE_DOMAIN_ID)).thenReturn( null );
		when( mockDomainclient.getDomain2Data(TOKEN, PROVIDER_DOMAIN_ID)).thenReturn( null );
		when ( mockDomainclient.createDomains(anyString(), anyListOf(DomainRequest.class))).thenThrow(new DomainDuplicateException("Duplicate domain found"));
		
		mockDomainService.createDomain(TOKEN, domainUpdateList, edivateLearn, catalogIds, DomainType.SCHOOL );
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#deleteDomain(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testDeleteDomain() {
		when( mockDomainclient.deleteDomain(anyString(), anyString())).thenReturn(Boolean.TRUE);
		
		boolean result = mockDomainService.deleteDomain(TOKEN, DOMAIN_ID_ONE);
		
		assertTrue( result );
		verify( mockDomainclient ).deleteDomain(anyString(), anyString());
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#updateDomain(java.lang.String, java.util.List, com.sinet.gage.dlap.entities.EdivateLearn, java.util.List)}.
	 * @throws DomainNameDuplicateException 
	 */
	@Test
	public void testUpdateDomain() throws DomainNameDuplicateException {
		List<String> catalogIds = new ArrayList<>();
		catalogIds.add(PROVIDER_DOMAIN_ID);

		List<DomainResponse> domainResponses = new ArrayList<>();
		domainResponses.add(createDomainResponse(DOMAIN_ID_ONE, DOMAIN_NAME_ONE, USERSPACE_ONE));
		
		List<Domain> domainUpdateList = new ArrayList<>();
		domainUpdateList.add(createDomain());
		
		EdivateLearn edivateLearn = new EdivateLearn("district", "Per Seat", "100", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		
		when( mockDomainclient.updateDomain(anyString(), anyListOf(DomainUpdate.class))).thenReturn(Boolean.TRUE);
		when( mockDomainclient.getDomainReponseList(anyString(), anyInt(), anyString(), anyString(), anyInt())).thenReturn( domainResponses );
		when( mockDomainclient.getDomain2Data(TOKEN, PROVIDER_DOMAIN_ID)).thenReturn( null );
				
		boolean result = mockDomainService.updateDomain(TOKEN, domainUpdateList, edivateLearn, catalogIds, DomainType.DISTRICT );
		
		assertTrue( result );
		verify( mockDomainclient ).updateDomain(anyString(), anyListOf(DomainUpdate.class));
		verify( mockDomainclient ).getDomain2Data(TOKEN, PROVIDER_DOMAIN_ID);
		verify( mockDomainclient ).getDomainReponseList(anyString(), anyInt(), anyString(), anyString(), anyInt());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#updateDomain(java.lang.String, java.util.List, com.sinet.gage.dlap.entities.EdivateLearn, java.util.List)}.
	 * @throws DomainNameDuplicateException 
	 */
	@Test(expected=DomainNameDuplicateException.class)
	public void testUpdateDomainFailedOnDuplicateDomainName () throws DomainNameDuplicateException {
		List<String> catalogIds = new ArrayList<>();
		catalogIds.add(PROVIDER_DOMAIN_ID);

		List<DomainResponse> domainResponses = new ArrayList<>();
		domainResponses.add(createDomainResponse(DOMAIN_ID_TWO, DOMAIN_NAME_ONE, USERSPACE_ONE));
		
		List<Domain> domainUpdateList = new ArrayList<>();
		domainUpdateList.add(createDomain());
		
		EdivateLearn edivateLearn = new EdivateLearn("district", "Per Seat", "100", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		
		when( mockProperties.getTemplateDistrictDomainId()).thenReturn(TEMPLATE_DOMAIN_ID);
		when( mockDomainclient.getDomainReponseList(anyString(), anyInt(), anyString(), anyString(), anyInt())).thenReturn( domainResponses );
		when( mockDomainclient.getDomain2Data(TOKEN, TEMPLATE_DOMAIN_ID)).thenReturn( null );
		when( mockDomainclient.getDomain2Data(TOKEN, PROVIDER_DOMAIN_ID)).thenReturn( null );
		
		
		mockDomainService.updateDomain(TOKEN, domainUpdateList, edivateLearn, catalogIds, DomainType.DISTRICT );
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#findDomain(java.lang.String, java.lang.Long)}.
	 */
	@Test
	public void testFindDomain() {
		when( mockDomainclient.getDomain( anyString(), anyLong())).thenReturn(createDomainResponse( DOMAIN_ID_ONE, DOMAIN_NAME_ONE, USERSPACE_ONE));
		
		DomainResponse result = mockDomainService.findDomain(TOKEN, Long.valueOf(DOMAIN_ID_ONE));
		
		assertThat( result, hasProperty("id", equalTo(DOMAIN_ID_ONE)));
		verify( mockDomainclient ).getDomain( anyString(), anyLong());
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#getDomainData(java.lang.String, java.lang.Long)}.
	 */
	@Test
	public void testGetDomainData() {
		EdivateLearn edivateLearn = new EdivateLearn("school", "Per Seat", "100", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		when( mockDomainclient.getDomain2Data(TOKEN, DOMAIN_ID_ONE)).thenReturn( createCustomization( edivateLearn ) );
		
		Customization customization = mockDomainService.getDomainData(TOKEN, Long.valueOf( DOMAIN_ID_ONE ) );
		
		assertThat( customization, hasProperty( "edivatelearn", equalTo( edivateLearn )));
		verify( mockDomainclient ).getDomain2Data(TOKEN, DOMAIN_ID_ONE);
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#findAllChildDomainsWithDomainData(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public void testFindAllChildDomainsWithDomainData() {
		when( mockDomainclient.getDomainReponseListWithDomainData(anyString(), anyInt(), anyString(), anyString(), anyInt())).thenReturn( Arrays.asList( createDomainResponse( DOMAIN_ID_ONE, DOMAIN_NAME_ONE, USERSPACE_ONE) ) );
		
		List<DomainResponse> result = mockDomainService.findAllChildDomainsWithDomainData(TOKEN, DOMAIN_ID_ONE, DOMAIN_NAME_ONE, "", 10);
		
		assertThat( result, is( not( empty() ) ) );
		verify( mockDomainclient ).getDomainReponseListWithDomainData(anyString(), anyInt(), anyString(), anyString(), anyInt());
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#getAvailabilityOfLicense(java.lang.String, com.sinet.gage.provision.model.SchoolDomainRequest, java.lang.String)}.
	 */
	@Test
	public void testGetAvailabilityOfLicense() {
		SchoolDomainRequest request = new SchoolDomainRequest();
		request.setDistrictDomainId(Long.valueOf(DISTRICT_DOMAIN_ID));
		request.setNumbersOfLicense(50);
		EdivateLearn edivateLearn = new EdivateLearn("district", "Per Seat", "100", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		List<DomainResponse> domainResponses = new ArrayList<>();
		
		when( mockDomainclient.getDomain2Data(TOKEN, DISTRICT_DOMAIN_ID)).thenReturn( createCustomization( edivateLearn ) );
		doReturn( domainResponses ).when(spiedDomainService).findAllChildDomains( anyString(), anyString(), anyString(), anyString(), anyInt() );
		
		boolean result = spiedDomainService.getAvailabilityOfLicense(TOKEN, request , "0");
		
		assertTrue( result );
		verify( mockDomainclient ).getDomain2Data(TOKEN, DISTRICT_DOMAIN_ID);
		verify( spiedDomainService ).findAllChildDomains( anyString(), anyString(), anyString(), anyString(), anyInt() );		
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#getAvailabilityOfLicense(java.lang.String, com.sinet.gage.provision.model.SchoolDomainRequest, java.lang.String)}.
	 */
	@Test
	public void testGetAvailabilityOfLicenseExceedsForNewSchool() {
		SchoolDomainRequest request = new SchoolDomainRequest();
		request.setDistrictDomainId(Long.valueOf(DISTRICT_DOMAIN_ID));
		request.setNumbersOfLicense(150);
		EdivateLearn edivateLearn = new EdivateLearn("district", "Per Seat", "100", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		List<DomainResponse> domainResponses = new ArrayList<>();
		
		when( mockDomainclient.getDomain2Data(TOKEN, DISTRICT_DOMAIN_ID)).thenReturn( createCustomization( edivateLearn ) );
		doReturn( domainResponses ).when(spiedDomainService).findAllChildDomains( anyString(), anyString(), anyString(), anyString(), anyInt() );
		
		boolean result = spiedDomainService.getAvailabilityOfLicense(TOKEN, request , "0");
		
		assertFalse( result );
		verify( mockDomainclient ).getDomain2Data(TOKEN, DISTRICT_DOMAIN_ID);
		verify( spiedDomainService ).findAllChildDomains( anyString(), anyString(), anyString(), anyString(), anyInt() );		
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#getAvailabilityOfLicense(java.lang.String, com.sinet.gage.provision.model.SchoolDomainRequest, java.lang.String)}.
	 */
	@Test
	public void testGetAvailabilityOfLicenseForExistingSchool() {
		SchoolDomainRequest request = new SchoolDomainRequest();
		request.setDistrictDomainId(Long.valueOf(DISTRICT_DOMAIN_ID));
		request.setNumbersOfLicense(150);
		
		EdivateLearn edivateLearn = new EdivateLearn("district", "Per Seat", "200", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		EdivateLearn schooleEdivateLearnOne = new EdivateLearn("school", "Per Seat", "50", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		EdivateLearn schooleEdivateLearnTwo = new EdivateLearn("school", "Per Seat", "50", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		
		List<DomainResponse> domainResponses = new ArrayList<>();
		domainResponses.add( createDomainResponse(DOMAIN_ID_ONE, DOMAIN_NAME_ONE, USERSPACE_ONE));
		domainResponses.add( createDomainResponse(DOMAIN_ID_TWO, DOMAIN_NAME_TWO, USERSPACE_TWO));
		
		when( mockDomainclient.getDomain2Data(TOKEN, DISTRICT_DOMAIN_ID)).thenReturn( createCustomization( edivateLearn ) );
		when( mockDomainclient.getDomain2Data(TOKEN, DOMAIN_ID_ONE)).thenReturn( createCustomization( schooleEdivateLearnOne ) );
		when( mockDomainclient.getDomain2Data(TOKEN, DOMAIN_ID_TWO)).thenReturn( createCustomization( schooleEdivateLearnTwo ) );
		doReturn( domainResponses ).when(spiedDomainService).findAllChildDomains( anyString(), anyString(), anyString(), anyString(), anyInt() );
		
		boolean result = spiedDomainService.getAvailabilityOfLicense(TOKEN, request , DOMAIN_ID_ONE);
		
		assertTrue( result );
		verify( mockDomainclient, times(3) ).getDomain2Data(anyString(), anyString());
		verify( spiedDomainService ).findAllChildDomains( anyString(), anyString(), anyString(), anyString(), anyInt() );		
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#getAvailabilityOfLicense(java.lang.String, com.sinet.gage.provision.model.SchoolDomainRequest, java.lang.String)}.
	 */
	@Test
	public void testGetAvailabilityOfLicenseExceedsForExistingSchool() {
		SchoolDomainRequest request = new SchoolDomainRequest();
		request.setDistrictDomainId(Long.valueOf(DISTRICT_DOMAIN_ID));
		request.setNumbersOfLicense(201);
		
		EdivateLearn edivateLearn = new EdivateLearn("district", "Per Seat", "200", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		EdivateLearn schooleEdivateLearnOne = new EdivateLearn("school", "Per Seat", "50", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		EdivateLearn schooleEdivateLearnTwo = new EdivateLearn("school", "Per Seat", null, "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		
		List<DomainResponse> domainResponses = new ArrayList<>();
		domainResponses.add( createDomainResponse(DOMAIN_ID_ONE, DOMAIN_NAME_ONE, USERSPACE_ONE));
		domainResponses.add( createDomainResponse(DOMAIN_ID_TWO, DOMAIN_NAME_TWO, USERSPACE_TWO));
		
		when( mockDomainclient.getDomain2Data(TOKEN, DISTRICT_DOMAIN_ID)).thenReturn( createCustomization( edivateLearn ) );
		when( mockDomainclient.getDomain2Data(TOKEN, DOMAIN_ID_ONE)).thenReturn( createCustomization( schooleEdivateLearnOne ) );
		when( mockDomainclient.getDomain2Data(TOKEN, DOMAIN_ID_TWO)).thenReturn( createCustomization( schooleEdivateLearnTwo ) );
		doReturn( domainResponses ).when(spiedDomainService).findAllChildDomains( anyString(), anyString(), anyString(), anyString(), anyInt() );
		
		boolean result = spiedDomainService.getAvailabilityOfLicense(TOKEN, request , DOMAIN_ID_ONE);
		
		assertFalse( result );
		verify( mockDomainclient, times(3) ).getDomain2Data(anyString(), anyString());
		verify( spiedDomainService ).findAllChildDomains( anyString(), anyString(), anyString(), anyString(), anyInt() );		
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.DomainServiceImpl#findProviderIdsList(java.lang.String)}.
	 */
	@Test
	public void testFindProviderIdsList() {
		List<DomainResponse> domainList = new ArrayList<>();
		domainList.add(createDomainResponse( DOMAIN_ID_ONE, DOMAIN_NAME_ONE, USERSPACE_ONE));
		doReturn( domainList ).when( spiedDomainService ).findAllContentDomains( anyString(), anyInt() );
		
		List<String> result = spiedDomainService.findProviderIdsList(TOKEN);
		
		assertThat( result.get(0), equalTo(DOMAIN_ID_ONE));
		verify( spiedDomainService ).findAllContentDomains( anyString(), anyInt() );
	}
	
	
	private DomainResponse createDomainResponse ( String domainId, String domainName, String userspace) {
		return new DomainResponse(domainId, domainName, userspace, null, null, null, 0, null, null, null, null, null, null);
	}
	
	private Customization createCustomization(EdivateLearn edivateLearn){
		List<Securehash> secureHashs = new ArrayList<>();
		List<Secretkey> secretkey = new ArrayList<Secretkey>();
		List<EncryptedData> encryptedData = new ArrayList<EncryptedData>();
		Securehash hash = new Securehash( secretkey, encryptedData );
		secureHashs.add( hash );
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
	
	private Domain createDomain() {
		Domain domain = new Domain();
		domain.setDomainId(DOMAIN_ID_ONE);
		domain.setName(DOMAIN_NAME_ONE);
		domain.setUserspace(USERSPACE_ONE);
		domain.setParentId(DISTRICT_DOMAIN_ID);
		return domain;
	}

}