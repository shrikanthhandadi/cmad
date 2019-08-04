package com.cisco.iot.ccs.exception;

@SuppressWarnings("serial")
public class NotFoundException extends CcsException {

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
