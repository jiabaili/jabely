package com.jabely.biz.order.dao;

import java.util.List;

import com.jabely.biz.order.dao.domain.OrderInfoDO;
import com.jabely.biz.order.dao.domain.query.QueryOrderInfoDO;

public interface IOrderInfoDao {

	public OrderInfoDO insertOrderInfo(OrderInfoDO orderInfo);

	public int updateOrderInfo(OrderInfoDO orderInfo);

	public OrderInfoDO queryOrderInfoByPrimaryKey(long orderId);

	public int countOrderInfo(QueryOrderInfoDO query);

	public List<OrderInfoDO> queryListForPage(QueryOrderInfoDO query);
	
	public List<OrderInfoDO> queryAllOrderInfo(QueryOrderInfoDO query);

	public List<OrderInfoDO> queryAllOrderInfo(QueryOrderInfoDO query, int timeout, int maxSizeAllow);
	
	public OrderInfoDO queryOrderInfo(QueryOrderInfoDO query);
}
