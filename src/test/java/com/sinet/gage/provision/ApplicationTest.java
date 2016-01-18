package com.sinet.gage.provision;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.OutputCapture;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sinet.gage.dlap.Application;

/**
 * Test for Application configuration
 * 
 * @author Team Gage
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationTest {

	/**
	 * JUnit rule captures the output of test run in console
	 */
	@Rule
	public OutputCapture outputCapture = new OutputCapture();
	
	
	/**
	 * Executes the application and test if its exits without 
	 * error
	 * @throws Exception
	 */
	@Test
	public void testDefaultSettings() throws Exception {
		assertEquals(0, SpringApplication.exit(SpringApplication
				.run(Application.class)));
	}
}
