/**
 * 
 */
package com.jabely.biz.user.biz;

import com.jabely.biz.user.dao.domain.UserWeixinInfoDO;

/**
 * @author fangmingxing
 *
 */
public interface IUserWriteManager {

	/**
	 * 保存用户微信信息
	 * 
	 * @param userWeixinInfo
	 * @return
	 */
	UserWeixinInfoDO saveUserWeixinInfo(UserWeixinInfoDO userWeixinInfo);
}
