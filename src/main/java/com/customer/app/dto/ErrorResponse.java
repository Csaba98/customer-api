package com.customer.app.dto;

import java.time.LocalDateTime;

public class ErrorResponse extends ErrorResponseBase {

	private String error;

	public ErrorResponse(int status, LocalDateTime timestamp, String error, String path) {
		super(status, timestamp, path);
		this.setError(error);
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
