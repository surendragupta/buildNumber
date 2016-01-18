package com.sinet.gage.provision.model;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
public class LoginRequestTest {

	@Test
    public void testSetandGetForUsername(){
		LoginRequest u= new LoginRequest();
		String userName="gageTeam";
    	u.setUserName(userName);
    	assertEquals(userName, u.getUserName());
    }
	
	@Test
    public void testSetandGetForuserPassword(){
		LoginRequest u= new LoginRequest();
		String userPassword="gageTeam";
    	u.setUserPassword(userPassword);
    	assertEquals(userPassword, u.getUserPassword());
    }
}
