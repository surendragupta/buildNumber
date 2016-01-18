package com.sinet.gage.provision.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Team Gage
 *
 */
@XmlRootElement(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private long userId;
	private String username;
	private String password;
	private long domainId;
	private String firstName;
	private String lastName;
	private String entities;
	
	private String passwordquestion;
	private String passwordanswer;
	private String email;
	private String reference;
	private String flags;
	private String rights;


	public User() {
	}

	@XmlElement
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public long getDomainId() {
		return domainId;
	}

	public void setDomainId(long domainId) {
		this.domainId = domainId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the entities
	 */
	public String getEntities() {
		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(String entities) {
		this.entities = entities;
	}

	public String getPasswordquestion() {
		return passwordquestion;
	}

	public void setPasswordquestion(String passwordquestion) {
		this.passwordquestion = passwordquestion;
	}

	public String getPasswordanswer() {
		return passwordanswer;
	}

	public void setPasswordanswer(String passwordanswer) {
		this.passwordanswer = passwordanswer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}
	
	

}
