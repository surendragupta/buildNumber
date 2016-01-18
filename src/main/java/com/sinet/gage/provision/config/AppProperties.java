package com.sinet.gage.provision.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Bean to bind properties from application.properties
 * 
 * @author Team Gage
 *
 */
@Configuration
@ConfigurationProperties
public class AppProperties {

	@Value("${cros.allowOrigin}")
	private String crosAllowOrigin;

	@Value("${cros.allowMethods}")
	private String crosAllowMethods;

	@Value("${cros.maxAge}")
	private String crosMaxAge;

	@Value("${cros.allowHeaders}")
	private String crosAllowHeader;
	
	@Value("${admin.username}")
	private String defaultAdminUserName;
	
	@Value("${admin.firstname}")
	private String defaultAdminFirstName;
	
	@Value("${admin.lastname}")
	private String defaultAdminLastName;
	
	@Value("${admin.reference}")
	private String defaultAdminExternalId;
	
	@Value("${admin.flags}")
	private String defaultAdminFlags;	
	
	@Value("${admin.district.name}")
	private String defaultDrictAdminName;

    @Value("${domain.course-catalog-id}")
	private String courseCatalogDomainId;
	
	@Value("${domain.customer-id}")
	private String customerDomainId;
	
	@Value("${domain.district.domain-id}")
	private String templateDistrictDomainId;
	
	@Value("${domain.school.domain-id}")
	private String templateSchoolDomainId;
	
	@Value("${domain.pilot.run.days}")
	private int defaultPilotRunInDays;
	
	@Value("${domain.subscription.days}")
	private int defaultSubscriptionInDays;
	
	@Value("${domain.course.subscription.days}")
	private String defaultCourseSubscriptionInDays;

	public String getCrosAllowOrigin() {
		return crosAllowOrigin;
	}

	public String getCrosAllowMethods() {
		return crosAllowMethods;
	}

	public String getCrosMaxAge() {
		return crosMaxAge;
	}

	public String getCrosAllowHeader() {
		return crosAllowHeader;
	}

	/**
	 * @return the defaultAdminUserName
	 */
	public String getDefaultAdminUserName() {
		return defaultAdminUserName;
	}

	/**
	 * @return the defaultAdminFirstName
	 */
	public String getDefaultAdminFirstName() {
		return defaultAdminFirstName;
	}

	/**
	 * @return the defaultAdminLastName
	 */
	public String getDefaultAdminLastName() {
		return defaultAdminLastName;
	}

	/**
	 * @return the defaultAdminExternalId
	 */
	public String getDefaultAdminExternalId() {
		return defaultAdminExternalId;
	}

	/**
	 * @return the defaultAdminFlags
	 */
	public String getDefaultAdminFlags() {
		return defaultAdminFlags;
	}

	/**
	 * @return the defaultDrictAdminName
	 */
	public String getDefaultDrictAdminName() {
		return defaultDrictAdminName;
	}

	public String getCourseCatalogDomainId() {
		return courseCatalogDomainId;
	}

	public String getCustomerDomainId() {
		return customerDomainId;
	}

	/**
	 * @return the templateDistrictDomainId
	 */
	public String getTemplateDistrictDomainId() {
		return templateDistrictDomainId;
	}

	/**
	 * @return the templateSchoolDomainId
	 */
	public String getTemplateSchoolDomainId() {
		return templateSchoolDomainId;
	}

	/**
	 * @return the defaultPilotRunInDays
	 */
	public int getDefaultPilotRunInDays() {
		return defaultPilotRunInDays;
	}

	/**
	 * @return the defaultSubscriptionInDays
	 */
	public int getDefaultSubscriptionInDays() {
		return defaultSubscriptionInDays;
	}

	/**
	 * @return the defaultCourseSubscriptionInDays
	 */
	public String getDefaultCourseSubscriptionInDays() {
		return defaultCourseSubscriptionInDays;
	}
	
	

}
