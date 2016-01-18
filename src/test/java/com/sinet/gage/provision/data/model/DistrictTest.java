package com.sinet.gage.provision.data.model;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
public class DistrictTest {

	
	private District myBean;

    @Before
    public void setUp() throws Exception {
    	//Object o = new Object();
    	myBean  = new District();
    	myBean.setDomainId(123456L);
    	
    }

    @Test
    public void beanIsSerializable() {
        final byte[] serializedMyBean = SerializationUtils.serialize(myBean);
        final District deserializedMyBean = (District) SerializationUtils.deserialize(serializedMyBean);
        long k=deserializedMyBean.getDomainId();
        assertEquals(123456L, k);
    }
    
    @Test
    public void testSetandGetForAdminUser(){
    	District m= new District();
    	String adminUser="Test data";
        m.setAdminUser(adminUser);
        assertEquals(adminUser, m.getAdminUser());
    }
    @Test
    public void testSetandGetForUsername(){
    	District m= new District();
    	long k=123456L;
        m.setDomainId(k);
        long h=m.getDomainId();
        assertEquals(k, h);
    }
    @Test
    public void testSetandGetForName(){
    	
    	District m= new District();
    	String name="Test data";
        m.setName(name);
        assertEquals(name, m.getName());
    	
    }
    
    @Test
    public void testSetandGetForUserSpace(){
    	
    	District m= new District();
    	String userSpace="Test data";
        m.setUserSpace(userSpace);
        assertEquals(userSpace, m.getUserSpace());
    	
    }
    
    @Test
    public void testSetandGetForPilot(){
    	
    	District m= new District();
    	Pilot Pilot= new Pilot(true, new Date());
        m.setPilot(Pilot);
        assertEquals(Pilot, m.getPilot());
    	
    }
    
    @Test
    public void testSetandGetForLicense(){
    	
    	District m= new District();
    	License licence= new License("ABC", 23,"ABCD");
        m.setLicense(licence);
        assertEquals(licence, m.getLicense());
    	
    }
    @Test
    public void testSetandGetForAdministrator(){
    	
    	District m= new District();
    	Administrator admin= new Administrator("TesterOne", "one",  "Sinet", "admin");
    	m.setAdministrator(admin);
    	assertEquals(admin, m.getAdministrator());
    	
    }
    @Test
    public void testSetandGetForSchoolList(){
    	
    	District m= new District();
    	School sch= new School();
    	long t=123456L;
    	sch.setSchoolId(123456L);
    	List<School> schoolList = new ArrayList<School>();
    	schoolList.add(sch);
    	m.setSchoolList(schoolList);
    	long k= m.getSchoolList().get(0).getSchoolId();
    	assertEquals(t,k);
    	
    }
    
}
