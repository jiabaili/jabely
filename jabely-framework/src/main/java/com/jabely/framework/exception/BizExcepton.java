/**
 * 
 */
package com.jabely.framework.exception;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author fangmingxing
 *
 */
public class BizExcepton extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4800217582997051101L;
	private Map<String, String> errorMsgs = new HashMap<String, String>();
	
	public BizExcepton(String code, String message) {
		super();
		errorMsgs.put(code, message);
	}
	
    @Override
    public String toString() {
        return errorMsgs.toString();
    }

    public String getCode() {
        return errorMsgs.keySet().isEmpty() ? null : errorMsgs.keySet().iterator().next();
    }

    public String getRmk() {
        return errorMsgs.values().isEmpty() ? null : errorMsgs.values().iterator().next();
    }
    
    public Set<String> getCodes() {
        return errorMsgs == null ? null : errorMsgs.keySet();
    }

    public Collection<String> getErrMsgs() {
        return errorMsgs == null ? null : errorMsgs.values();
    }
    
    public Map<String, String> getErrorMsgs() {
        return errorMsgs;
    }

    public Map<String, String> convertToErrMsgs() {
        return errorMsgs;
    }
}
