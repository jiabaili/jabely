/**
 * 
 */
package com.jabely.biz.user.dao.domain.query;

import com.jabely.framework.common.QueryBaseDO;

/**
 * @author fangmingxing
 *
 */
public class QueryUserWeixinInfoDO extends QueryBaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7351289772432593918L;
	private Long userId;
	private String unionId;
	private String openId;
	private String nickName;
	private String sex;
	private String city;
	private String province;
	private String country;
	private String headImgUrl;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

}
