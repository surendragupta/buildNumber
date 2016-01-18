package com.sinet.gage.provision;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link com.sinet.gage.provision.GetToken}
 * 
 * @author Team Gage
 *
 */
public class GetTokenTest {

	private static final String USER_NAME = "rajender.tella";
	private static String token = "";

	@Before
	public void before() {
		GetToken gettoken = new GetToken();
		token = gettoken.getToken(USER_NAME);
	}

	@Test
	public void getToken() throws Exception {
		assertEquals(175, token.length());
	}

}