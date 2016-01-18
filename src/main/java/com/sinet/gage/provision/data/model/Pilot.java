package com.sinet.gage.provision.data.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Team Gage
 *
 */
@Embeddable
public class Pilot {

	@Column(name = "pilot")
	private boolean pilot;

	@Column(name = "pilot_end_date")
	private Date pilotEndDate;

	@Column(name = "pilot_start_date")
	private Date pilotStartDate;
	
	public Pilot() {
	}
	
	public Pilot( boolean pilot, Date pilotEndDate ) {
		super();
		this.pilot = pilot;
		this.pilotStartDate = new Date();
		this.pilotEndDate = pilotEndDate;
	}

	/**
	 * @return the pilot
	 */
	public boolean isPilot() {
		return pilot;
	}

	/**
	 * @param pilot
	 *            the pilot to set
	 */
	public void setPilot(boolean pilot) {
		this.pilot = pilot;
	}

	/**
	 * @return the pilotEndDate
	 */
	public Date getPilotEndDate() {
		return pilotEndDate;
	}

	/**
	 * @param pilotEndDate
	 *            the pilotEndDate to set
	 */
	public void setPilotEndDate(Date pilotEndDate) {
		this.pilotEndDate = pilotEndDate;
	}

	/**
	 * @return the pilotStartDate
	 */
	public Date getPilotStartDate() {
		return pilotStartDate;
	}

	/**
	 * @param pilotStartDate
	 *            the pilotStartDate to set
	 */
	public void setPilotStartDate(Date pilotStartDate) {
		this.pilotStartDate = pilotStartDate;
	}

}
