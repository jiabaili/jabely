/**
 * 
 */
package com.jabely.framework.weixin;

import com.jabely.framework.exception.BizExcepton;

/**
 * @author fangmingxing
 *
 */
public class WeixinBizExcepton extends BizExcepton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7742079430302386231L;

	/**
	 * 
	 */
	
	public WeixinBizExcepton(String code, String message) {
		super(code, message);
	}

}
