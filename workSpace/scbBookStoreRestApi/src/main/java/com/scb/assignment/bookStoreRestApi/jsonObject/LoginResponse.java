package com.scb.assignment.bookStoreRestApi.jsonObject;

public class LoginResponse {
	private Boolean success;
	private String token;

	public LoginResponse() {
	}

	public LoginResponse(String token) {
		super();
		success = true;
		this.token = token;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginResponse [success=" + success + ", token=" + token + "]";
	}

}
