package com.sinet.gage.provision.util;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sinet.gage.dlap.entities.UserRequest;
import com.sinet.gage.provision.Application;
import com.sinet.gage.provision.data.model.Administrator;
import com.sinet.gage.provision.data.model.District;
import com.sinet.gage.provision.data.model.School;
import com.sinet.gage.provision.model.DistrictDomainRequest;
import com.sinet.gage.provision.model.Domain;
import com.sinet.gage.provision.model.EditSchoolDomainRequest;
import com.sinet.gage.provision.model.SchoolDomainRequest;

/**
 * @author Team Gage
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
public class PojoMapperTest {
	
	@Autowired
	PojoMapper pojoMapper;

	/**
	 * Test method for {@link com.sinet.gage.provision.util.PojoMapper#mapToAdministrator(com.sinet.gage.dlap.entities.UserRequest)}.
	 */
	@Test
	public void testMapToAdministrator() {
		UserRequest user = new UserRequest("Gage", "gage@1234", null, null, "admin", "admin", null, null, null, null, null);
		
		Administrator admin = pojoMapper.mapToAdministrator(user);
		
		assertNotNull(admin);
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.util.PojoMapper#mapToDistrict(com.sinet.gage.provision.model.DistrictDomainRequest, com.sinet.gage.dlap.entities.UserRequest, java.lang.String)}.
	 */
	@Test
	public void testMapToDistrict() {
		UserRequest user = new UserRequest("Gage", "gage@1234", null, null, "admin", "admin", null, null, null, null, null);
		DistrictDomainRequest domianRequest = new DistrictDomainRequest();
		
		District district = pojoMapper.mapToDistrict(domianRequest, user, "1234");
		
		assertNotNull(district);
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.util.PojoMapper#mapToDistrictForEditing(com.sinet.gage.provision.model.DistrictDomainRequest, java.lang.String)}.
	 */
	@Test
	public void testMapToDistrictForEditing() {
		DistrictDomainRequest domianRequest = new DistrictDomainRequest();
		
		District district = pojoMapper.mapToDistrictForEditing(domianRequest, "1234");
		
		assertNotNull(district);
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.util.PojoMapper#mapToDomainList(com.sinet.gage.provision.model.DistrictDomainRequest)}.
	 */
	@Test
	public void testMapToDomainList() {
		DistrictDomainRequest domianRequest = new DistrictDomainRequest();
		
		List<Domain> domainList = pojoMapper.mapToDomainList(domianRequest);
		
		assertThat(domainList,hasSize(1) );
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.util.PojoMapper#mapToDomains(com.sinet.gage.provision.model.SchoolDomainRequest)}.
	 */
	@Test
	public void testMapToDomains() {
		SchoolDomainRequest schoolRequest = new SchoolDomainRequest();
		schoolRequest.setDistrictDomainId(12344l);
		schoolRequest.setExternalId("123");
		schoolRequest.setLicensePool("pool");
		schoolRequest.setLicenseType("fixed");
		schoolRequest.setLoginPrefix("gage-01");
		schoolRequest.setName("gage");
		schoolRequest.setNumbersOfLicense(99);
		schoolRequest.setPilotDomain(false);
		schoolRequest.setPilotEndDate("10-12-1990"); 
		
		List<Domain> domainList = pojoMapper.mapToDomains(schoolRequest);
		
		assertThat(domainList,hasSize(1) );
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.util.PojoMapper#mapToSchool(com.sinet.gage.provision.model.SchoolDomainRequest, com.sinet.gage.dlap.entities.UserRequest, java.lang.String)}.
	 */
	@Test
	public void testMapToSchool() {
		UserRequest user = new UserRequest("Gage", "gage@1234", null, null, "admin", "admin", null, null, null, null, null);
		SchoolDomainRequest schoolRequest = new SchoolDomainRequest();
		
		School school = pojoMapper.mapToSchool(schoolRequest, user, "1234");
		
		assertNotNull(school);
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.util.PojoMapper#mapToSchoolForEditing(com.sinet.gage.provision.model.EditSchoolDomainRequest)}.
	 */
	@Test
	public void testMapToSchoolForEditing() {
		EditSchoolDomainRequest schoolRequest = new EditSchoolDomainRequest();
		
		School school = pojoMapper.mapToSchoolForEditing(schoolRequest);

		assertNotNull(school);
	}

}
