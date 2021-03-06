package com.scb.assignment.bookStoreRestApi.jsonObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {
	private String username;
	private String password;
	@JsonProperty("date_of_birth")
	private String dateOfBirth;

	public CreateUserRequest() {
	}

	public CreateUserRequest(String username, String password, String dateOfBirth) {
		super();
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
	}

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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "CreateUserRequest [username=" + username + ", password=" + password + ", dateOfBirth=" + dateOfBirth
				+ "]";
	}

}
