package com.sinet.gage.provision.service.impl;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinet.gage.provision.data.model.District;
import com.sinet.gage.provision.data.repository.DistrictRepository;
import com.sinet.gage.provision.report.WelcomeLetterGenerator;
import com.sinet.gage.provision.service.WelcomeLetterService;

/**
 * Welcome letter service implementation
 * 
 * @author Team Gage
 *
 */
@Service("welcomeLetterService")
public class WelcomeLetterServiceImpl implements WelcomeLetterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeLetterServiceImpl.class);
	
	@Autowired
	DistrictRepository districtRepository;
	
	@Autowired
	WelcomeLetterGenerator wlGenerator;
	
	/**
	 * Creates welcome letter for the district domain
	 * 
	 * @param domainId
	 * @param userName
	 */
	@Override
	public InputStream createWelcomeLetter(Long domianId, String userName,
			List<String> courses) throws Exception {
		LOGGER.debug("Inside createWelcomeLetter Service implementation");
		District district = districtRepository.findOne(domianId);
		return wlGenerator.generateWelcomeLetter(district, userName, courses);
	}

}