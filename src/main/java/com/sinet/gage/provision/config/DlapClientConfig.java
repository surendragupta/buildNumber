package com.sinet.gage.provision.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Team Gage
 *
 */
@Configuration
@ComponentScan(basePackages={"com.sinet.gage.dlap.clients", "com.sinet.gage.dlap.config","com.sinet.gage.dlap.utils"})
public class DlapClientConfig {

}
