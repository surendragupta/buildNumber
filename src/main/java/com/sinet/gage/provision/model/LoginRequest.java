package com.sinet.gage.provision.model;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Team Gage
 *
 */
@XmlRootElement(name = "user")
public class LoginRequest {

	@NotNull(message = "error.username.notnull")
	private String userName;

	@NotNull(message = "error.userpassword.notnull")
	private String userPassword;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}
