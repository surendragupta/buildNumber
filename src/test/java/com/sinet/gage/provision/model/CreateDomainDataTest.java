package com.sinet.gage.provision.model;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;

public class CreateDomainDataTest {
	
	@Test
    public void testSetandGetForDomainName(){
		DistrictDomainRequest u= new DistrictDomainRequest();
		String domainName="Test Domain";
    	u.setName(domainName);
        assertEquals(domainName, u.getName());
    }
	
	@Test
    public void testSetandGetForState(){
		DistrictDomainRequest u= new DistrictDomainRequest();
		String state="Alabama";
    	u.setState(state);
        assertEquals(state, u.getState());
    }
	
	
	@Test
    public void testSetandGetForLoginPrefix(){
		DistrictDomainRequest u= new DistrictDomainRequest();
		String loginPrefix="ald";
    	u.setLoginPrefix(loginPrefix);
        assertEquals(loginPrefix, u.getLoginPrefix());
    }
	
	@Test
    public void testSetandGetForExternalId(){
		DistrictDomainRequest u= new DistrictDomainRequest();
		String externalId="test-12";
    	u.setExternalId(externalId);
        assertEquals(externalId, u.getExternalId());
    }
	
	@Test
    public void testSetandGetForSubscriptionEndDate(){
		DistrictDomainRequest u= new DistrictDomainRequest();
		String subscriptionEndDate="30/10/2016";
    	u.setSubscriptionEndDate(subscriptionEndDate);
        assertEquals(subscriptionEndDate, u.getSubscriptionEndDate());
		
    }
	@Test
    public void testSetandGetForSubscriptionStartDate() throws ParseException{
		DistrictDomainRequest u= new DistrictDomainRequest();
		String subscriptionStartDate="30/10/2016";
    	u.setSubscriptionStartDate(subscriptionStartDate);
        assertEquals(subscriptionStartDate, u.getSubscriptionStartDate());
		
    }
	
	
	@Test
    public void testSetandGetForLicenseType() throws ParseException{
		DistrictDomainRequest u= new DistrictDomainRequest();
		String licenseType="fixed";
    	u.setLicenseType(licenseType);
        assertEquals(licenseType, u.getLicenseType());
		
    }
	@Test
    public void testSetandGetForLicensePool() throws ParseException{
		DistrictDomainRequest u= new DistrictDomainRequest();
		String licensePool="100";
    	u.setLicensePool(licensePool);
        assertEquals(licensePool, u.getLicensePool());
		
    }
	@Test
    public void testSetandGetForNumbersOfLicense() throws ParseException{
		DistrictDomainRequest u= new DistrictDomainRequest();
		int numbersOfLicense = 100;
    	u.setNumbersOfLicense(numbersOfLicense);
        assertEquals(numbersOfLicense, u.getNumbersOfLicense());
		
    }
	
	@Test
    public void testSetandGetForPilotDomain(){
		DistrictDomainRequest u= new DistrictDomainRequest();
		String pilotEndDate="30/10/2016";
    	u.setPilotEndDate(pilotEndDate);
        assertEquals(pilotEndDate, u.getPilotEndDate());
		
    }
}
