package com.sinet.gage.provision.model;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SchoolDomainRequestTest {

	
	@Test
    public void testSetandGetForDistrictDomainId(){
		SchoolDomainRequest u= new SchoolDomainRequest();
		long districtDomainId=123456;
    	u.setDistrictDomainId(districtDomainId);
    	long k=u.getDistrictDomainId();
    	 assertEquals(districtDomainId, k);
    }
	
	@Test
    public void testSetandGetForName(){
		SchoolDomainRequest u= new SchoolDomainRequest();
		String name="gageTeam";
    	u.setName(name);
    	 assertEquals(name, u.getName());
    }
	
	@Test
    public void testSetandGetForLoginPrefix(){
		SchoolDomainRequest u= new SchoolDomainRequest();
		String loginPrefix="gageTeam";
    	u.setLoginPrefix(loginPrefix);
    	 assertEquals(loginPrefix, u.getLoginPrefix());
    }
	
	@Test
    public void testSetandGetForExternalId(){
		SchoolDomainRequest u= new SchoolDomainRequest();
		String externalId="gageTeam";
    	u.setExternalId(externalId);
    	 assertEquals(externalId, u.getExternalId());
    }
	@Test
    public void testSetandGetForLicensePool(){
		SchoolDomainRequest u= new SchoolDomainRequest();
		String licensePool="gageTeam";
    	u.setLicensePool(licensePool);
    	 assertEquals(licensePool, u.getLicensePool());
    }
	
	@Test
    public void testSetandGetForNumberOfLicense(){
		SchoolDomainRequest u= new SchoolDomainRequest();
		int numberOfLicense=50;
    	u.setNumbersOfLicense(numberOfLicense);
    	 assertEquals(numberOfLicense, u.getNumbersOfLicense());
    }
	
	
	
	@Test
    public void testSetandGetForLicenseType(){
		SchoolDomainRequest u= new SchoolDomainRequest();
		String licenseType="fixed";
    	u.setLicenseType(licenseType);
    	 assertEquals(licenseType, u.getLicenseType());
    }
	
	
	
}
