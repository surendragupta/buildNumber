package com.sinet.gage.provision.service;

import java.io.InputStream;
import java.util.List;

/**
 * Welcome letter service interface
 * 
 * @author Team Gage
 *
 */
public interface WelcomeLetterService {

	/**
	 * Create welcome letter
	 * @param courses 
	 * 
	 * @return Welcome letter
	 */
	public InputStream createWelcomeLetter(Long districtId, String userName, List<String> courses ) throws Exception ;
	
	
}
