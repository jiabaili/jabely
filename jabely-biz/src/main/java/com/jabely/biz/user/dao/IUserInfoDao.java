/**
 * 
 */
package com.jabely.biz.user.dao;

import java.util.List;

import com.jabely.biz.user.dao.domain.UserInfoDO;
import com.jabely.biz.user.dao.domain.query.QueryUserInfoDO;

/**
 * @author fangmingxing
 *
 */
public interface IUserInfoDao {

	/**
	 * 插入账号信息
	 * 
	 * @param UserLoginInfo
	 * @return
	 */
	public UserInfoDO insertUserInfo(UserInfoDO userLoginInfo);

	public int updateUserInfo(UserInfoDO userLoginInfo);

	public UserInfoDO queryUserInfoByPrimaryKey(Long userId);
	
	public UserInfoDO queryUserInfo(QueryUserInfoDO query);

	public List<UserInfoDO> queryListForPage(QueryUserInfoDO query);

	public int countUserInfo(QueryUserInfoDO query);

	public List<UserInfoDO> queryAllUserInfo(QueryUserInfoDO query);

	/**
	 * 按条件查询所有的记录
	 * 
	 * @param query
	 * @param timeout
	 * @param maxSizeAllow
	 * @return
	 */
	public List<UserInfoDO> queryAllUserInfo(QueryUserInfoDO query, int timeout, int maxSizeAllow);
}