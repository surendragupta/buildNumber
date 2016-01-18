package com.sinet.gage.provision.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.sinet.gage.dlap.config.ServerSettings;
import com.sinet.gage.provision.Application;
import com.sinet.gage.provision.model.LoginRequest;
import com.sinet.gage.provision.model.Message;
import com.sinet.gage.provision.model.MessageType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebIntegrationTest(randomPort = true)
public class UserControllerIntegrationTest {

	private static final String BASE_URI = "http://localhost:";
	private RestTemplate restTemplate = new RestTemplate();
	
	@Value("${local.server.port}")
	private int port;
	
	@Autowired
	ServerSettings serverSettings;
	
	private String getBaseUrl() {
		return BASE_URI + port;
	}

	@Test
	public void authenticateUserFailed() throws Exception {
		String uri = getBaseUrl() + "/user/login";

		LoginRequest user = new LoginRequest();
		user.setUserName("test/administrator");
		user.setUserPassword("dfdf");

		Message result = restTemplate.postForObject(uri, user, Message.class);
		assertEquals(MessageType.ERROR, result.getMessageType());
	}
	
	@Test
	public void authenticateUserWithValidCredentials() throws Exception {
		String uri = getBaseUrl() + "/user/login";
		
		LoginRequest user = new LoginRequest();
		
		user.setUserName("lm-domain1/admin1");
		user.setUserPassword("admin1");

		Message result = restTemplate.postForObject(uri, user, Message.class);
		assertEquals(MessageType.SUCCESS, result.getMessageType());
	}
	
	@Test
	public void logoutUser() throws Exception {
		String uri = getBaseUrl() + "/user/logout";		

		Message result = restTemplate.getForObject(uri, Message.class);
		assertEquals(MessageType.SUCCESS, result.getMessageType());
	}
}
