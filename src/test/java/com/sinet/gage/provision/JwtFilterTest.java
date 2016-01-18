package com.sinet.gage.provision;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JwtFilterTest {
	
	private static final String AUTHORIZATION_HEADER_BEARER_SAMPLE = "Bearer ";
	private static final String AUTHORIZATION_HEADER_INVALID_SAMPLE = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsbS1kb21haW4xL2FkbWluMSIsInVzZXIiOiJsbS1kb21haW4xL2FkbWluMSIsImlhdCI6MTQ0OTc0ODc0MCwiZXhwIjoxNDQ5NzQ5NjQwfQ.wN12Td0arvMvpBWDR0_4AjUKsw_fUumWcA30yku0gYw";
	private static final String AUTHORIZATION_HEADER="Authorization";
	
	@Mock
	HttpServletRequest mockRequest;
	
	@Mock
	HttpServletResponse mockResponse;
	
	@Mock
	FilterChain mockFilterChain;
	
	/**
	 * Test case for {@link com.sinet.gage.provision.JwtFilter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, FilterChain)}
	 * 
	 * Test Case : Test case with valid values ,AUTHORIZATION_HEADER_BEARER_SAMPLE value starts with "Bearer "  
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testDoFilter() throws IOException, ServletException{
		GetToken getToken = new GetToken();
		String token = getToken.getToken( "administrator" );
		when(mockRequest.getHeader(AUTHORIZATION_HEADER)).thenReturn(AUTHORIZATION_HEADER_BEARER_SAMPLE + token);
		callDoFilterAndVerify();
	}

	/**
	 * Test case for {@link com.sinet.gage.provision.JwtFilter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, FilterChain)}
	 * 
	 * Test Case : Test case with invalid values , AUTHORIZATION_HEADER_INVALID_SAMPLE value does not start with "Bearer "  
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test(expected=ServletException.class)
	public void testDoFilterInValidAuthHeader() throws IOException, ServletException{
		when(mockRequest.getHeader(AUTHORIZATION_HEADER)).thenReturn(AUTHORIZATION_HEADER_INVALID_SAMPLE);
		callDoFilterAndVerify();
	}
	
	private void callDoFilterAndVerify() throws IOException, ServletException {
		new JwtFilter().doFilter(mockRequest, mockResponse, mockFilterChain);
		verify(mockRequest).setAttribute(Mockito.anyString(), Mockito.anyString());
		verify(mockFilterChain).doFilter(Mockito.any(), Mockito.any());
	}	
	
	
}
