package com.wp.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoginModel {

	@Pattern(regexp = "[a-zA-Z0-9._]+",message = "only aplhanumeric characters allowed,except .,_ ")	
	private String username;
	@NotNull(message = "empty field not allowed")
	private String password;
	private String userType;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
