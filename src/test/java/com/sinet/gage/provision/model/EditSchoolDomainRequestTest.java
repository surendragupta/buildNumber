package com.sinet.gage.provision.model;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
public class EditSchoolDomainRequestTest extends SchoolDomainRequest{

	
	@Test
    public void testSetandGetForDomainId(){
		EditSchoolDomainRequest u= new EditSchoolDomainRequest();
		long domainId=123456;
    	u.setDomainId(domainId);
    	long k=u.getDomainId();
    	 assertEquals(domainId, k);
    }
}
