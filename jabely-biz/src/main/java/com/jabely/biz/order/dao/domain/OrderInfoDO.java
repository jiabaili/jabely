/**
 * 
 */
package com.jabely.biz.order.dao.domain;

import java.util.Date;

import com.jabely.framework.common.BaseDO;

/**
 * @author fangmingxing
 *
 */
public class OrderInfoDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3966678669201522526L;
	private Long orderId;
	private String outerOrderId;
	private String buyerMobilePhone;
	private Date createDate;
	private String referalCode;
	private Integer payMoney;
	private Integer rebateMoney;
	private Integer commission;
	private Integer logisticsFee;
	private Integer status;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOuterOrderId() {
		return outerOrderId;
	}

	public void setOuterOrderId(String outerOrderId) {
		this.outerOrderId = outerOrderId;
	}

	public String getBuyerMobilePhone() {
		return buyerMobilePhone;
	}

	public void setBuyerMobilePhone(String buyerMobilePhone) {
		this.buyerMobilePhone = buyerMobilePhone;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getReferalCode() {
		return referalCode;
	}

	public void setReferalCode(String referalCode) {
		this.referalCode = referalCode;
	}

	public Integer getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}

	public Integer getRebateMoney() {
		return rebateMoney;
	}

	public void setRebateMoney(Integer rebateMoney) {
		this.rebateMoney = rebateMoney;
	}

	public Integer getCommission() {
		return commission;
	}

	public void setCommission(Integer commission) {
		this.commission = commission;
	}

	public Integer getLogisticsFee() {
		return logisticsFee;
	}

	public void setLogisticsFee(Integer logisticsFee) {
		this.logisticsFee = logisticsFee;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
