package com.jeovalo.almundo.callcenter.api.exception;

/**
 * Almundo CallCenter
 * for HTTP 400 errors
 */
public class NotSupportFormatException extends RuntimeException {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8866484021939618274L;

	/**
	 * Constructor por defecto 
	 */
    public NotSupportFormatException() {
        super();
    }

    /**
     * 
     * @param message
     * @param cause
     */
    public NotSupportFormatException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * @param message
     */
    public NotSupportFormatException(final String message) {
        super(message);
    }

    /**
     * 
     * @param cause
     */
    public NotSupportFormatException(final Throwable cause) {
        super(cause);
    }

}
