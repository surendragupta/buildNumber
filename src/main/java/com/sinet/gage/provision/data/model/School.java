package com.sinet.gage.provision.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/** 
 * Entity class for school 
 * 
 * @author Team Gage
 *
 */
@Entity
@Table(name="school",schema="provision")
public class School implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name= "school_id")
	private Long schoolId;
	
	@Column(name= "sname")
	private String name;
	
	@Column(name= "userspace")
	private String userSpace;
	
	@ManyToOne
	@JoinColumn(name="domain_id",referencedColumnName="domain_id")
	private District district;
	
	@Embedded
	Administrator administrator;
	
	@Embedded
	private License license;
	
	@Embedded
	private Pilot pilot;
	
	/**
	 * @return the schoolId
	 */
	public Long getSchoolId() {
		return schoolId;
	}

	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	/**
	 * @return the sName
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param sName the sName to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the userSpace
	 */
	public String getUserSpace() {
		return userSpace;
	}
	
	/**
	 * @param userSpace the userSpace to set
	 */
	public void setUserSpace(String userSpace) {
		this.userSpace = userSpace;
	}
	
	/**
	 * @return the district
	 */
	public District getDistrict() {
		return district;
	}
	/**
	 * @param district the district to set
	 */
	public void setDistrict(District district) {
		this.district = district;
	}
	/**
	 * @return the administrator
	 */
	public Administrator getAdministrator() {
		return administrator;
	}
	/**
	 * @param administrator the administrator to set
	 */
	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}

	/**
	 * @return the license
	 */
	public License getLicense() {
		return license;
	}

	/**
	 * @param license the license to set
	 */
	public void setLicense(License license) {
		this.license = license;
	}

	/**
	 * @return the pilot
	 */
	public Pilot getPilot() {
		return pilot;
	}

	/**
	 * @param pilot the pilot to set
	 */
	public void setPilot(Pilot pilot) {
		this.pilot = pilot;
	}
	
	

}
