package com.scb.assignment.bookStoreRestApi.exception;

public class DuplicateItemException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -786877168815256730L;

	public DuplicateItemException(String message) {
		super(message);
	}
}
