package com.cisco.iot.ccs.exception;

@SuppressWarnings("serial")
public class ValidationException extends CcsException {

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}
