/**
 * 
 */
package com.jabely.biz.user.dao;

import java.util.List;

import com.jabely.biz.user.dao.domain.UserWeixinInfoDO;
import com.jabely.biz.user.dao.domain.query.QueryUserWeixinInfoDO;

/**
 * @author fangmingxing
 *
 */
public interface IUserWeixinInfoDao {

	public UserWeixinInfoDO insertUserWeixinInfo(UserWeixinInfoDO userBasicInfo);

	public int updateUserWeixinInfo(UserWeixinInfoDO userBasicInfo);

	public UserWeixinInfoDO queryUserWeixinInfoByPrimaryKey(Long userId);
	
	public UserWeixinInfoDO queryUserWeixinInfo(QueryUserWeixinInfoDO query);

	public List<UserWeixinInfoDO> queryListForPage(QueryUserWeixinInfoDO query);

	public int countUserWeixinInfo(QueryUserWeixinInfoDO query);

	public List<UserWeixinInfoDO> queryAllUserWeixinInfo(QueryUserWeixinInfoDO query);

	public List<UserWeixinInfoDO> queryAllUserWeixinInfo(QueryUserWeixinInfoDO query, int timeout, int maxSizeAllow);
}
