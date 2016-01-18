package com.sinet.gage.provision.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Pojo class for Edit school domain action
 * 
 * @author Team Gage
 *
 */
public class EditSchoolDomainRequest extends SchoolDomainRequest {

	@NotNull(message = "error.domainid.notnull")
	@JsonProperty("domainid")
	private Long domainId;

	/**
	 * @return the domainId
	 */
	public Long getDomainId() {
		return domainId;
	}

	/**
	 * @param domainId
	 *            the domainId to set
	 */
	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

}
