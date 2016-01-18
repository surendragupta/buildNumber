package com.sinet.gage.provision.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sinet.gage.provision.annotation.ValidDate;

public class BaseDomainRequest {

	@NotNull(message = "error.domainname.notnull")
	@JsonProperty("name")
	private String name;

	@NotNull(message = "error.userspace.notnull")
	@JsonProperty("loginPrefix")
	private String loginPrefix;

	@NotNull(message = "error.reference.notnull")
	@JsonProperty("externalId")
	private String externalId;
	
	@NotNull(message = "error.licensetype.notnull")
	@JsonProperty("licenseType")
	private String licenseType;
	
	@NotNull(message = "error.licensepool.notnull")
	@JsonProperty("licensePool")
	private String licensePool;

	@JsonProperty("numbersOfLicense")
	private int numbersOfLicense;

	@JsonProperty("pilotDomain")
	private boolean pilotDomain;

	@ValidDate(message="error.pilot.endate.invalid")
	@JsonProperty("pilotEndDate")
	private String pilotEndDate;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the loginPrefix
	 */
	public String getLoginPrefix() {
		return loginPrefix;
	}

	/**
	 * @param loginPrefix
	 *            the loginPrefix to set
	 */
	public void setLoginPrefix(String loginPrefix) {
		this.loginPrefix = loginPrefix;
	}

	/**
	 * @return the externalId
	 */
	public String getExternalId() {
		return externalId;
	}

	/**
	 * @param externalId
	 *            the externalId to set
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	

	/**
	 * @return the numbersOfLicense
	 */
	public int getNumbersOfLicense() {
		return numbersOfLicense;
	}

	/**
	 * @param numbersOfLicense the numbersOfLicense to set
	 */
	public void setNumbersOfLicense(int numbersOfLicense) {
		this.numbersOfLicense = numbersOfLicense;
	}

	/**
	 * @return the pilotDomain
	 */
	public boolean isPilotDomain() {
		return pilotDomain;
	}

	/**
	 * @param pilotDomain
	 *            the pilotDomain to set
	 */
	public void setPilotDomain(boolean pilotDomain) {
		this.pilotDomain = pilotDomain;
	}

	/**
	 * @return the pilotEndDate
	 */
	public String getPilotEndDate() {
		return pilotEndDate;
	}

	/**
	 * @param pilotEndDate
	 *            the pilotEndDate to set
	 */
	public void setPilotEndDate(String pilotEndDate) {
		this.pilotEndDate = pilotEndDate;
	}

	/**
	 * @return the licenseType
	 */
	public String getLicenseType() {
		return licenseType;
	}

	/**
	 * @param licenseType the licenseType to set
	 */
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	/**
	 * @return the licensePool
	 */
	public String getLicensePool() {
		return licensePool;
	}

	/**
	 * @param licensePool the licensePool to set
	 */
	public void setLicensePool(String licensePool) {
		this.licensePool = licensePool;
	}

}
