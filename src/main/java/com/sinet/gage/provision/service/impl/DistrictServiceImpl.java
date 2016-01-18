package com.sinet.gage.provision.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinet.gage.provision.config.AppProperties;
import com.sinet.gage.provision.data.model.Administrator;
import com.sinet.gage.provision.data.model.District;
import com.sinet.gage.provision.data.model.School;
import com.sinet.gage.provision.data.repository.DistrictRepository;
import com.sinet.gage.provision.data.repository.SchoolRepository;
import com.sinet.gage.provision.service.DistrictService;

/**
 * Service class to manage district and school entity
 * 
 * @author Team Gage
 *
 */
@Service("districtService")
public class DistrictServiceImpl implements DistrictService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DistrictServiceImpl.class);

	@Autowired
	AppProperties properties;

	@Autowired
	DistrictRepository districtRepository;

	@Autowired
	SchoolRepository schoolRepository;

	public final String EMPTY = StringUtils.EMPTY;

	
	/**
	 * 
	 * @param district
	 */
	@Override
	public District insertDistrict(District district) {
		return districtRepository.save(district);
	}

	/**
	 * 
	 * @param districtId
	 */
	@Override
	public District deleteDistrict(Long districtId) {
		District district = districtRepository.findOne(districtId);
		if (district != null) {
			districtRepository.delete(district);
		}
		return district;
	}

	/**
	 * 
	 * @param districtId
	 * @param school
	 */
	@Override
	public School insertSchool(Long districtId, School school) {
		District district = districtRepository.findOne(districtId);
		if (district != null) {
			school.setDistrict(district);
			school = schoolRepository.save(school);
			district.getSchoolList().add(school);
		}
		return school;
	}

	/**
	 * 
	 * @param schoolId
	 */
	@Override
	public void deleteSchool(Long schoolId) {
		schoolRepository.delete(schoolId);
	}

	/**
	 * 
	 * @param districtId
	 * @param school
	 * @return School entity
	 */
	@Override
	public School updateSchool(Long districtId, School school) {
		District district = districtRepository.findOne(districtId);
		School existingSchool = schoolRepository.findOne(school.getSchoolId());

		if (existingSchool == null) {
			Administrator admin = new Administrator();
			admin.setFirstName(properties.getDefaultAdminFirstName());
			admin.setLastName(properties.getDefaultAdminLastName());
			admin.setPassword(EMPTY);
			admin.setUserName(properties.getDefaultAdminUserName());
			school.setAdministrator(admin);
		} else {
			existingSchool.setName( school.getName() );
			existingSchool.setPilot( school.getPilot() );
			existingSchool.setLicense( school.getLicense() );
			school = existingSchool;
		}
		if (district != null) {
			school.setDistrict(district);
			school = schoolRepository.save(school);
			if (school != null) {
				district.getSchoolList().add(school);
				districtRepository.save(district);
			}
		}
		return school;
	}

	/**
	 * 
	 * @param district
	 * @return District entity
	 */
	@Override
	public District updateDistrict(District district) {
		District existingDistrict = districtRepository.findOne(district.getDomainId());

		if (existingDistrict == null) {
			Administrator admin = new Administrator();
			admin.setFirstName(properties.getDefaultAdminFirstName());
			admin.setLastName(properties.getDefaultAdminLastName());
			admin.setPassword(EMPTY);
			admin.setUserName(properties.getDefaultAdminUserName());
			district.setAdministrator(admin);
			return districtRepository.save(district);
		} else {
			existingDistrict.setName(district.getName());
			existingDistrict.setPilot(district.getPilot());
			existingDistrict.setLicense(district.getLicense());
			return districtRepository.save(existingDistrict);
		}		
	}

}
