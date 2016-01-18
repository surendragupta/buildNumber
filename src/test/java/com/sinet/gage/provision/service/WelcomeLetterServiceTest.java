package com.sinet.gage.provision.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sinet.gage.provision.data.model.District;
import com.sinet.gage.provision.data.repository.DistrictRepository;
import com.sinet.gage.provision.report.WelcomeLetterGenerator;
import com.sinet.gage.provision.service.impl.WelcomeLetterServiceImpl;

/**
 * @author Team Gage
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class WelcomeLetterServiceTest {
	
	private static final Long DOMAIN_ID = 9101266L;

	private static final String USER_NAME = "Anna Welch";

	private static final List<String> COURSE_LIST = new ArrayList<>();

	@InjectMocks
	private WelcomeLetterService mockWelcomeLetterService = new WelcomeLetterServiceImpl();
	
	@Mock
	private WelcomeLetterGenerator mockWelcomeGenerator;
	
	@Mock
	private DistrictRepository mockDistrictRepository;
	
	@Mock
	private InputStream mockInputStream;

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.WelcomeLetterServiceImpl#createWelcomeLetter(java.lang.Long, java.lang.String, java.util.List)}.
	 * @throws Exception 
	 */
	@Test
	public void testCreateWelcomeLetter() throws Exception {
		District district = new District();
		when( mockDistrictRepository.findOne(anyLong())).thenReturn( district );
		when( mockWelcomeGenerator.generateWelcomeLetter(any(District.class), anyString(), anyListOf(String.class))).thenReturn(mockInputStream);
		
		InputStream result = mockWelcomeLetterService.createWelcomeLetter(DOMAIN_ID, USER_NAME, COURSE_LIST);
		
		assertNotNull(result);
		verify( mockDistrictRepository ).findOne(anyLong());
		verify( mockWelcomeGenerator ).generateWelcomeLetter(any(District.class), anyString(), anyListOf(String.class));
	}

}
