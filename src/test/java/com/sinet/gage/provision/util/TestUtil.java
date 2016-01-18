package com.sinet.gage.provision.util;

import java.lang.reflect.Method;

import org.springframework.context.MessageSource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinet.gage.provision.controller.ExceptionControllerAdvice;

public class TestUtil {
	
	public static ExceptionHandlerExceptionResolver createExceptionResolver(MessageSource messageSource) {
		ExceptionControllerAdvice exceptionControllerAdvice = new ExceptionControllerAdvice();
		exceptionControllerAdvice.setMessageSource(messageSource);
	    ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
	        protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
	            Method method = new ExceptionHandlerMethodResolver(ExceptionControllerAdvice.class).resolveMethod(exception);
	            return new ServletInvocableHandlerMethod(exceptionControllerAdvice, method);
	        }
	    };
	    exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	    exceptionResolver.afterPropertiesSet();
	    return exceptionResolver;
	}

	public static <T> String jsonify(T object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);		
	}
	
}
