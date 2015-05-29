/**
 * 
 */
package com.jabely.framework.weixin;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author mingxing.fmx
 * 
 */
public class WeixinUser extends Weixin {

	private Integer subscribe;
	private String openid;
	private String nickname;
	private String sex;
	private String language;
	private String city;
	private String province;
	private String country;
	private String headimgurl;
	private String subscribe_time;
	private String unionid;
	private List<String> privilege;

	@JSONField(serialize = false)
	public boolean isSubscribed() {
		return subscribe != null && subscribe == 1;
	}

	@JSONField(serialize = false)
	public boolean isUnSubscribe() {
		return subscribe != null && subscribe == 0;
	}

	@JSONField(serialize = false)
	public Boolean isFemale() {
		return sex == null ? null : "2".equals(sex);
	}

	@JSONField(serialize = false)
	public Boolean isMale() {
		return sex == null ? null : "1".equals(sex);
	}

	@JSONField(serialize = false)
	public Boolean isUnknownSex() {
		return sex == null ? null : "0".equals(sex);
	}

	@JSONField(serialize = false)
	public Date getSubscribeDate() {
		return new Date(Long.parseLong(this.subscribe_time) * 1000);
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public List<String> getPrivilege() {
		return privilege;
	}

	public void setPrivilege(List<String> privilege) {
		this.privilege = privilege;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(String subscribe_time) {
		this.subscribe_time = subscribe_time;
	}
}
