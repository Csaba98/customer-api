package com.customer.app.exception;

public class CustomerAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 8844348024373820133L;

	public CustomerAlreadyExistsException(String errorMsg) {
		super(errorMsg);
	}

}
