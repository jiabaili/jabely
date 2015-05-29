/**
 * 
 */
package com.jabely.biz.user.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import com.jabely.biz.user.dao.IUserInfoDao;
import com.jabely.biz.user.dao.domain.UserInfoDO;
import com.jabely.biz.user.dao.domain.query.QueryUserInfoDO;
import com.jabely.common.MybatisBaseDao;
import com.jabely.framework.sequence.Sequence;
import com.jabely.framework.util.CollectionUtils;
import com.jabely.framework.util.NumberUtils;

/**
 * @author fangmingxing
 *
 */
public class UserInfoDaoImpl extends MybatisBaseDao implements IUserInfoDao {
	private static final String NS = "com.jabely.biz.user.dao.IUserInfoDao.";

	@Resource
	private Sequence userSequence;

	@Override
	public UserInfoDO insertUserInfo(UserInfoDO userLoginInfo) {
		if (NumberUtils.isNullOrZero(userLoginInfo.getUserId())) {
			userLoginInfo.setUserId(userSequence.nextValue());
		}
		if (userLoginInfo.getStatus() == null) {
			userLoginInfo.setStatus(1);
		}
		processBeforeInsert(userLoginInfo);
		sqlSession.insert(NS + "insertUserInfo", userLoginInfo);
		return userLoginInfo;
	}

	@Override
	public int updateUserInfo(UserInfoDO userLoginInfoDO) {
		if (NumberUtils.isNullOrZero(userLoginInfoDO.getUserId())) {
			throw new IllegalArgumentException("更新user_info，参数非法，user_id主键未传");
		}
		if (userLoginInfoDO.getVersion() == null) {
			throw new IllegalArgumentException("更新user_info，参数非法，version主键未传");
		}
		return sqlSession.update(NS + "updateUserInfo", userLoginInfoDO);
	}

	@Override
	public UserInfoDO queryUserInfoByPrimaryKey(Long userId) {
		if (NumberUtils.isNullOrZero(userId)) {
			throw new IllegalArgumentException("查询user_info，参数非法，user_id主键未传");
		}
		return (UserInfoDO) sqlSession.selectOne(NS + "queryUserInfoByPrimaryKey", userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfoDO> queryListForPage(QueryUserInfoDO query) {
		return super.queryPageData(query, NS + "countUserInfo", NS + "queryListForPage");
	}

	@Override
	public int countUserInfo(QueryUserInfoDO query) {
		return count(query, NS + "countUserInfo");
	}

	@Override
	public List<UserInfoDO> queryAllUserInfo(QueryUserInfoDO query) {
		return queryAllUserInfo(query, -1, -1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfoDO> queryAllUserInfo(QueryUserInfoDO query, int timeout, int maxSizeAllow) {
		return super.selectAllData(query, NS + "queryListForPage", timeout, maxSizeAllow);
	}

	@Override
	public UserInfoDO queryUserInfo(QueryUserInfoDO query) {
		List<UserInfoDO> list = queryAllUserInfo(query);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
}
