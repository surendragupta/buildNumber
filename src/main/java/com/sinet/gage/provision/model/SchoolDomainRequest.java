package com.sinet.gage.provision.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Pojo class for create school domain request
 * 
 * @author Team Gage
 *
 */
public class SchoolDomainRequest extends BaseDomainRequest {

	private boolean fullSubscription;
	
	private List<String> courseCatalogs;
	
	private List<String> courseSelection;
	
	@NotNull(message = "error.parentid.notnull")
	@JsonProperty("disrict")
	private Long districtDomainId;

	
	/**
	 * @return the districtDomainId
	 */
	public Long getDistrictDomainId() {
		return districtDomainId;
	}

	/**
	 * @param districtDomainId
	 *            the districtDomainId to set
	 */
	public void setDistrictDomainId(Long districtDomainId) {
		this.districtDomainId = districtDomainId;
	}

	/**
	 * @return the courseCatalogs
	 */
	public List<String> getCourseCatalogs() {
		return courseCatalogs;
	}

	/**
	 * @param courseCatalogs the courseCatalogs to set
	 */
	public void setCourseCatalogs(List<String> courseCatalogs) {
		this.courseCatalogs = courseCatalogs;
	}

	/**
	 * @return the courseSelection
	 */
	public List<String> getCourseSelection() {
		return courseSelection;
	}

	/**
	 * @param courseSelection the courseSelection to set
	 */
	public void setCourseSelection(List<String> courseSelection) {
		this.courseSelection = courseSelection;
	}

	/**
	 * @return the fullSubscription
	 */
	public boolean isFullSubscription() {
		return fullSubscription;
	}

	/**
	 * @param fullSubscription the fullSubscription to set
	 */
	public void setFullSubscription(boolean fullSubscription) {
		this.fullSubscription = fullSubscription;
	}
	
	
	
}
