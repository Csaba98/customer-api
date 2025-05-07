package com.customer.app.dto;

import java.time.LocalDateTime;

class ErrorResponseBase {

	private LocalDateTime timestamp;
	private int status;
	private String path;

	ErrorResponseBase(int status, LocalDateTime timestamp, String path) {
		this.status = status;
		this.timestamp = timestamp;
		this.path = path;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
