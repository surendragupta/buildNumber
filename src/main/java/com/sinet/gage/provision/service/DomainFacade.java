package com.sinet.gage.provision.service;

import java.util.List;
import java.util.Map;

import com.sinet.gage.dlap.exception.AvailabilityLicenseException;
import com.sinet.gage.dlap.exception.DomainDuplicateException;
import com.sinet.gage.dlap.exception.DomainNameDuplicateException;
import com.sinet.gage.provision.model.DistrictDomainRequest;
import com.sinet.gage.provision.model.EditSchoolDomainRequest;
import com.sinet.gage.provision.model.SchoolDomainRequest;

/**
 * 
 * @author Team Gage
 *
 */
public interface DomainFacade {

	public boolean createDistrictDomain( String token, DistrictDomainRequest domainData ) throws DomainDuplicateException, DomainNameDuplicateException;

	public boolean editDistrictDomain( String token, DistrictDomainRequest domainData, String domainId ) throws DomainNameDuplicateException;

	public Map<String, List<String>> getSubscriptionsForDomain( String token, String domainId );
	
	public Map<String, List<String>> getSubscriptionsForSchool( String token, String domainId );

	boolean createSchool(String token, SchoolDomainRequest request) throws DomainDuplicateException, DomainNameDuplicateException, AvailabilityLicenseException;

	boolean editSchool(String token, EditSchoolDomainRequest request) throws DomainNameDuplicateException, AvailabilityLicenseException;
	
}
