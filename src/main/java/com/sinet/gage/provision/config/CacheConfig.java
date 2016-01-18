package com.sinet.gage.provision.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * @author Team Gage
 *
 */
@Configuration
@EnableCaching
public class CacheConfig {

	/**
	 * 
	 * @return
	 */
	@Bean
	EhCacheCacheManager ehCacheCacheManager() {
		return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
	}

	/**
	 * 
	 * @return
	 */
	@Bean
	EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheManagerFactoryBean.setShared(true);
		ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		return ehCacheManagerFactoryBean;
	}

}
