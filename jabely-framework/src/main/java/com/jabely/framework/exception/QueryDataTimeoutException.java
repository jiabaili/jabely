/**
 * 
 */
package com.jabely.framework.exception;

/**
 * @author xiedan
 *
 */
public class QueryDataTimeoutException extends DefaultException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7451339170752319693L;

	public QueryDataTimeoutException() {
        super();
    }

    public QueryDataTimeoutException(String message) {
        super(message);
    }

    public QueryDataTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryDataTimeoutException(Throwable cause) {
        super(cause);
    }
}
