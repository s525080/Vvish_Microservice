package com.appdev.vvish.exception;

public class VVishException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Default constructor.
	 */
	public VVishException() {
		super();
	}
	
	/**
	 * Constructor that allows a specific error message to be specified.
	 *
	 * @param message
	 *            the detail message.
	 */
	public VVishException(String message) {
		super(message);
	}

	/**
	 * Constructor that allows a specific error message to be specified.
	 *
	 * @param message
	 *            the detail message.
	 */
	public VVishException(String message,Exception e) {
		super(message,e);
	}

}
