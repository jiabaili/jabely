/**
 * 
 */
package com.jabely.framework.util;

/**
 * @author xiedan
 *
 */
public final class AssertUtils {

	public static void assertNull(Object target, String message) {
		if (target == null) {
			throw new IllegalArgumentException(StringUtils.isBlank(message) ? "参数不能为空" : message);
		}
	}
}
