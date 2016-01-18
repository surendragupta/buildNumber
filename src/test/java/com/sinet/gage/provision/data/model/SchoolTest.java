package com.sinet.gage.provision.data.model;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
public class SchoolTest {

	private School myBean;

    @Before
    public void setUp() throws Exception {
    	//Object o = new Object();
    	myBean  = new School();
    	myBean.setSchoolId(123456L);
    	
    }

    @Test
    public void beanIsSerializable() {
        final byte[] serializedMyBean = SerializationUtils.serialize(myBean);
        final School deserializedMyBean = (School) SerializationUtils.deserialize(serializedMyBean);
        long k=deserializedMyBean.getSchoolId();
        assertEquals(123456L, k);
    }
    
    @Test
    public void testSetandGetForDomainId(){
    	School m= new School();
    	long domainId=123456L;
    	m.setSchoolId(domainId);
       long k=m.getSchoolId();
    	assertEquals(domainId, k);
    }
	
    @Test
    public void testSetandGetForName(){
    	School m= new School();
    	
    	String name="schoolAdmin";
    	m.setName(name);
       	assertEquals(name,m.getName());
    }
	
    @Test
    public void testSetandGetForUserSpace(){
    	School m= new School();
    	String userSpace="userSpace";
    	m.setUserSpace(userSpace);
       	assertEquals(userSpace,m.getUserSpace());
    }
    @Test
    public void testSetandGetForPilot(){
    	School m= new School();
    	Pilot Pilot= new Pilot(true, new Date());
        m.setPilot(Pilot);
        assertEquals(Pilot, m.getPilot());
    }
    @Test
    public void testSetandGetForAdministrator(){
    	
    	School m= new School();
    	Administrator admin= new Administrator("TesterOne", "one",  "Sinet", "admin");
    	m.setAdministrator(admin);
    	assertEquals(admin, m.getAdministrator());
    	
    }
    @Test
    public void testSetandGetForLicense(){
    	
    	School m= new School();
    	License licence= new License("ABC", 23,"ABCD");
        m.setLicense(licence);
        assertEquals(licence, m.getLicense());
    	
    }
}
