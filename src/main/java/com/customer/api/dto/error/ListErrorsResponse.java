package com.customer.api.dto.error;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ListErrorsResponse extends ErrorResponseBase {

	private List<Map<String, String>> error;

	public ListErrorsResponse(int status, LocalDateTime timestamp, List<Map<String, String>> error, String path) {
		super(status, timestamp, path);
		this.setError(error);
	}

	public List<Map<String, String>> getError() {
		return error;
	}

	public void setError(List<Map<String, String>> error) {
		this.error = error;
	}

}
