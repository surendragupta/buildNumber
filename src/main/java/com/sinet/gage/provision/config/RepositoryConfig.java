package com.sinet.gage.provision.config;


import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * @author Team Gage
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.sinet.gage.provision.data.repository"})
@EntityScan(basePackages= {"com.sinet.gage.provision.data.model"})
public class RepositoryConfig {

}
