package com.customer.api.dto.error;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorsResponse extends ErrorResponseBase {

	private Map<String, String> error;

	public ErrorsResponse(int status, LocalDateTime timestamp, Map<String, String> error, String path) {
		super(status, timestamp, path);
		this.setError(error);
	}

	public Map<String, String> getError() {
		return error;
	}

	public void setError(Map<String, String> error) {
		this.error = error;
	}

}
