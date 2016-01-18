package com.sinet.gage.provision.model;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.SerializationUtils;
public class MessageTest {
	
	
	
	//private static final MessageType SUCCESS = EXPIRED;
	private Message myBean;

    @Before
    public void setUp() throws Exception {
    	//Object o = new Object();
    	myBean  = new Message(MessageType.SUCCESS, "correct", "");
    	
    }

    @Test
    public void beanIsSerializable() {
        final byte[] serializedMyBean = SerializationUtils.serialize(myBean);
        final Message deserializedMyBean = (Message) SerializationUtils.deserialize(serializedMyBean);
        assertEquals("correct", deserializedMyBean.getMessage());
    }
    
    @Test
    public void testSetandGetForMessage(){
      	Message m= new Message();
    	String s="Test data";
        m.setMessage(s);
    	assertEquals(s, m.getMessage());
    }
    
    @Test
    public void testSetandGetForMessageType(){
      	Message m= new Message();
    	m.setMessageType(MessageType.SUCCESS);
       	assertEquals(MessageType.SUCCESS, m.getMessageType());
    }
    
}
