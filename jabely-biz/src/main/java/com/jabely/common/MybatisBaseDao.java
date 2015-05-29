/**
 * 
 */
package com.jabely.common;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

import com.jabely.framework.common.BaseDao;
import com.jabely.framework.common.QueryBaseDO;

/**
 * @author xiedan
 *
 */
public abstract class MybatisBaseDao extends BaseDao {

	@Resource
	protected SqlSession sqlSession;

	@Override
	public Integer count(QueryBaseDO query, String countSql) {
		return sqlSession.selectOne(countSql, query);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List selectList(QueryBaseDO query, String dataSql) {
		return sqlSession.selectList(dataSql, query);
	}

	@Override
	public int getSizePerSelect(){
		return 500;
	}
}
