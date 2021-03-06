package com.scb.assignment.bookStoreRestApi.jsonObject;

import org.springframework.http.HttpStatus;

public class CommonResponse {
	private Boolean success;
	private HttpStatus status;
	private String message;

	public CommonResponse() {
	}

	public CommonResponse(Boolean success, HttpStatus status, String message) {
		super();
		this.success = success;
		this.status = status;
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CommonResponse [success=" + success + ", status=" + status + ", message=" + message + "]";
	}

}
