/**
 * 
 */
package com.jabely.web.common.exception;

import com.jabely.framework.exception.DefaultException;


/**
 * @author xiedan
 *
 */
public class PageException extends DefaultException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7487406882515705517L;

	public PageException() {
        super();
    }

    public PageException(String message) {
        super(message);
    }

    public PageException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageException(Throwable cause) {
        super(cause);
    }
}
