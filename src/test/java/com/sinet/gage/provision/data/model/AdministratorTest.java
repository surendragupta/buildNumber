package com.sinet.gage.provision.data.model;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.SerializationUtils;

import com.sinet.gage.provision.model.Message;
import com.sinet.gage.provision.model.MessageType;
public class AdministratorTest {

	//private static final MessageType SUCCESS = EXPIRED;
		private Administrator myBean;

	    @Before
	    public void setUp() throws Exception {
	    	//Object o = new Object();
	    	myBean  = new Administrator("TesterOne", "one",  "Sinet", "admin");
	    	
	    }

	    @Test
	    public void beanIsSerializable() {
	        final byte[] serializedMyBean = SerializationUtils.serialize(myBean);
	        final Administrator deserializedMyBean = (Administrator) SerializationUtils.deserialize(serializedMyBean);
	        assertEquals("TesterOne", deserializedMyBean.getUserName());
	    }
	    
	    @Test
	    public void testSetandGetForUsername(){
	    	Administrator m= new Administrator();
	    	String username="Test data";
	        m.setUserName(username);
	        assertEquals(username, m.getUserName());
	    }
	    
	    @Test
	    public void testSetandGetForFirstName(){
	    	Administrator m= new Administrator();
	    	String fname="Test data";
	        m.setFirstName(fname);
	        assertEquals(fname, m.getFirstName());
	    }
	    @Test
	    public void testSetandGetForLastname(){
	    	Administrator m= new Administrator();
	    	String lname="Test data";
	        m.setLastName(lname);
	        assertEquals(lname, m.getLastName());
	    }
	    @Test
	    public void testSetandGetForPassword(){
	    	Administrator m= new Administrator();
	    	String password="Test data";
	        m.setPassword(password);
	        assertEquals(password, m.getPassword());
	    }
}
