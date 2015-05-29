package com.jabely.framework.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.jabely.framework.result.error.AbstractErrorResult;

/**
 * 
 * @author mingxing.fmx
 * 
 * @param <T>
 */
public class DefaultResult<T> extends AbstractErrorResult implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 847251551561054485L;

	public DefaultResult() {
        super();
    }

    public DefaultResult(T module) {
        super();
        this.module = module;
    }

    private T module; // 实际的返回结果
    private int totalCount = 0;
    private Map<String, Object> otherResult;

    public void addResult(String bizKey, Object o) {
        if (otherResult == null) {
            otherResult = new HashMap<String, Object>();
        }
        otherResult.put(bizKey, o);
    }

    public T getModule() {
        return module;
    }

    public void setModule(T module) {
        this.module = module;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
