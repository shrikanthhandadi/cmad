package com.cisco.iot.ccs.exception;

@SuppressWarnings("serial")
public class InvalidDataException extends CCSException {

	public InvalidDataException(String message) {
		super(message);
	}

	public InvalidDataException(Throwable cause) {
		super(cause);
	}

	public InvalidDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
