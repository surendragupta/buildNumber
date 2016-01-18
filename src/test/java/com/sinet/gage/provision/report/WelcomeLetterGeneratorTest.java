package com.sinet.gage.provision.report;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import com.sinet.gage.provision.data.model.Administrator;
import com.sinet.gage.provision.data.model.District;
import com.sinet.gage.provision.data.model.School;

/**
 * Test class for {@link com.sinet.gage.provision.report.WelcomeLetterGenerator}
 * 
 * @author Team Gage
 *
 */
public class WelcomeLetterGeneratorTest {
	
	WelcomeLetterGenerator welcomeLetterGenerator = new WelcomeLetterGenerator();

	/**
	 * Test method for {@link com.sinet.gage.provision.report.WelcomeLetterGenerator#generateWelcomeLetter(District, String, List)}
	 * 
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@Test
	public void testGenerateWelcomeLetter() throws InvalidFormatException, IOException {
		
		List<String> courses = new ArrayList<>();
		courses.add("Course Title One");
		courses.add("Course Title Two");
		courses.add("Course Title Three");		
		District district = createDistrict();
		
		InputStream inputStream = welcomeLetterGenerator.generateWelcomeLetter(district, "Anna Welch", courses);
		
		assertNotNull( inputStream );	
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.report.WelcomeLetterGenerator#generateWelcomeLetter(District, String, List)}
	 * 
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@Test
	public void testGenerateWelcomeLetterDistrictNull () throws InvalidFormatException, IOException {
		List<String> courses = new ArrayList<>();
		courses.add("Course Title One");

		InputStream inputStream = welcomeLetterGenerator.generateWelcomeLetter(null, "Anna Welch", courses);
		
		assertNotNull( inputStream );	
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.report.WelcomeLetterGenerator#generateWelcomeLetter(District, String, List)}
	 * 
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@Test
	public void testGenerateWelcomeLetterUserNameNull () throws InvalidFormatException, IOException {
		List<String> courses = new ArrayList<>();
		courses.add("Course Title One");
		courses.add("Course Title Two");
		District district = createDistrict();
		
		InputStream inputStream = welcomeLetterGenerator.generateWelcomeLetter(district, null, courses);
		
		assertNotNull( inputStream );	
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.report.WelcomeLetterGenerator#generateWelcomeLetter(District, String, List)}
	 * 
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@Test
	public void testGenerateWelcomeLetterNoCourse () throws InvalidFormatException, IOException {
		List<String> courses = new ArrayList<>();
		District district = createDistrict();

		InputStream inputStream = welcomeLetterGenerator.generateWelcomeLetter(district, null, courses);
		
		assertNotNull( inputStream );	
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.report.WelcomeLetterGenerator#generateWelcomeLetter(District, String, List)}
	 * 
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@Test
	public void testGenerateWelcomeLetterNullCourse () throws InvalidFormatException, IOException {
		District district = createDistrict();

		InputStream inputStream = welcomeLetterGenerator.generateWelcomeLetter(district, null, null);
		
		assertNotNull( inputStream );	
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.report.WelcomeLetterGenerator#generateWelcomeLetter(District, String, List)}
	 * 
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@Test
	public void testGenerateWelcomeLetterNoSchools() throws InvalidFormatException, IOException {
		
		List<String> courses = new ArrayList<>();
		courses.add("Course Title One");
		courses.add("Course Title Two");
		courses.add("Course Title Three");
		courses.add("Course Title Four");		
		District district = createDistrict();
		district.getSchoolList().clear();
		
		InputStream inputStream = welcomeLetterGenerator.generateWelcomeLetter(district, "Anna Welch", courses);
		
		assertNotNull( inputStream );	
	}

	private District createDistrict() {
		District district = new District();
		district.setAdminUser("Domain Administrator");
		district.setName("Acamedy School District");
		district.setUserSpace("asd-1234");
		district.setSchoolList(crateSchools());
		district.setAdministrator(creatAdministrator());
		return district;
	}

	private List<School> crateSchools() {
		List<School> schools = new ArrayList<>();
		School school = new School();
		school.setAdministrator(creatAdministrator());
		school.setName("Academy Elementary");
		school.setUserSpace("ael-1234");
		schools.add(school);
		return schools;
	}

	private Administrator creatAdministrator() {
		Administrator administrator = new Administrator();
		administrator.setFirstName("Admin");
		administrator.setLastName("User");
		administrator.setPassword("password");
		administrator.setUserName("administrator");
		return administrator;
	}
}