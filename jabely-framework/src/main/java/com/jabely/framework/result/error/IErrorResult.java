/**
 * 
 */
package com.jabely.framework.result.error;

import java.util.Map;
import java.util.Set;

/**
 * @author fangmingxing
 *
 */
public interface IErrorResult {

    /**
     * 获取错误码集合
     * 
     * @return
     */
    public Set<String> getErrorCodes();

    /**
     * 获取需要替换错误消息中的占位符参数Map
     * 
     * @return
     */
    public Map<String, Map<String, Object>> getMessageParamsContext();
}
