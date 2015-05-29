package com.jabely.framework.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.jabely.framework.util.PropertyUtils;

public abstract class QueryBaseDO implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_PAGE_SIZE = 30;
	public static final int DEFAULT_PAGE_NO = 1;
	public static final int MAX_PAGE_SIZE = 5000;

	/**
	 * 页面大小
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;
	/**
	 * 第几页
	 */
	private int pageNum = DEFAULT_PAGE_NO;

	/**
	 * 这个值一般由服务端设置
	 */
	private int total;

	/** 是否要分页 */
	private boolean needPagination = true;

	/** 是否要获取总页数，如果用户点分页查询第2页到第N页可以不用查询记录总数 */
	private boolean needPageTotal = true;

	/** 是否需要排序 */
	private List<OrderByDesc> orderBys;

	protected Long options;

	/**
	 * 验证查询对象设置的参数是否合理 主要是为了防止全表查询 默认是判断是否所有的
	 * 
	 * @return
	 */
	protected boolean validateParam() {
		if (options != null) {
			return true;
		}
		return !validatePropertyValueAllNull();
	}

	public void addOrderByDesc(String sortColumn, String sortType) {
		if (orderBys == null) {
			orderBys = new ArrayList<OrderByDesc>();
		}
		orderBys.add(new OrderByDesc(sortColumn, sortType));
	}

	public void addOrderByDesc(String sortColumn) {
		if (orderBys == null) {
			orderBys = new ArrayList<OrderByDesc>();
		}
		orderBys.add(new OrderByDesc(sortColumn));
	}

	public List<OrderByDesc> getOrderBys() {
		return orderBys;
	}

	public void setOrderBys(List<OrderByDesc> orderBys) {
		this.orderBys = orderBys;
	}

	protected boolean validatePropertyValueAllNull() {
		return isAllPropertyNull(getClass(), this);
	}

	protected boolean isAllPropertyNull(Class<?> clazz, Object o) {
		Field[] fileds = clazz.getDeclaredFields();
		if (fileds != null && fileds.length > 0) {
			for (Field f : fileds) {
				// 不是final，也不是static，是private的属性
				if (!Modifier.isFinal(f.getModifiers()) && !Modifier.isStatic(f.getModifiers()) && Modifier.isPrivate(f.getModifiers())) {
					try {
						Object value = PropertyUtils.getProperty(o, f.getName());
						// 只要有一个值被设置了，就可以
						if (value != null) {
							return false;
						}
					} catch (Exception e) {

					}
				}
			}
		}
		return true;
	}

	public Long getOptions() {
		return options;
	}

	public void setOptions(Long options) {
		this.options = options;
	}

	public void addOptions(Long oneOption) {
		if (oneOption == null || (oneOption & (oneOption - 1)) != 0) {
			throw new IllegalArgumentException("单个option必须为2的幂数,且不能为空" + oneOption);
		}
		if (getOptions() == null) {
			setOptions(0l);
		}
		setOptions(getOptions() | oneOption);
	}

	public void removeOptions(Long oneOption) {
		if (oneOption == null || (oneOption & (oneOption - 1)) != 0) {
			throw new IllegalArgumentException("单个option必须为2的幂数,且不能为空" + oneOption);
		}
		if (getOptions() == null) {
			return;
		}
		setOptions(getOptions() & (oneOption ^ Long.MAX_VALUE));
	}

	public int getTotalPage() {
		int page = this.getTotal() / this.getPageSize();
		int standTotalNum = this.getPageSize() * page;
		if (this.getTotal() > standTotalNum)
			return page + 1;
		return page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		if (pageSize <= 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		if (pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	/**
	 * 在导出，要查询所有的数据的时候使用，最大MAX_PAGE_SIZE<br>
	 * 此时不需要查询总数，不需要分页
	 */
	public void setMaxPageSize() {
		this.pageSize = MAX_PAGE_SIZE;
		setNeedPageTotal(false);
		setNeedPagination(false);
	}

	public int getPageNum() {
		// total已经被赋值,修正pagenum 的值
		if (Integer.MAX_VALUE != total && total > 0) {
			pageNum = Math.min(pageNum, (int) Math.ceil((double) total / pageSize));
		}

		if (pageNum <= 0) {
			pageNum = DEFAULT_PAGE_NO;
		}
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		if (pageNum <= 0) {
			pageNum = DEFAULT_PAGE_NO;
		}
		this.pageNum = pageNum;
	}

	/**
	 * mysql 分页专用
	 * 
	 * @return
	 */
	public int getStart() {
		return getPageSize() * (getPageNum() - 1);
	}

	/**
	 * Getter
	 * 
	 * @return needPagination
	 */
	public boolean isNeedPagination() {
		return needPagination;
	}

	/**
	 * Setter
	 * 
	 * @param needPagination
	 */
	public void setNeedPagination(boolean needPagination) {
		this.needPagination = needPagination;
	}

	/**
	 * Getter
	 * 
	 * @return needPageTotal
	 */
	public boolean isNeedPageTotal() {
		return needPageTotal;
	}

	/**
	 * Setter
	 * 
	 * @param needPageTotal
	 */
	public void setNeedPageTotal(boolean needPageTotal) {
		this.needPageTotal = needPageTotal;
	}
}
