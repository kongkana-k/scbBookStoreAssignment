package com.scb.assignment.bookStoreRestApi.exception;

public class InvalidTokenException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7903841662289111040L;

	public InvalidTokenException(String message) {
		super(message);
	}
}
