package com.sinet.gage.provision.util;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

public class DateUtilTest {
	
	DateUtil dateUtil;
	
	private static final DateTimeFormatter fmt = DateTimeFormat.forPattern("MM-dd-yyyy");
	SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
	SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	@Before
	public void before(){
		dateUtil = new DateUtil();
	}

	@Test
	public void testParseFromMDY() {
		Date expected = Date.from(LocalDate.of(1990, 12, 10).atStartOfDay(ZoneId.systemDefault()).toInstant());		

		Date date = dateUtil.parseFromMDY("12-10-1990");
		
		assertTrue( expected.equals(date));		
	}

	@Test
	public void testToMDYFormat() {
		String expected = formatter.format(Date.from( LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		String date = dateUtil.toMDYFormat();
		
		assertTrue( expected.equals(date));		
	}

/*	@Test
	public void testToISODateFormat() {
		String expected = isoFormatter.format(Date.from( LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		Date date =parseFromMDY( dateUtil.toISODateFormat());
		String date1=formatter.format(Date.from(date.toInstant()));
		
		assertTrue( expected.equals(date));	
	}*/

	@Test
	public void testToISODateFormatDate() {
		String expected =isoFormatter.format(Date.from(LocalDate.of(1990, 12, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()));		

		String date = dateUtil.toISODateFormat(parseFromMDY("12-10-1990"));
		
		assertTrue( expected.equals(date));	
	}

	@Test
	public void testToISODateFormatString() {
		String expected =isoFormatter.format(Date.from(LocalDate.of(1990, 12, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()));		

		String date = dateUtil.toISODateFormat("12-10-1990");
		
		assertTrue( expected.equals(date));
	}

	@Test
	public void testAddDaysInt() {
		String expected =formatter.format( Date.from(LocalDate.now().plusDays(5l).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		String date=formatter.format(Date.from(dateUtil.addDays(5).toInstant()));
		
		assertTrue( expected.equals(date));
	}

	@Test
	public void testAddDaysStringInt() {
		Date expected = Date.from(LocalDate.of(1990, 12, 10).plusDays(5l).atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		Date date = dateUtil.addDays("12-10-1990", 5);
		
		assertTrue( expected.equals(date));
	}

	@Test
	public void testPilotEndDate() {
		Date expected = Date.from(LocalDate.of(1990, 12, 10).atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		Date date = dateUtil.pilotEndDate("12-10-1990", 5);
		
		assertTrue( expected.equals(date));
	
	}

	@Test
	public void testSubscriptionStartDate() {
		String expected = isoFormatter.format(Date.from(LocalDate.of(1990, 12, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		String date = dateUtil.subscriptionStartDate("12-10-1990");
		
		assertTrue( expected.equals(date));
	}

	@Test
	public void testSubscriptionEndDate() {
		String expected = isoFormatter.format(Date.from(LocalDate.of(1990, 12, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		String date = dateUtil.subscriptionEndDate("12-10-1990",5);
		
		assertTrue( expected.equals(date));
		
	}
	
	public Date parseFromMDY(String date) {
		return fmt.parseDateTime(date).toDate();
	}

}
