package com.sinet.gage.provision.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

/**
 * Date utiltiy class converts dates to ISO format
 * string, converts strings to date and add days to 
 * date 
 * @author Team Gage
 *
 */
public class DateUtil {
	
	private static final DateTimeFormatter fmt = DateTimeFormat.forPattern("MM-dd-yyyy");
	private static final DateTimeFormatter isoFmt = new DateTimeFormatterBuilder()
	        .appendPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
	        .toFormatter();
	/**
	 * Parse from string of date in MM-dd-yyyy format
	 * to date
	 * 
	 * @param date 
	 *        date in string format 
	 * @return
	 *        Date object parsed from string
	 */
	public Date parseFromMDY(String date) {
		return fmt.parseDateTime(date).toDate();
	}
	
	/**
	 * Format current date formatted as MM-dd-yyyy
	 * 
	 * @return
	 *        Current date formated as MM-dd-yyyy
	 */
	public String toMDYFormat( ) {
		LocalDateTime dateTime = LocalDateTime.now();
		return dateTime.toString(fmt);
	}
	
	/**
	 * Format current date formatted as MM-dd-yyyy
	 * 
	 * @return
	 *        Current date formated as MM-dd-yyyy
	 */
	public String toMDYFormat( Date date ) {
		return fmt.print( date.getTime() );
	}
	
	/**
	 * Format current date in ISO format
	 * 
	 * @return
	 *        current date formatted
	 */
	public String toISODateFormat(  ) {
		LocalDateTime dateTime = LocalDateTime.now();
		return dateTime.toString(isoFmt);
	}
	
	/**
	 * Format any date in ISO format
	 * 
	 * @param date
	 *        Date to be formatted
	 * @return
	 *        parameter date formatted
	 */
	public String toISODateFormat( Date date ) {
		return isoFmt.print(date.getTime());
	}
	
	/**
	 * Format any date string from MM-dd-yyyy to 
	 * ISO format
	 * 
	 * @param date
	 *       date string in MM-dd-yyyy format  
	 * @return
	 *        parameter string in ISO format
	 */
	public String toISODateFormat( String date ) {
		LocalDateTime dateTime = fmt.parseLocalDateTime(date);
		return dateTime.toString(isoFmt);
	}
	
	/**
	 * Adds number of days to current date
	 * 
	 * @param days
	 *        Number of days to be added
	 * @return
	 *        New date after adding number of days
	 */
	public Date addDays( int days ) {
		LocalDateTime startDate = new LocalDateTime();
		return startDate.plusDays(days).toDate();
	}
	
	/**
	 * Adds number of days to date string 
	 * 
	 * @param date
	 *          date string in MM-dd-yyyy format
	 * @param days
	 *        Number of days to be added
	 * @return
	 *        New date after adding number of days
	 */
	public Date addDays(String date, int days) {
		LocalDateTime startDate = fmt.parseLocalDateTime(date);
		return startDate.plusDays(days).toDate();
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public Date pilotEndDate(String date, int defaultDays) {
		if (StringUtils.isBlank(date)) {
			return addDays(defaultDays);
		} else {
			return parseFromMDY(date);
		}
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public String subscriptionStartDate(String date) {
		if (StringUtils.isBlank(date)) {
			return toISODateFormat(toMDYFormat());
		} else {
			return toISODateFormat(date);
		}
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public String subscriptionEndDate(String date, int defaultDays) {
		if (StringUtils.isBlank(date)) {
			Date endDate = addDays(toMDYFormat(), defaultDays );
			return toISODateFormat(endDate);
		} else {
			return toISODateFormat(date);
		}
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public String subscriptionEndDateFromStartDate(String StartDate, String endDate, int defaultDays) {
		if (StringUtils.isBlank(endDate)) {
			Date newEndDate = addDays(StartDate, defaultDays );
			return toISODateFormat(newEndDate);
		} else {
			return toISODateFormat(endDate);
		}
	}

}
