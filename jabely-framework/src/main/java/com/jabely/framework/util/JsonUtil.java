/**
 * 
 */
package com.jabely.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.jabely.framework.common.KeyValue;

/**
 * @author mingxing.fmx
 *
 */
public class JsonUtil {

	public static String toJson(Object o) {
		return JSON.toJSONString(o);
	}

	public static <T> List<T> parseJsonToList(String jsonStr, Class<T> clazz) {
		return JSON.parseArray(jsonStr, clazz);
	}

	public static final <T> T parseObject(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}
	public static void main(String[] args) {
		Map<Long, String> map = new HashMap<Long, String>();
		map.put(1L, "test1");
		map.put(2L, "test2");
		map.put(3L, "test3");
		System.out.println(toJson(map));
		
		List<KeyValue> list = new ArrayList<KeyValue>();
		list.add(new KeyValue(1L,"test1"));
		list.add(new KeyValue(2L,"test2"));
		list.add(new KeyValue(3L,"test3"));
		System.out.println(toJson(list));
	}
}
