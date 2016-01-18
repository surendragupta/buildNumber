package com.sinet.gage.provision.data.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Team Gage
 *
 */
@Embeddable
public class License {
	
	@Column(name ="license_type" )
	private String licenseType;
	
	@Column(name ="license_pool" )
	private String licensePool;
	
	@Column(name ="number_of_license" )
	private int numberOfLicense;
	
	public License() {
	}
	
	public License( String licenseType, int numberOflicencse, String licensePool ) {
		super();
		this.licenseType = licenseType;
		this.numberOfLicense = numberOflicencse;
		this.licensePool = licensePool;
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

	/**
	 * @return the numberOfLicense
	 */
	public int getNumbersOfLicense() {
		return numberOfLicense;
	}

	/**
	 * @param numberOfLicense the numberOfLicense to set
	 */
	public void setNumbersOfLicense(int numberOfLicense) {
		this.numberOfLicense = numberOfLicense;
	}

}
