/**
 * 
 */
package com.jabely.biz.user;

import com.jabely.biz.user.dao.domain.UserWeixinInfoDO;
import com.jabely.framework.weixin.WeixinUser;

/**
 * @author fangmingxing
 *
 */
public final class UserUtil {

	public static UserWeixinInfoDO createUserWeixinInfo(WeixinUser weixinUser) {
		UserWeixinInfoDO userWeixinInfo = new UserWeixinInfoDO();
		userWeixinInfo.setOpenId(weixinUser.getOpenid());
		userWeixinInfo.setUnionId(weixinUser.getUnionid());
		userWeixinInfo.setCity(weixinUser.getCity());
		userWeixinInfo.setProvince(weixinUser.getProvince());
		userWeixinInfo.setCountry(weixinUser.getCountry());
		userWeixinInfo.setNickName(weixinUser.getNickname());
		userWeixinInfo.setHeadImgUrl(weixinUser.getHeadimgurl());
		userWeixinInfo.setSex(weixinUser.getSex());
		return userWeixinInfo;
	}
}
