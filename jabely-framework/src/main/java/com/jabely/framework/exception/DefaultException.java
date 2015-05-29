/**
 * 
 */
package com.jabely.framework.exception;

/**
 * 
 * @author mingxing.fmx
 * 
 */
public class DefaultException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 14409842942162083L;

	public DefaultException() {
        super();
    }

    public DefaultException(String message) {
        super(message);
    }

    public DefaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public DefaultException(Throwable cause) {
        super(cause);
    }
}
