/**
 * 
 */
package com.jabely.framework.common;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.jabely.framework.exception.DefaultException;
import com.jabely.framework.util.AttributeUtil;
import com.jabely.framework.util.StringUtils;

/**
 * @author mingxing.fmx
 * 
 */
public abstract class BaseDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Date gmtCreate;
	protected Date gmtModified;
	protected Long version;
	protected Long options;
	protected Map<String, String> featureMap;// 原始属性对象

	protected String dbFeature;
	protected Long dbOptions;
	protected Long dbVersion;
	protected Date dbGmtCreate;// 记录创建时间
	protected Date dbGmtModified;// 记录最后修改时间
	protected Map<String, String> dbFeatureMap;// 原始属性对象
	
	protected static void newInsert(BaseDO objectDo) {
		objectDo.setOptions(0l);
		objectDo.setVersion(0l);
		if (objectDo.getFeatureMap() == null) {
			objectDo.setFeatureMap(new HashMap<String, String>());
		}
	}

	protected void copyProperties(BaseDO src, BaseDO target)
			throws DefaultException {
		try {
			PropertyUtils.copyProperties(target, src);
		} catch (IllegalAccessException e) {
			throw new DefaultException(e);
		} catch (InvocationTargetException e) {
			throw new DefaultException(e);
		} catch (NoSuchMethodException e) {
			throw new DefaultException(e);
		}
	}

	protected void initUpdateFields(BaseDO dbDO) {
//		if (getFeature() == null) {
//			this.feature = dbDO.getFeature();
//		}

		if (getFeatureMap() == null) {
			this.featureMap = dbDO.getFeatureMap();
		}

		if (getOptions() == null) {
			this.options = dbDO.getOptions();
		}

		if (getVersion() == null) {
			this.version = dbDO.getVersion();
		}

		if (getGmtCreate() == null) {
			this.gmtCreate = dbDO.getGmtCreate();
		}

		if (getGmtModified() == null) {
			this.gmtModified = dbDO.getGmtModified();
		}

		this.dbFeature = dbDO.getFeature();
		this.dbFeatureMap = dbDO.getFeatureMap();
		this.dbOptions = dbDO.getOptions();
		this.dbVersion = dbDO.getVersion();
		this.dbGmtCreate = dbDO.getGmtCreate();
		this.dbGmtModified = dbDO.getGmtModified();
	}

	/**
	 * 判断options是否发生变化，一般情况下给ibatis使用，禁止调用
	 * 
	 * @return
	 */
	public boolean isOptionsChange() {
		long src = (dbOptions == null ? 0 : dbOptions);
		long target = (getOptions() == null ? 0 : getOptions());
		return src != target;
	}

	/**
	 * 判断attribute是否发生变化，一般情况下给ibatis使用，禁止调用
	 * 
	 * @return
	 */
	public boolean isFeatureChange() {
		Map<String, String> src = (dbFeatureMap == null ? new HashMap<String, String>()
				: dbFeatureMap);
		Map<String, String> target = (getFeatureMap() == null ? new HashMap<String, String>()
				: getFeatureMap());

		if (src.size() != target.size()) {
			return true;
		}
		for (String key : src.keySet()) {
			Object value1 = src.get(key);
			Object value2 = target.get(key);
			if (value1 == null) {
				value1 = "";
			}
			if (value2 == null) {
				value2 = "";
			}
			if (!value1.equals(value2)) {
				return true;
			}
		}
		return false;
	}

	public String getFeature() {
		if (getFeatureMap() == null || getFeatureMap().isEmpty()) {
			return null;
		}
		return AttributeUtil.toString(getFeatureMap());
	}

	public void setFeature(String feature) {
//		this.feature = feature;
		setFeatureMap(AttributeUtil.fromString(feature));
	}

	/** 以下是 attribute 的实现,只能实现类修改attribute */
	public void setAttribute(String key, String value) {
		if (StringUtils.isEmpty(value)) {
			removeAttribute(key);
		} else {
			if(getFeatureMap()==null) {
				setFeatureMap(new HashMap<String,String>());
			}
			getFeatureMap().put(key, value);
		}
	}

	public void removeAttribute(String key) {
		if(getFeatureMap()==null) {
			return;
		}
		getFeatureMap().remove(key);
	}

	public String getAttribute(String name) {
		if (getFeatureMap() == null) {
			return null;
		}
		return getFeatureMap().get(name);
	}

	public boolean containsKey(String key) {
		if (getFeatureMap() == null) {
			return false;
		}
		return getFeatureMap().containsKey(key);
	}

	public void addOptions(Long oneOption) {
		if (oneOption == null || (oneOption & (oneOption - 1)) != 0) {
			throw new IllegalArgumentException("单个option必须为2的幂数,且不能为空"
					+ oneOption);
		}
		if (getOptions() == null) {
			setOptions(0l);
		}
		setOptions(getOptions() | oneOption);
	}

	public void removeOptions(Long oneOption) {
		if (oneOption == null || (oneOption & (oneOption - 1)) != 0) {
			throw new IllegalArgumentException("单个option必须为2的幂数,且不能为空"
					+ oneOption);
		}
		if (getOptions() == null) {
			return;
		}
		setOptions(getOptions() & (oneOption ^ Long.MAX_VALUE));
	}

	public boolean isOptions(Long oneOption) {
		if (oneOption == null || (oneOption & (oneOption - 1)) != 0) {
			throw new IllegalArgumentException("单个option必须为2的幂数,且不能为空"
					+ oneOption);
		}
		long tmp = getOptions() == null ? 0l : getOptions();
		return ((tmp & oneOption) == oneOption);
	}

	public Long getOptions() {
		return options;
	}

	public void setOptions(Long options) {
		this.options = options;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getDbFeature() {
		return dbFeature;
	}

	public void setDbFeature(String dbFeature) {
		this.dbFeature = dbFeature;
	}

	public Long getDbOptions() {
		return dbOptions;
	}

	public void setDbOptions(Long dbOptions) {
		this.dbOptions = dbOptions;
	}

	public Long getDbVersion() {
		return dbVersion;
	}

	public void setDbVersion(Long dbVersion) {
		this.dbVersion = dbVersion;
	}

	public Map<String, String> getDbFeatureMap() {
		return dbFeatureMap;
	}

	public void setDbFeatureMap(Map<String, String> dbFeatureMap) {
		this.dbFeatureMap = dbFeatureMap;
	}

	public Date getDbGmtCreate() {
		return dbGmtCreate;
	}

	public void setDbGmtCreate(Date dbGmtCreate) {
		this.dbGmtCreate = dbGmtCreate;
	}

	public Date getDbGmtModified() {
		return dbGmtModified;
	}

	public void setDbGmtModified(Date dbGmtModified) {
		this.dbGmtModified = dbGmtModified;
	}

	public Map<String, String> getFeatureMap() {
		return featureMap;
	}

	public void setFeatureMap(Map<String, String> featureMap) {
		this.featureMap = featureMap;
	}

	/**
	 * setter for column 创建时间
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/**
	 * getter for column 创建时间
	 */
	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	/**
	 * setter for column 修改时间
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * getter for column 修改时间
	 */
	public Date getGmtModified() {
		return this.gmtModified;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this);
	}

}