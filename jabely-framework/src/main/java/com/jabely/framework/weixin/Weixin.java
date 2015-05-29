/**
 * 
 */
package com.jabely.framework.weixin;

/**
 * @author mingxing.fmx
 * 
 */
public class Weixin {

	private String errcode;
	private String errmsg;

	public boolean isSuccess() {
		return (errcode == null || "".equals(errcode) || "0".equals(errcode));
	}
	
	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
