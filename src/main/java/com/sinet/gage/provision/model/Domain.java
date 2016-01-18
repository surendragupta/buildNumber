package com.sinet.gage.provision.model;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Team Gage
 *
 */
@XmlRootElement(name = "domain")
public class Domain {

	private String domainId;
	
	@NotNull(message = "error.domainname.notnull")
	private String name;
	
	@NotNull(message = "error.userspace.notnull")
	private String userspace;

	@NotNull(message = "error.parentid.notnull")
	private String parentId;
	
	private String reference;

	public Domain() {}
	
	public Domain(String domainId) {
		this.domainId = domainId;
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
	 * @return the userspace
	 */
	public String getUserspace() {
		return userspace;
	}

	/**
	 * @param userspace the userspace to set
	 */
	public void setUserspace(String userspace) {
		this.userspace = userspace;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the domainId
	 */
	public String getDomainId() {
		return domainId;
	}

	/**
	 * @param domainId the domainId to set
	 */
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	
}
