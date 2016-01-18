package com.sinet.gage.provision.util;

/**
 * Constants class for all message properties keys
 * 
 * @author Team Gage
 *
 */
public class MessageConstants {
	
	// CROS Filter properties
	public static final String CROS_ALLOW_ORIGIN = "cros.allow.origin";
	public static final String CROS_ALLOW_METHODS = "cros.allow.methods";
	public static final String CROS_MAX_AGE = "cros.max.age";
	public static final String CROS_ALLOW_HEADER = "cros.allow.headers";
	
	// User Authentication message codes
	public static final String LOGIN_AUTHENTICATED = "user.login.authenticated";
	public static final String LOGIN_UNAUTHORIZED = "user.login.unauthorized";
	public static final String LOGIN_INVALID = "user.login.invalid";
	public static final String LOGIN_UNAVAILABLE_RIGHTS = "user.login.unavailable.rights";
	
	// User logout message codes
	public static final String LOGOUT_SUCCESS = "user.logout.success";
	public static final String LOGOUT_FAIL = "user.logout.fail";
	
	// User message codes
	public static final String DOMAIN_USER_FOUND = "domain.user.found";
	public static final String DOMAIN_USER_NOTFOUND = "domain.user.notfound";
	public static final String USER_FOUND = "user.found";
	public static final String USER_NOTFOUND = "user.notfound";
	public static final String USER_CREATED = "user.created";
	public static final String USER_CREATION_FAIL = "user.creation.fail";
	public static final String ADMIN_USER_CREATED = "admin.user.created";
	public static final String ADMIN_USER_CREATION_FAIL = "admin.user.creation.fail";
	public static final String USER_UPDATED = "user.update";
	public static final String USER_UPDATION_FAIL = "user.update.fail";
	
	// Right message codes
	public static final String DOMAIN_RIGHTS_FOUND = "domain.rights.found";
	public static final String DOMAIN_RIGHTS_NOTFOUND = "domain.rights.notfound";
	public static final String COURSE_RIGHTS_FOUND = "course.rights.found";
	public static final String COURSE_RIGHTS_NOTFOUND = "course.rights.notfound";
	public static final String SECTION_RIGHTS_FOUND = "section.rights.found";
	public static final String SECTION_RIGHTS_NOTFOUND = "section.rights.notfound";
	public static final String ENTITY_RIGHTS_FOUND = "entity.rights.found";
	public static final String ENTITY_RIGHTS_NOTFOUND = "entity.rights.notfound";
	public static final String EFFECTIVE_RIGHTS_FOUND = "effective.rights.found";
	public static final String EFFECTIVE_RIGHTS_NOTFOUND = "effective.rights.notfound";
	public static final String DOMAIN_RIGHTS_UPDATE_SUCCESS = "rights.update.success";
	public static final String DOMAIN_RIGHTS_UPDATE_FAIL = "rights.update.fail";
	
	// Domain message code
	public static final String DOMAIN_FOUND = "domain.found";
	public static final String DOMAIN_NOT_FOUND = "domain.notfound";
	public static final String DOMAINS_FOUND = "domains.found";
	public static final String DOMAINS_NOT_FOUND = "domains.notfound";
	public static final String DOMAIN_CREATED = "domains.created";
	public static final String DOMAIN_NOT_CREATED = "domains.notcreated";
	public static final String DOMAIN_PREFIX_DUPLICATED = "domains.prefix.duplicated";
	public static final String DOMAIN_UPDATED = "domains.updated";
	public static final String DOMAIN_NOT_UPDATED= "domains.notupdated";
	public static final String DOMAIN_NAME_DUPLICATED = "domains.name.duplicated";
	
	public static final String LICENSE_UNAVAILABILITY = "error.license.unavailability";
	
	// Course message code
	public static final String DOMAIN_COURSE_FOUND = "domain.course.found";
	public static final String DOMAIN_COURSE_NOTFOUND = "domain.course.notfound";
	public static final String DOMAIN_COURSE_COPIED = "course.copied";
	public static final String DOMAIN_COURSE_NOTCOPIED = "course.copy.fail";
	
	// resource message code
	public static final String DOMAIN_RESOURCE_COPIED = "resource.copied";
	public static final String DOMAIN_RESOURCE_NOTCOPIED = "resource.copy.fail";
	
	// subscription message code
	public static final String SUBSCRIPTION_CREATED = "subscription.created";
	public static final String SUBSCRIPTION_CREATION_FAIL = "subscription.creation.fail";
	

}
