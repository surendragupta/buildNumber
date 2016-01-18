package com.sinet.gage.provision.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
@Configuration
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class ErrorConfiguration  implements EmbeddedServletContainerCustomizer {
	/**
     * Set error pages for specific error response codes
     */
   @Override 
   public void customize( ConfigurableEmbeddedServletContainer container ) {
	  container.addErrorPages( new ErrorPage( HttpStatus.NOT_FOUND, "/error.html" ) );
       
   }

}
