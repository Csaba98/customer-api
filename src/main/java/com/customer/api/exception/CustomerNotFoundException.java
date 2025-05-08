package com.customer.api.exception;

public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5088188443158469888L;

	public CustomerNotFoundException(String errorMsg) {
		super(errorMsg);
	}
}
