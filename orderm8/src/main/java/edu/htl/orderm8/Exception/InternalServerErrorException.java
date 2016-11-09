package edu.htl.orderm8.Exception;

public class InternalServerErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InternalServerErrorException(String message) {
		super(message);
	}

}
