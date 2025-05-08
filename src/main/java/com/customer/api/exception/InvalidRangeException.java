package com.customer.api.exception;

public class InvalidRangeException extends RuntimeException {

	private static final long serialVersionUID = -2678381007222741066L;

	public InvalidRangeException(String param1, String param2) {
		super(String.format("Invalid range at '%s' and '%s' parameters!", param1, param2));
	}

}
