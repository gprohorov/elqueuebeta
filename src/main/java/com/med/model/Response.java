package com.med.model;

public class Response {
	
	private boolean status;
	private String message;
	
	public Response() {}
	
	public Response(boolean status, String message) {
		this.status = status;
		this.message = message;
	}

	public Response(boolean status) {
		this.status = status;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Response{" +
				"status=" + status + '\n' +
				"message='" + message + '\'' +
				'}';
	}
}
