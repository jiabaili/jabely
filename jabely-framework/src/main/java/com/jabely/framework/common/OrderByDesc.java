/**
 * 
 */
package com.jabely.framework.common;

import java.io.Serializable;

/**
 * @author fangmingxing
 *
 */
public class OrderByDesc implements Serializable {

	public static final String SORT_TYPE_ASC = "asc";
	public static final String SORT_TYPE_DESC = "desc";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7016384228071994955L;
	private String sortColumn;
	private String sortType;

	public OrderByDesc(String sortColumn) {
		this(sortColumn, SORT_TYPE_ASC);
	}

	public OrderByDesc(String sortColumn, String sortType) {
		String newSortType = sortType.toLowerCase();
		if (!SORT_TYPE_ASC.equals(newSortType) && !SORT_TYPE_DESC.equals(newSortType)) {
			throw new IllegalArgumentException("sortType参数非法，只能为 " + SORT_TYPE_ASC + " 或 " + SORT_TYPE_DESC);
		}
		this.sortColumn = sortColumn;
		this.sortType = newSortType;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

}
