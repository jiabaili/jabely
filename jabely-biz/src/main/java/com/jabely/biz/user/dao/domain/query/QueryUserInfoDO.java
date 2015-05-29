/**
 * 
 */
package com.jabely.biz.user.dao.domain.query;

import com.jabely.framework.common.QueryBaseDO;

/**
 * @author fangmingxing
 *
 */
public class QueryUserInfoDO extends QueryBaseDO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4895506663207403040L;
	private String mobilePhone; // 账号手机
	private String password; // 账号密码
	private Integer status; // 账号状态

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
