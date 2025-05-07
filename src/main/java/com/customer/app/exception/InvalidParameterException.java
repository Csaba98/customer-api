package com.customer.app.exception;

public class InvalidParameterException extends RuntimeException {
	
	private static final long serialVersionUID = -4858095508259820658L;

	public InvalidParameterException(String param) {
		super(String.format("Invalid '%s' parameter!", param));
	}

}
