package com.sinet.gage.provision.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinet.gage.dlap.entities.UserRequest;
import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.data.model.Administrator;
import com.sinet.gage.provision.data.model.District;
import com.sinet.gage.provision.data.model.License;
import com.sinet.gage.provision.data.model.Pilot;
import com.sinet.gage.provision.data.model.School;
import com.sinet.gage.provision.model.DistrictDomainRequest;
import com.sinet.gage.provision.model.Domain;
import com.sinet.gage.provision.model.EditSchoolDomainRequest;
import com.sinet.gage.provision.model.SchoolDomainRequest;

/**
 * @author Team.Gage
 *
 */
@Component
public class PojoMapper {

	private DateUtil dateUtil = new DateUtil();
	
	@Autowired
	AppProperties properties;
	
	/**
	 * @param user
	 * @return admin
	 */
	public Administrator mapToAdministrator(UserRequest user) {
		Administrator admin = new Administrator();
		admin.setFirstName(user.getFirstname());
		admin.setLastName(user.getLastname());
		admin.setPassword(user.getPassword());
		admin.setUserName(user.getUsername());
		return admin;
	}
	
	/**
	 * 
	 * @param domain
	 * @param user
	 * @param domainId
	 * @return
	 */
	public District mapToDistrict(DistrictDomainRequest domain, UserRequest user, String domainId) {
		Administrator admin = mapToAdministrator( user );

		License license = new License(domain.getLicenseType(), domain.getNumbersOfLicense(), domain.getLicensePool());

		Pilot pilot = new Pilot(domain.isPilotDomain(), dateUtil.pilotEndDate( domain.getPilotEndDate(), properties.getDefaultPilotRunInDays() ));

		District district = new District();
		district.setName(domain.getName());
		district.setDomainId(Long.parseLong(domainId));
		district.setUserSpace(domain.getLoginPrefix());
		district.setAdministrator(admin);
		district.setAdminUser(properties.getDefaultDrictAdminName());
		district.setLicense(license);
		district.setPilot(pilot);

		return district;
	}
	
	/**
	 * 
	 * @param request
	 * @return district
	 */
	public District mapToDistrictForEditing(DistrictDomainRequest domain, String domainId) {
		
		License license = new License(domain.getLicenseType(), domain.getNumbersOfLicense(), domain.getLicensePool());

		Pilot pilot = new Pilot(domain.isPilotDomain(), dateUtil.pilotEndDate( domain.getPilotEndDate(), properties.getDefaultPilotRunInDays() ));

		District district = new District();
		district.setName(domain.getName());
		district.setDomainId(Long.parseLong(domainId));
		district.setUserSpace(domain.getLoginPrefix());
		district.setAdminUser(properties.getDefaultDrictAdminName());
		district.setLicense(license);
		district.setPilot(pilot);

		return district;
	}
	
	/**
	 * Maps between domain details to domain entity
	 * 
	 * @param domainData
	 *            CreateDistrictRequest pojo contains domain details
	 * @return List of domains pojos
	 */
	public List<Domain> mapToDomainList(DistrictDomainRequest domainData) {
		List<Domain> domainList = new ArrayList<Domain>();

		Domain domain = new Domain();
		domain.setName(domainData.getName());
		domain.setParentId(domainData.getState());
		domain.setReference(domainData.getExternalId());
		domain.setUserspace(domainData.getLoginPrefix());
		domainList.add(domain);
		return domainList;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public List<Domain> mapToDomains(SchoolDomainRequest request) {
		List<Domain> listOfDomains = new ArrayList<>();
		Domain domain = new Domain();
		domain.setName(request.getName());
		domain.setParentId(request.getDistrictDomainId().toString());
		domain.setReference(request.getExternalId());
		domain.setUserspace(request.getLoginPrefix());
		listOfDomains.add(domain);
		return listOfDomains;
	}
	
	/**
	 * 
	 * @param request
	 * @param user
	 * @param domainId
	 * @return
	 */
	public School mapToSchool(SchoolDomainRequest request, UserRequest user, String domainId) {
		Administrator admin = mapToAdministrator(user);

		License license = new License(request.getLicenseType(), request.getNumbersOfLicense(), request.getLicensePool());

		Pilot pilot = new Pilot(request.isPilotDomain(), dateUtil.pilotEndDate( request.getPilotEndDate(), properties.getDefaultPilotRunInDays() ));

		School school = new School();
		school.setName(request.getName());
		school.setSchoolId(Long.parseLong(domainId));
		school.setUserSpace(request.getLoginPrefix());
		school.setAdministrator(admin);
		school.setLicense(license);
		school.setPilot(pilot);

		return school;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public School mapToSchoolForEditing(EditSchoolDomainRequest request) {
		License license = new License(request.getLicenseType(), request.getNumbersOfLicense(), request.getLicensePool());

		Pilot pilot = new Pilot(request.isPilotDomain(), dateUtil.pilotEndDate( request.getPilotEndDate(), properties.getDefaultPilotRunInDays() ));

		School school = new School();
		school.setName(request.getName());
		school.setSchoolId(request.getDomainId());
		school.setUserSpace(request.getLoginPrefix());
		school.setLicense(license);
		school.setPilot(pilot);

		return school;
	}
}
