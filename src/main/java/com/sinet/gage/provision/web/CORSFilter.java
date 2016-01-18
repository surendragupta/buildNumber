package com.sinet.gage.provision.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinet.gage.provision.config.AppProperties;

/**
 * Filter add CROS header to outgoing response 
 * 
 * @author Team Gage
 *
 */
@Component
public class CORSFilter implements Filter {
	
	@Autowired
	private AppProperties properties;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setHeader("Access-Control-Allow-Origin", properties.getCrosAllowOrigin());
		httpResponse.setHeader("Access-Control-Allow-Methods", properties.getCrosAllowMethods());
		httpResponse.setHeader("Access-Control-Max-Age", properties.getCrosMaxAge());
		httpResponse.setHeader("Access-Control-Allow-Headers", properties.getCrosAllowHeader());
		chain.doFilter(request, httpResponse);
	}

	@Override
	public void destroy() {	
	}
}
