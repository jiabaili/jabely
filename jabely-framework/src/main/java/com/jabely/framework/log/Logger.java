/**
 * 
 */
package com.jabely.framework.log;

import org.slf4j.LoggerFactory;

/**
 * 日志打印工具类
 * 
 * @author mingxing.fmx
 * 
 */
public class Logger {

	private static org.slf4j.Logger biz = LoggerFactory.getLogger("biz");
	private static org.slf4j.Logger exp = LoggerFactory.getLogger("exp");

	/**
	 * 打印警告级别及以上的日志
	 * 
	 * @param bizKey
	 *            给某块业务定义的常量
	 * @param msg
	 *            需要打印的消息
	 * @param params
	 *            参数
	 */
	public static void biz(String bizKey, String msg, Object... params) {
		if (biz.isWarnEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append(bizKey);
			sb.append(",");
			sb.append(msg);
			if (params != null && params.length > 0) {
				for (Object p : params) {
					if (p != null) {
						sb.append(";");
						sb.append(String.valueOf(p));
					}
				}
			}
			biz.warn(sb.toString());
		}
	}

	public static void biz(String msg) {
		if (biz.isWarnEnabled()) {
			biz.warn(msg);
		}
	}

	/**
	 * 打印错误及以上级别的日志
	 * 
	 * @param bizKey
	 *            给某块业务定义的常量
	 * @param msg
	 *            需要打印的消息
	 * @param t
	 *            异常堆栈
	 * @param params
	 *            参数
	 */
	public static void exp(String bizKey, String msg, Throwable t, Object... params) {
		if (exp.isErrorEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append(bizKey);
			sb.append(",");
			sb.append(msg);
			if (params != null && params.length > 0) {
				for (Object p : params) {
					if (p != null) {
						sb.append(";");
						sb.append(String.valueOf(p));
					}
				}
			}
			exp.error(sb.toString(), t);
		}
	}

	/**
	 * 日志形式 <br>
	 * <b>时间 type 毫秒</b> <br>
	 * 2010-04-20 key 51 <br>
	 * web显示：module：key<br>
	 * module：submodule：key
	 * 
	 * @param key
	 * @param time
	 *            毫秒
	 */
	public static void stat(String bizKey, String className, String methodName, long time) {
	}
}
