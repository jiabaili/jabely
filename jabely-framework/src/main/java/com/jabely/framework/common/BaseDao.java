/**
 * 
 */
package com.jabely.framework.common;

import java.util.ArrayList;
import java.util.List;

import com.jabely.framework.exception.QueryDataTimeoutException;
import com.jabely.framework.util.CollectionUtils;
import com.jabely.framework.util.NumberUtils;

/**
 * @author mingxing.fmx
 * 
 */
public abstract class BaseDao {

	protected void processBeforeInsert(BaseDO base) {
		if (base.getVersion() == null) {
			base.setVersion(0L);
		}
		if (base.getOptions() == null) {
			base.setOptions(0L);
		}
	}
	
	public abstract int getSizePerSelect();
	
	/**
	 * 按条件查询所有的记录
	 * 
	 * @param query
	 *            业务查询对象
	 * @param timeout
	 *            超时毫秒数，-1，不超时
	 * @param maxSizeAllow
	 *            返回最大的，-1，无限制
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List selectAllData(QueryBaseDO query, String dataSql, int timeout, int maxSizeAllow) {
		if (query == null) {
			throw new IllegalArgumentException("业务查询对象不能为空");
		}
		if (!query.validateParam()) {
			throw new IllegalArgumentException("业务查询对象条件设置不合理，可能会导致全表查询，查出结果集巨大而导致内存溢出");
		}
		List result = new ArrayList();
		List list = null;
		int pageNum = 1;
		// 每次取多少条记录
		int pageSize = getSizePerSelect();
//		int lastPageSize = maxSizeAllow > pageSize ? maxSizeAllow % pageSize : -1;
		long startTime = System.currentTimeMillis();
		query.setNeedPagination(true);// 分页去取
		query.setPageSize(pageSize);
		do {
			query.setPageNum(pageNum);
			list = selectList(query, dataSql);
			pageNum++;
			if (CollectionUtils.isNotEmpty(list)) {
				result.addAll(list);
			} else {
				break;
			}

			long endTime = System.currentTimeMillis();
			// 超时时间设置了，校验下有没有超时
			if (timeout > 0 && endTime - startTime > timeout) {
				throw new QueryDataTimeoutException(query.getClass().getName());
			}

			// 最大允许返回的结果数量如果有限制的话，判断一下
			if (maxSizeAllow > 0 && result.size() >= maxSizeAllow) {
				break;
			}
		} while (list != null && !list.isEmpty() && list.size() == pageSize);
		return result;
	}

	@SuppressWarnings("rawtypes")
	protected List queryPageData(QueryBaseDO query, String countSql, String dataSql) {
		List result = null;
		if (query.isNeedPageTotal()) {
			// totalcount
			Integer count = count(query, countSql);
			if (NumberUtils.isNullOrZero(count)) {
				query.setTotal(0);
			} else {
				query.setTotal(count);
				result = selectList(query, dataSql);
			}
		} else {
			result = selectList(query, dataSql);
		}
		return result;
	}

	public abstract Integer count(QueryBaseDO query, String countSql);

	@SuppressWarnings("rawtypes")
	public abstract List selectList(QueryBaseDO query, String dataSql);
}
