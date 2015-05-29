package com.jabely.framework.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.Validate;

import com.jabely.framework.common.BaseConstants;

/**
 * 
 * @since 2013-5-14 上午11:30:43
 */
public class AttributeUtil implements BaseConstants {
    static final String R_SP = "#3A";
    static final String R_SSP = "#3B";

    /**
     * 通过Map转换成String
     * 
     * @param attrs
     * @return
     */
    public static final String toString(Map<String, String> attrs) {
        StringBuilder sb = new StringBuilder();
        if (null != attrs && !attrs.isEmpty()) {
            sb.append(SP);
            List<String> list = new ArrayList<String>(attrs.keySet());
            Collections.sort(list);
            for (String key : list) {
                String val = attrs.get(key);
                if (StringUtils.isNotEmpty(val)) {
                    sb.append(encode(key)).append(SSP).append(encode(val)).append(SP);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 通过字符串解析成attributes
     * 
     * @param str
     * @return
     */
    public static final Map<String, String> fromString(String str) {
        Map<String, String> attrs = new HashMap<String, String>();
        if (StringUtils.isNotBlank(str)) {
            String[] arr = str.split(SP);
            if (null != arr) {
                for (String kv : arr) {
                    if (StringUtils.isNotBlank(kv)) {
                        String[] ar = kv.split(SSP);
                        if (null != ar && ar.length == 2) {
                            String key = decode(ar[0]);
                            String val = decode(ar[1]);
                            if (StringUtils.isNotEmpty(val)) {
                                attrs.put(key, val);
                            }
                        }
                    }
                }
            }
        }
        return attrs;
    }

    /**
     * 通过字符串解析成attributes
     * 
     * @param str
     * @return
     */
    public static final Map<String, String> toMap(String str) {
        Map<String, String> attrs = new HashMap<String, String>();
        if (StringUtils.isNotBlank(str)) {
            String[] arr = StringUtils.split(str, SP);
            for (String kv : arr) {
                if (StringUtils.isNotBlank(kv)) {
                    String[] ar = kv.split(SSP);
                    if (ar.length == 2) {
                        String k = decode(ar[0]);
                        String v = decode(ar[1]);
                        if (StringUtils.isNotBlank(k) && StringUtils.isNotBlank(v)) {
                            attrs.put(k, v);
                        }
                    }
                }
            }
        }
        return attrs;
    }

    private static String encode(String val) {
        return StringUtils.replace(StringUtils.replace(val, SP, R_SP), SSP, R_SSP);
    }

    private static String decode(String val) {
        return StringUtils.replace(StringUtils.replace(val, R_SP, SP), R_SSP, SSP);
    }

    /**
     * 获取枚举值，如果不在枚举范围内，就取枚举数组的第一个为默认值。 如果传入的值为NULL，就原样返回NULL，防止数据库设置该字段时变成默认值。
     */
    public static <T> T getEnum(T value, T... enums) {
        Validate.notEmpty(enums, "enums不能为空");

        if (value == null) {
            return value;
        }

        for (T t : enums) {
            if (t.equals(value)) {
                return value;
            }
        }

        return enums[0];
    }

    public static String replacePlaceHolderWithMapValue(String msg, Map<String, Object> params) {
        Pattern placeHolderPattern = Pattern.compile("\\$\\{([\\w\\d]+)\\}");
        Matcher m = placeHolderPattern.matcher(msg);
        Map<String, String> relaceList = new HashMap<String, String>();
        while (m.find()) {
            String placeHolder = m.group(0);
            String key = m.group(1);
            String errorValue;
            Object value = params.get(key);
            if (value != null) {
                errorValue = value.toString();
            } else {
                errorValue = key;
            }
            relaceList.put(placeHolder, errorValue);
        }
        for (Entry<String, String> keyValue : relaceList.entrySet()) {
            msg = msg.replace(keyValue.getKey(), keyValue.getValue());
        }

        return msg;
    }

    /**
     * 将list用“:”连接后返回
     * 
     * @param list
     * @return
     */
    public static String getList2String(List<Long> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            for (Long item : list) {
                sb.append(item);
                sb.append(SSP);
            }
        }
        return sb.toString();
    }

    public static List<Long> getString2List(String speDes) {
        if (speDes == null) {
            return Collections.emptyList();
        }
        String[] speIdsstr = speDes.split(SSP);
        List<Long> speIds = new ArrayList<Long>();
        for (String speIdStr : speIdsstr) {
            speIds.add(Long.parseLong(speIdStr));
        }
        return speIds;
    }

}
