/**
 * 
 */
package com.jabely.biz.user.biz.impl;

import javax.annotation.Resource;

import com.jabely.biz.user.biz.IUserReadManager;
import com.jabely.biz.user.dao.IUserWeixinInfoDao;
import com.jabely.biz.user.dao.domain.UserInfoDO;
import com.jabely.biz.user.dao.domain.UserWeixinInfoDO;
import com.jabely.biz.user.dao.domain.query.QueryUserWeixinInfoDO;

/**
 * @author fangmingxing
 *
 */
public class UserReadManagerImpl implements IUserReadManager {

	@Resource
	private IUserWeixinInfoDao userWeixinInfoDao;

	@Override
	public UserWeixinInfoDO queryUserWeixinInfo(String openId) {
		QueryUserWeixinInfoDO query = new QueryUserWeixinInfoDO();
		query.setOpenId(openId);
		return userWeixinInfoDao.queryUserWeixinInfo(query);
	}

	@Override
	public UserInfoDO queryUserLoginInfo(Long userId) {
		return null;
	}

}
