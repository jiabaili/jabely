/**
 * 
 */
package com.jabely.biz.user.biz;

import com.jabely.biz.user.dao.domain.UserInfoDO;
import com.jabely.biz.user.dao.domain.UserWeixinInfoDO;

/**
 * @author fangmingxing
 *
 */
public interface IUserReadManager {

	/**
	 * 根据openId获取微信数据信息
	 * FIXME 还没有实现
	 * 
	 * @param openId
	 * @return
	 */
	UserWeixinInfoDO queryUserWeixinInfo(String openId);
	
	/**
	 * 根据用户id查询登陆信息
	 * 
	 * @param userId
	 * @return
	 */
	UserInfoDO queryUserInfo(Long userId);
}
