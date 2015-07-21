package com.ashwin.evolve.programs.exceptions;

/**
 * This exception is thrown whenever an instruction encounters an error in its
 * execution. Note: programs that throw execution exceptions should have maximum
 * fitness.
 * 
 * @author ashwin
 * 
 */
public class ExecutionException extends Exception {

	private static final long serialVersionUID = 599232149410410208L;

	public ExecutionException() {
		super();
	}
	
	public ExecutionException(String message) {
		super(message);
	}
	
	public ExecutionException(Throwable cause) {
		super(cause);
	}
	
	public ExecutionException(String message, Throwable cause) {
		super(message, cause);
	}
}
