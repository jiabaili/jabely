/**
 * 
 */
package com.jabely.biz.user.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import com.jabely.biz.user.dao.IUserWeixinInfoDao;
import com.jabely.biz.user.dao.domain.UserWeixinInfoDO;
import com.jabely.biz.user.dao.domain.query.QueryUserWeixinInfoDO;
import com.jabely.common.MybatisBaseDao;
import com.jabely.framework.sequence.Sequence;
import com.jabely.framework.util.CollectionUtils;

/**
 * @author fangmingxing
 *
 */
public class UserWeixinInfoDaoImpl extends MybatisBaseDao implements IUserWeixinInfoDao {
	private static final String NS = "com.jabely.biz.user.dao.IUserWeixinInfoDao.";
	@Resource
	private Sequence userSequence;

	@Override
	public UserWeixinInfoDO insertUserWeixinInfo(UserWeixinInfoDO userWeixinInfo) {
		if (userWeixinInfo.getUserId() == null || userWeixinInfo.getUserId() <= 0) {
			userWeixinInfo.setUserId(userSequence.nextValue());
		}
		processBeforeInsert(userWeixinInfo);
		sqlSession.insert(NS + "insertUserWeixinInfo", userWeixinInfo);
		return userWeixinInfo;
	}

	@Override
	public int updateUserWeixinInfo(UserWeixinInfoDO userWeixinInfo) {
		return sqlSession.update(NS + "updateUserWeixinInfo", userWeixinInfo);
	}

	@Override
	public UserWeixinInfoDO queryUserWeixinInfoByPrimaryKey(Long userId) {
		return sqlSession.selectOne(NS + "queryUserWeixinInfoByPrimaryKey", userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserWeixinInfoDO> queryListForPage(QueryUserWeixinInfoDO query) {
		return super.queryPageData(query, NS + "countUserWeixinInfo", NS + "queryListForPage");
	}

	@Override
	public int countUserWeixinInfo(QueryUserWeixinInfoDO query) {
		return count(query, NS + "countUserWeixinInfo");
	}

	@Override
	public List<UserWeixinInfoDO> queryAllUserWeixinInfo(QueryUserWeixinInfoDO query) {
		return queryAllUserWeixinInfo(query, -1, -1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserWeixinInfoDO> queryAllUserWeixinInfo(QueryUserWeixinInfoDO query, int timeout, int maxSizeAllow) {
		return super.selectAllData(query, NS + "queryListForPage", timeout, maxSizeAllow);
	}

	@Override
	public UserWeixinInfoDO queryUserWeixinInfo(QueryUserWeixinInfoDO query) {
		List<UserWeixinInfoDO> list = queryAllUserWeixinInfo(query);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
}
