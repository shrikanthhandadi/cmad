package com.cisco.iot.ccs.exception;

@SuppressWarnings("serial")
public class CcsException extends RuntimeException {

	public CcsException(String message) {
		super(message);
	}

	public CcsException(Throwable cause) {
		super(cause);
	}

	public CcsException(String message, Throwable cause) {
		super(message, cause);
	}

}
