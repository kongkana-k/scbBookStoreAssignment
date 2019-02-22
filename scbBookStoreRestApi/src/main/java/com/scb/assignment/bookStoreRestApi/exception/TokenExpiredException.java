package com.scb.assignment.bookStoreRestApi.exception;

public class TokenExpiredException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6084348760753480453L;

	public TokenExpiredException(String message) {
		super(message);
	}
}
