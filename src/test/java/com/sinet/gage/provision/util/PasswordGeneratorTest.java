package com.sinet.gage.provision.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.sinet.gage.provision.util.PasswordGenerator;

public class PasswordGeneratorTest {

	PasswordGenerator generator;
	
	@Before
	public void before() {
		generator = PasswordGenerator.builder();
	}
	
	@Test
	public void testGeneratePasswordNotNull() {
		String password = generator.generate();
		assertNotNull(password);
	}
	
	@Test
	public void testGeneratedPasswordLength(){
		String password = generator.generate();
		assertEquals(12,password.length());
	}

	@Test
	public void testGeneratedPasswordsAreNotSame(){
		String passwordOne = generator.generate();
		String passwordTwo = generator.generate();
		assertNotEquals(passwordOne,passwordTwo);
	}
}
