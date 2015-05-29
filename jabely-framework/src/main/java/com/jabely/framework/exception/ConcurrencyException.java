/**
 * 
 */
package com.jabely.framework.exception;

/**
 * @author xiedan
 *
 */
public class ConcurrencyException extends DefaultException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7451339170752319693L;

	public ConcurrencyException() {
        super();
    }

    public ConcurrencyException(String message) {
        super(message);
    }

    public ConcurrencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConcurrencyException(Throwable cause) {
        super(cause);
    }
}
