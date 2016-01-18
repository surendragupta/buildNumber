package com.sinet.gage.provision.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sinet.gage.provision.Application;
import com.sinet.gage.provision.config.AppProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
public class AppPropertiesTest {

	@Autowired
	AppProperties appProperties;
	
	@Test
	public void testGetCrosAllowOrigin() {
		assertNotNull(appProperties.getCrosAllowOrigin());
	}

	@Test
	public void testGetCrosAllowMethods() {
		assertNotNull(appProperties.getCrosAllowMethods());
	}

	@Test
	public void testGetCrosMaxAge() {
		assertNotNull(appProperties.getCrosMaxAge());
	}

	@Test
	public void testGetCrosAllowHeader() {
		assertNotNull(appProperties.getCrosAllowHeader());
	}

	@Test
	public void testGetDefaultAdminUserName() {
		assertNotNull(appProperties.getDefaultAdminUserName());
	}

	@Test
	public void testGetDefaultAdminFirstName() {
		assertNotNull(appProperties.getDefaultAdminFirstName());
	}

	@Test
	public void testGetDefaultAdminLastName() {
		assertNotNull(appProperties.getDefaultAdminLastName());
	}

	@Test
	public void testGetDefaultAdminExternalId() {
		assertNotNull(appProperties.getDefaultAdminExternalId());
	}

	@Test
	public void testGetDefaultAdminFlags() {
		assertNotNull(appProperties.getDefaultAdminFlags());
	}

	@Test
	public void testGetCourseCatalogDomainId() {
		assertNotNull(appProperties.getCourseCatalogDomainId());
	}

	@Test
	public void testGetCustomerDomainId() {
		assertNotNull(appProperties.getCustomerDomainId());
	}
	@Test
	public void testGetDefaultCourseSubscriptionInDays() {
		assertNotNull(appProperties.getDefaultCourseSubscriptionInDays());
	}
	@Test
	public void testGetDefaultDrictAdminName() {
		assertNotNull(appProperties.getDefaultDrictAdminName());
	}
	@Test
	public void testGetDefaultPilotRunInDays() {
		assertNotNull(appProperties.getDefaultPilotRunInDays());
	}
	@Test
	public void testGetDefaultSubscriptionInDays() {
		assertNotNull(appProperties.getDefaultSubscriptionInDays());
	}
	@Test
	public void testGetTemplateDistrictDomainId() {
		assertNotNull(appProperties.getTemplateDistrictDomainId());
	}
	@Test
	public void testGetTemplateSchoolDomainId() {
		assertNotNull(appProperties.getTemplateSchoolDomainId());
	}
}
