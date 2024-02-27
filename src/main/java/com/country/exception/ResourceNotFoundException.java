package com.country.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super("Country is not available..");
	}

	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
