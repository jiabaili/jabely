/**
 * 
 */
package com.jabely.framework.weixin;

import com.jabely.framework.exception.DefaultException;

/**
 * @author fmxgreat
 * 
 */
public class WeixinHttpException extends DefaultException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8435177820829928425L;

	/**
	 * 
	 */

	public WeixinHttpException(String msg, Throwable t) {
		super(msg, t);
	}

	public WeixinHttpException(Throwable t) {
		super(t);
	}

	public WeixinHttpException(String msg) {
		super(msg);
	}
}
