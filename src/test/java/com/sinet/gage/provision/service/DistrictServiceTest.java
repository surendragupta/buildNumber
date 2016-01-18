package com.sinet.gage.provision.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.data.model.Administrator;
import com.sinet.gage.provision.data.model.District;
import com.sinet.gage.provision.data.model.School;
import com.sinet.gage.provision.data.repository.DistrictRepository;
import com.sinet.gage.provision.data.repository.SchoolRepository;
import com.sinet.gage.provision.service.impl.DistrictServiceImpl;

/**
 * Test class for {@link com.sinet.gage.provision.service.DistrictService}
 * 
 * @author Team Gage
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DistrictServiceTest {
	
	private static final String ADMIN_FIRST_NAME = "Admin";

	@InjectMocks
	private DistrictService mockDistrictService = new DistrictServiceImpl();
	
	@Mock
	private AppProperties mockAppProperties;
	
	@Mock
	private DistrictRepository mockDistrictRepository;
	
	@Mock
	private SchoolRepository mockSchoolRepository;
	
	/**
	 * Sets up test data
	 */
	@Before
	public void setUp () {
		when(mockAppProperties.getDefaultAdminFirstName()).thenReturn(ADMIN_FIRST_NAME);
		when(mockAppProperties.getDefaultAdminLastName()).thenReturn("User");
		when(mockAppProperties.getDefaultAdminUserName()).thenReturn("administrator");
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.DistrictService#insertDistrict(District)}
	 */
	@Test
	public void testInsertDistrict() {
		District district = createDistrict(1L);
		when(mockDistrictRepository.save(any(District.class))).thenReturn( district );
		
		District result = mockDistrictService.insertDistrict( district );
		
		assertNotNull(result.getDomainId());
		verify( mockDistrictRepository, times(1)).save(any(District.class));
	}

	/**
	 *  Test method for {@link com.sinet.gage.provision.service.DistrictService#insertSchool(Long, School)}
	 */
	@Test
	public void testInsertSchool() {
		District district = createDistrict(1L);
		School school = createSchool();
		when(mockDistrictRepository.findOne(anyLong())).thenReturn( district );
		when(mockSchoolRepository.save(any(School.class))).thenReturn( school );
		School result = mockDistrictService.insertSchool(1L,school );
		
		assertNotNull(result.getSchoolId());
		verify( mockSchoolRepository, times(1)).save(any(School.class));
		verify( mockDistrictRepository, times(1)).findOne(anyLong());
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.DistrictService#deleteDistrict(Long)}
	 */
	@Test
	public void testDeleteDistrict () {
		District district = createDistrict(1L);
		when(mockDistrictRepository.findOne(anyLong())).thenReturn(district);
		
		District result = mockDistrictService.deleteDistrict(1L);
		
		assertEquals(district, result);
		verify(mockDistrictRepository,times(1)).findOne(anyLong());
		verify(mockDistrictRepository, times(1)).delete(any(District.class));
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.DistrictService#deleteDistrict(Long)}
	 */
	@Test
	public void testDeleteDistrictNotFound () {
		when(mockDistrictRepository.findOne(anyLong())).thenReturn(null);
		
		District result = mockDistrictService.deleteDistrict(1L);
		
		assertNull(result);
		verify(mockDistrictRepository,times(1)).findOne(anyLong());
		verify(mockDistrictRepository, times(0)).delete(any(District.class));
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.DistrictService#deleteDistrict(Long)}
	 */
	@Test
	public void testUpdateDistrict () {
		District district = createDistrict(1L);
		when(mockDistrictRepository.findOne(anyLong())).thenReturn(district);
		when(mockDistrictRepository.save(any(District.class))).thenReturn( district );
		
		District result = mockDistrictService.updateDistrict(district);
		
		assertEquals(district, result);
		verify(mockDistrictRepository,times(1)).findOne(anyLong());
		verify( mockDistrictRepository, times(1)).save(any(District.class));
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.DistrictService#deleteDistrict(Long)}
	 */
	@Test
	public void testUpdateDistrictNotExisting () {
		District district = createDistrict(1L);
		when(mockDistrictRepository.findOne(anyLong())).thenReturn(null);
		when(mockDistrictRepository.save(any(District.class))).thenReturn( district );
		
		District result = mockDistrictService.updateDistrict(district);
		
		assertEquals(ADMIN_FIRST_NAME,result.getAdministrator().getFirstName());
		verify(mockDistrictRepository,times(1)).findOne(anyLong());
		verify( mockDistrictRepository, times(1)).save(any(District.class));
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.DistrictService#insertSchool(Long, School)}
	 */
	@Test
	public void testInsertSchoolNoDistrict() {
		School school = createSchool();
		when(mockDistrictRepository.findOne(anyLong())).thenReturn( null );
		when(mockSchoolRepository.save(any(School.class))).thenReturn( school );
		School result = mockDistrictService.insertSchool(1L,school );
		
		assertNotNull(result.getSchoolId());
		verifyZeroInteractions( mockSchoolRepository);
		verify( mockDistrictRepository, times(1)).findOne(anyLong());
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.DistrictService#deleteSchool(Long)}
	 */
	@Test
	public void testDeleteSchool () {
		doNothing().when( mockSchoolRepository).delete(anyLong());
		mockDistrictService.deleteSchool(1L);
		
		verify(mockSchoolRepository,times(1)).delete(anyLong());
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.DistrictService#updateSchool(Long, School)}
	 */
	@Test
	public void testUpdateSchool () {
		School school = createSchool();
		District district = createDistrict(1L);
		when( mockDistrictRepository.findOne(anyLong())).thenReturn(district);
		when( mockSchoolRepository.findOne(anyLong())).thenReturn(school);
		when(mockSchoolRepository.save(any(School.class))).thenReturn( school );
		when(mockDistrictRepository.save(any(District.class))).thenReturn( district );
		
		School result = mockDistrictService.updateSchool(1L, school);
		
		assertNotNull(result.getDistrict());
		verify( mockDistrictRepository, times(1)).findOne(anyLong());
		verify( mockSchoolRepository, times(1)).findOne(anyLong());
		verify( mockDistrictRepository, times(1)).save(any(District.class));
		verify( mockSchoolRepository, times(1)).save(any(School.class));
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.DistrictService#updateSchool(Long, School)}
	 */
	@Test
	public void testUpdateSchoolNotExisting () {
		School school = createSchool();
		District district = createDistrict(1L);
		when( mockDistrictRepository.findOne(anyLong())).thenReturn(district);
		when( mockSchoolRepository.findOne(anyLong())).thenReturn(null);
		when(mockSchoolRepository.save(any(School.class))).thenReturn( school );
		when(mockDistrictRepository.save(any(District.class))).thenReturn( district );
		
		School result = mockDistrictService.updateSchool(1L, school);
		
		assertNotNull(result.getDistrict());
		verify( mockDistrictRepository, times(1)).findOne(anyLong());
		verify( mockSchoolRepository, times(1)).findOne(anyLong());
		verify( mockDistrictRepository, times(1)).save(any(District.class));
		verify( mockSchoolRepository, times(1)).save(any(School.class));
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.DistrictService#updateSchool(Long, School)}
	 */
	@Test
	public void testUpdateSchoolNoDomain () {
		School school = createSchool();
		when( mockDistrictRepository.findOne(anyLong())).thenReturn(null);
		when( mockSchoolRepository.findOne(anyLong())).thenReturn(null);
		
		School result = mockDistrictService.updateSchool(1L, school);
		
		assertNull(result.getDistrict());
		verify( mockDistrictRepository, times(1)).findOne(anyLong());
		verify( mockSchoolRepository, times(1)).findOne(anyLong());
		verify( mockDistrictRepository, times(0)).save(any(District.class));
		verify( mockSchoolRepository, times(0)).save(any(School.class));
	}
	
	/**
	 *  Test method for {@link com.sinet.gage.provision.service.DistrictService#updateSchool(Long, School)}
	 */
	@Test
	public void testUpdateSchoolFailed () {
		School school = createSchool();
		District district = createDistrict(1L);
		when( mockDistrictRepository.findOne(anyLong())).thenReturn(district);
		when( mockSchoolRepository.findOne(anyLong())).thenReturn(school);
		when(mockSchoolRepository.save(any(School.class))).thenReturn( null );
		
		School result = mockDistrictService.updateSchool(1L, school);
		
		assertNull(result);
		verify( mockDistrictRepository, times(1)).findOne(anyLong());
		verify( mockSchoolRepository, times(1)).findOne(anyLong());
		verify( mockDistrictRepository, times(0)).save(any(District.class));
		verify( mockSchoolRepository, times(1)).save(any(School.class));
	}
	
	private School createSchool() {
		School school = new School();
		school.setSchoolId(1L);
		school.setName("Canyon Elementary School");
		school.setUserSpace("ces-12346");
		school.setAdministrator(createAdministrator());
		return school;
	}
	
	private District createDistrict(Long domainId) {
		District district = new District();
		district.setDomainId(domainId);
		district.setName( "District Of Tempe" );
		district.setUserSpace( "dot-12345" );
		district.setAdminUser( "Mark Schaufer" );
		district.setAdministrator( createAdministrator() );
		return district;
	}
	
	private Administrator createAdministrator() {
		Administrator administrator  = new Administrator();
		administrator.setFirstName(ADMIN_FIRST_NAME);
		administrator.setLastName("User");
		administrator.setUserName("administrator");
		administrator.setPassword("ABCD@FGHS!#12");
		return administrator;
	}

}
