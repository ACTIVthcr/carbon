package org.carbon.adventure.exception;

public class OutOfMapException extends Exception {

	private static final long serialVersionUID = 1L;

	public OutOfMapException() {
		super();
	}

	public OutOfMapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OutOfMapException(String message, Throwable cause) {
		super(message, cause);
	}

	public OutOfMapException(String message) {
		super(message);
	}

	public OutOfMapException(Throwable cause) {
		super(cause);
	}

}
