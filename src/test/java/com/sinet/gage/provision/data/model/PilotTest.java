package com.sinet.gage.provision.data.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

/**
 * Test class for {@link com.sinet.gage.provision.data.model.Pilot}
 * 
 * @author Team Gage
 *
 */
public class PilotTest {

	@Test
	public void testSetAndGetForEndDate() {
		Date date = new Date();
		Pilot pilot = new Pilot(true, date);

		assertEquals(date, pilot.getPilotEndDate());
	}

	@Test
	public void testSetAndGetForStartdate() {
		
		Date date = new Date();
		Pilot pilot = new Pilot();
		pilot.setPilot(Boolean.TRUE);
		pilot.setPilotStartDate(date);
		pilot.setPilotEndDate(new Date());
		
		assertTrue(pilot.isPilot());
		assertEquals( date, pilot.getPilotStartDate());
	}
}
