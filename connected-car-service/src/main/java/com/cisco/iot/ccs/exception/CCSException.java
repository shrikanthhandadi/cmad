package com.cisco.iot.ccs.exception;

@SuppressWarnings("serial")
public class CCSException extends RuntimeException {

	public CCSException(String message) {
		super(message);
	}

	public CCSException(Throwable cause) {
		super(cause);
	}

	public CCSException(String message, Throwable cause) {
		super(message, cause);
	}

}
