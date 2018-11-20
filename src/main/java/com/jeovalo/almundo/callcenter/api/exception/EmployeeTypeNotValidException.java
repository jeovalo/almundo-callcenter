package com.jeovalo.almundo.callcenter.api.exception;

/**
 * Almundo CallCenter
 * for HTTP 400 errors
 */
public class EmployeeTypeNotValidException extends RuntimeException {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8866484021939618274L;

	/**
	 * Constructor por defecto 
	 */
    public EmployeeTypeNotValidException() {
        super();
    }

    /**
     * 
     * @param message
     * @param cause
     */
    public EmployeeTypeNotValidException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * @param message
     */
    public EmployeeTypeNotValidException(final String message) {
        super(message);
    }

    /**
     * 
     * @param cause
     */
    public EmployeeTypeNotValidException(final Throwable cause) {
        super(cause);
    }

}
