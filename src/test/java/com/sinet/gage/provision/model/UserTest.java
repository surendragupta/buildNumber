package com.sinet.gage.provision.model;
import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
public class UserTest {
	
	private User myBean;
	
	 @Before
	    public void setUp() throws Exception {
	    	//Object o = new Object();
	    	myBean  = new User();
	    	myBean.setDomainId(123456);
	    	myBean.setEmail("tester@tester.com");
	    	myBean.setFirstName("Test");
	    	myBean.setLastName("Test");
	    }

	    @Test
	    public void beanIsSerializable() {
	        final byte[] serializedMyBean = SerializationUtils.serialize(myBean);
	        final User deserializedMyBean = (User) SerializationUtils.deserialize(serializedMyBean);
	        assertEquals("Test", deserializedMyBean.getFirstName());
	    }

	    @Test
	    public void testSetandGetForId(){
	      	User u= new User();
	    	int i=100234;
	       u.setDomainId(i);
	       assertEquals(i, u.getDomainId());
	    }
	    
	    @Test
	    public void testSetandGetForEmail(){
	      	String email="test@test.com";
	    	User u= new User();
	    	u.setEmail(email);
	       assertEquals(email, u.getEmail());
	    }
	    @Test
	    public void testSetandGetForFirstName(){
	      	String fname="tester";
	    	User u= new User();
	    	u.setFirstName(fname);
	       assertEquals(fname, u.getFirstName());
	    }
	    @Test
	    public void testSetandGetForLastName(){
	      	String lname="Sinet";
	    	User u= new User();
	    	u.setLastName(lname);
	       assertEquals(lname, u.getLastName());
	    }
	
	    @Test
	    public void testSetandGetForpassword(){
	    	String p="admin3/lm";
	    	User u= new User();
	    	u.setPassword(p);
	    	assertEquals(p, u.getPassword());
	    }
	    @Test
	    public void testSetandGetForpasswordQuestionr(){
	    	String pQns="what is your pet name?";
	    	User u= new User();
	    	u.setPasswordquestion(pQns);
	    	assertEquals(pQns, u.getPasswordquestion());
	    }
	    @Test
	    public void testSetandGetForpasswordAnswer(){
	    	String pAns="Sinet";
	    	User u= new User();
	    	u.setPasswordanswer(pAns);
	    	assertEquals(pAns, u.getPasswordanswer());
	    }
	    @Test
	    public void testSetandGetForRefrence(){
	    	String reference="Sinet";
	    	User u= new User();
	    	u.setReference(reference);
	    	assertEquals(reference, u.getReference());
	    }
	    @Test
	    public void testSetandGetForRight(){
	    	String right="Sinet";
	    	User u= new User();
	    	u.setRights(right);
	    	assertEquals(right, u.getRights());
	    }
	    @Test
	    public void testSetandGetForFlags(){
	    	String flag="0";
	    	User u= new User();
	    	u.setFlags(flag);
	    	assertEquals(flag, u.getFlags());
	    }
	    
	    @Test
	    public void testSetandGetForUserId(){
	    	long userId=784352;
	    	User u= new User();
	    	u.setUserId(userId);
	    	assertEquals(userId, u.getUserId());
	    }
	    @Test
	    public void testSetandGetForUserName(){
	    String username="rajender";
	    	User u= new User();
	    	u.setUsername(username);
	    	assertEquals(username, u.getUsername());
	    }
	    
	    
	    @Test
	    public void testSetandGetForentities(){
	    String entities="SinetApplication";
	    	User u= new User();
	    	u.setEntities(entities);
	    	assertEquals(entities, u.getEntities());
	    }
}
