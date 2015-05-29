/**
 * 
 */
package com.jabely.biz.user.dao.domain;

import com.jabely.framework.common.BaseDO;

/**
 * @author fangmingxing
 *
 */
public class UserInfoDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1775842426198331312L;
	private Long userId; // 账号id
	private String mobilePhone; // 账号手机
	private String password; // 账号密码
	private String identifyCard; // 身份证
	private String alipayAccount; // 支付宝
	private String bankName; // 银行名称
	private String bankAccount; // 银行卡号
	private Integer status; // 账号状态

	public String getIdentifyCard() {
		return identifyCard;
	}

	public void setIdentifyCard(String identifyCard) {
		this.identifyCard = identifyCard;
	}

	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
