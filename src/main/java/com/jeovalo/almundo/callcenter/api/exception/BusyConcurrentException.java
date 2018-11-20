package com.jeovalo.almundo.callcenter.api.exception;

/**
 * Almundo CallCenter
 * for HTTP 400 errors
 */
public final class BusyConcurrentException extends RuntimeException {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2836544990729149914L;

	/**
	 * Constructor por defecto 
	 */
    public BusyConcurrentException() {
        super();
    }

    /**
     * 
     * @param message
     * @param cause
     */
    public BusyConcurrentException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * @param message
     */
    public BusyConcurrentException(final String message) {
        super(message);
    }

    /**
     * 
     * @param cause
     */
    public BusyConcurrentException(final Throwable cause) {
        super(cause);
    }
}