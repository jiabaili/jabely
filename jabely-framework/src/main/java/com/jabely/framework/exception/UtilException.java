/**
 * 
 */
package com.jabely.framework.exception;

/**
 * 
 * @author mingxing.fmx
 *
 */
public class UtilException extends DefaultException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4216786948072739037L;

	public UtilException(String msg){
        super(msg);
    }
    
    public UtilException(String msg, Throwable t){
        super(msg, t);
    }
}
