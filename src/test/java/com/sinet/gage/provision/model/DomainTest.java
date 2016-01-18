package com.sinet.gage.provision.model;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
public class DomainTest {

	@Test
    public void testSetandGetForDomainId(){
		Domain u= new Domain();
		String domainId="Test Domain";
    	u.setDomainId(domainId);
    	 assertEquals(domainId, u.getDomainId());
    }
	@Test
    public void testSetandGetForName(){
		Domain u= new Domain();
		String name="Test Domain";
    	u.setName(name);
    	 assertEquals(name, u.getName());
    }
	
		
	@Test
    public void testSetandGetForUserspace(){
		Domain u= new Domain();
		String userspace="Test Domain";
    	u.setUserspace(userspace);
    	 assertEquals(userspace, u.getUserspace());
    }
	
	@Test
    public void testSetandGetForParentId(){
		Domain u= new Domain();
		String parentId="Test Domain";
    	u.setParentId(parentId);
    	 assertEquals(parentId, u.getParentId());
    }
	
	
	@Test
    public void testSetandGetForReference(){
		Domain u= new Domain();
		String reference="Test Domain";
    	u.setReference(reference);
    	 assertEquals(reference, u.getReference());
    }
	
}
