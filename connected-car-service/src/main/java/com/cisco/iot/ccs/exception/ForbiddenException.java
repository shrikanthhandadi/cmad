package com.cisco.iot.ccs.exception;

@SuppressWarnings("serial")
public class ForbiddenException extends CcsException {

	public ForbiddenException(String message) {
		super(message);
	}

	public ForbiddenException(Throwable cause) {
		super(cause);
	}

	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

}
