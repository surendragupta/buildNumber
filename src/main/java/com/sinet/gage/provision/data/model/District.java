package com.sinet.gage.provision.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity class for District
 * 
 * @author Team Gage
 *
 */
@Entity
@Table(name="district",schema="provision")
public class District implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id	
	@Column(name= "domain_id")
	private Long domainId;
	
	@Column(name= "name")	
	private String name;
	
	@Column(name= "userspace")	
	private String userSpace;
	
	@Column(name= "admin_user")	
	private String adminUser;	
	
	@OneToMany(mappedBy= "district", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<School> schoolList = new ArrayList<School>();
	
	@Embedded
	private Administrator administrator;
	
	@Embedded
	private License license;
	
	@Embedded
	private Pilot pilot;
	
	/**
	 * @return the domainId
	 */
	public Long getDomainId() {
		return domainId;
	}
	
	/**
	 * @param domainId the domainId to set
	 */
	public void setDomainId(Long domainId) {
		this.domainId = domainId;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
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
	 * @return the schoolList
	 */
	public List<School> getSchoolList() {
		return schoolList;
	}
	/**
	 * @param schoolList the schoolList to set
	 */
	public void setSchoolList(List<School> schoolList) {
		this.schoolList = schoolList;
	}
	/**
	 * @return the adminUser
	 */
	public String getAdminUser() {
		return adminUser;
	}
	/**
	 * @param adminUser the adminUser to set
	 */
	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
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
