package com.sinet.gage.provision.util;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinet.gage.dlap.clients.DlapAuthenticationClient;
import com.sinet.gage.dlap.config.ServerSettings;
import com.sinet.gage.dlap.entities.LoginResponse;


/**
 * Insert the Dlap Authentication token to specific methods
 * using default user name and password from settings
 * 
 * @author Team Gage
 *
 */
@Aspect
@Component
public class DefaultAuthenticationAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthenticationAspect.class);

	@Autowired
	DlapAuthenticationClient authenticationClient;
	
	@Autowired
	ServerSettings serverSettings;
	
	@Pointcut("execution(* com.sinet.gage.provision.service.impl.*.*(..))")
	private Object serviceMethods() { return null;};
	
	/**
	 * Intercepts all service layer methods which have String token as first 
	 * parameter, replaces the token with default DLAP user authentication 
	 * token if the token argument for method is blank.
	 * 
	 * @param jointPoint
	 *        method jointPoint 
	 * @param token
	 *        Token parameter of the service method invoked 
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "serviceMethods() && args(token,..)")
	public Object serviceMethodsWithAuthTokenArgument( ProceedingJoinPoint  jointPoint, String token ) throws Throwable {
		LOGGER.info("Intercepted service method " + jointPoint.getSignature().toLongString());
		if(StringUtils.isBlank(token) ){
			LOGGER.info("Replaced the autentication token for service method ");
			LoginResponse loginResponse = authenticationClient.loginUser(serverSettings.getLoginUserName(), serverSettings.getLoginPassword());
			token = loginResponse.getToken();
			jointPoint.getArgs()[0] = token;
		}		
		return jointPoint.proceed(jointPoint.getArgs());
	}	
}
