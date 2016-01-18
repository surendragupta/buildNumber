package com.sinet.gage.provision.data.model;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
public class LicenseTest {

	@Test
    public void testSetandGetForLicenseType(){
		License m= new License();
    	String licenseType="concurrent";
        m.setLicenseType(licenseType);
        assertEquals(licenseType, m.getLicenseType());
    }
	
	@Test
    public void testSetandGetForLicensePool(){
		License m= new License();
    	String licensePool="fixed";
        m.setLicensePool(licensePool);
        assertEquals(licensePool, m.getLicensePool());
    }
	
	@Test
    public void testSetandGetForNumberOfLicense(){
		License m= new License();
    	int numberOfLicense=100;
        m.setNumbersOfLicense(numberOfLicense);
        assertEquals(numberOfLicense, m.getNumbersOfLicense());
    }
}
