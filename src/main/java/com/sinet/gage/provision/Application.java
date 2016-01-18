package com.sinet.gage.provision;


import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Boot main Application class
 * 
 * @author Team Gage
 *
 */
@Configuration
@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class Application extends SpringBootServletInitializer {
	 @Bean
	    public FilterRegistrationBean jwtFilter() {
	        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	        registrationBean.setFilter(new JwtFilter());
	        registrationBean.addUrlPatterns("/domain/*");
	        registrationBean.addUrlPatterns("/course/*");
	        //registrationBean.addUrlPatterns("/welcome/*");
	        return registrationBean;
	    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    
	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty("error.path", "/index.html");

		// Set to false to turn-off Spring Boot's error page. Unhandled
		// exceptions will be handled by container in the usual way.
		props.setProperty("error.whitelabel.enabled", "true");
        SpringApplication.run(Application.class, args);
    }
	 /**
     * Set error pages for specific error response codes
     */
   
   
}
