package com.cisco.iot.ccs.model;

public class ErrorResponse {

	private int code;

	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static Builder code(int code) {
		Builder builder = new Builder();
		builder.code(code);
		return builder;
	}

	public static class Builder {
		private ErrorResponse errorResponse;

		public Builder() {
			errorResponse = new ErrorResponse();
		}

		private Builder code(int code) {
			errorResponse.setCode(code);
			return this;
		}

		public ErrorResponse message(String message) {
			errorResponse.setMessage(message);
			return errorResponse;
		}
	}
}
