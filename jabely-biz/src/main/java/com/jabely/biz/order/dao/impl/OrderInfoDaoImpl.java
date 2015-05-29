/**
 * 
 */
package com.jabely.biz.order.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import com.jabely.biz.order.dao.IOrderInfoDao;
import com.jabely.biz.order.dao.domain.OrderInfoDO;
import com.jabely.biz.order.dao.domain.query.QueryOrderInfoDO;
import com.jabely.common.MybatisBaseDao;
import com.jabely.framework.sequence.Sequence;
import com.jabely.framework.util.CollectionUtils;
import com.jabely.framework.util.NumberUtils;

/**
 * @author fangmingxing
 *
 */
public class OrderInfoDaoImpl extends MybatisBaseDao implements IOrderInfoDao {

	private static final String NS = "com.jabely.biz.order.dao.IOrderInfoDao.";

	@Resource
	private Sequence orderSequence;

	@Override
	public OrderInfoDO insertOrderInfo(OrderInfoDO orderInfo) {
		if (NumberUtils.isNullOrZero(orderInfo.getOrderId())) {
			orderInfo.setOrderId(orderSequence.nextValue());
		}
		processBeforeInsert(orderInfo);
		sqlSession.insert(NS + "insertOrderInfo", orderInfo);
		return orderInfo;
	}

	@Override
	public int updateOrderInfo(OrderInfoDO orderInfo) {
		return sqlSession.update(NS + "updateOrderInfo", orderInfo);
	}

	@Override
	public OrderInfoDO queryOrderInfoByPrimaryKey(long orderId) {
		return sqlSession.selectOne(NS + "queryOrderInfoByPrimaryKey", orderId);
	}

	@Override
	public int countOrderInfo(QueryOrderInfoDO query) {
		return count(query, NS + "countOrderInfo");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderInfoDO> queryListForPage(QueryOrderInfoDO query) {
		return super.queryPageData(query, NS + "countOrderInfo", NS + "queryListForPage");
	}

	@Override
	public List<OrderInfoDO> queryAllOrderInfo(QueryOrderInfoDO query) {
		return queryAllOrderInfo(query, -1, -1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderInfoDO> queryAllOrderInfo(QueryOrderInfoDO query, int timeout, int maxSizeAllow) {
		return super.selectAllData(query, NS + "queryListForPage", timeout, maxSizeAllow);
	}

	@Override
	public OrderInfoDO queryOrderInfo(QueryOrderInfoDO query) {
		List<OrderInfoDO> list = queryAllOrderInfo(query);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
}