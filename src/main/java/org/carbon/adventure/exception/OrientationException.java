package org.carbon.adventure.exception;

public class OrientationException extends Exception {

	private static final long serialVersionUID = 1L;

	public OrientationException() {
		super();
	}

	public OrientationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OrientationException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrientationException(String message) {
		super(message);
	}

	public OrientationException(Throwable cause) {
		super(cause);
	}

}
