package com.db.extrato.exception;

public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GenericException(String errorMessage) {
        super(errorMessage);
    }
	
}
