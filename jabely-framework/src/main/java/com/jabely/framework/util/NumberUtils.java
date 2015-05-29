/**
 * 
 */
package com.jabely.framework.util;

import java.math.BigDecimal;

/**
 * @author mingxing.fmx
 * 
 */
public final class NumberUtils extends org.apache.commons.lang.math.NumberUtils {

    /**金额为分的格式 */    
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";    
    
    public static Double fenToYuan(Long money, double defaultValue) {
        if (money == null)
            return defaultValue;
        StringBuffer sb = new StringBuffer();
        sb.append((money >= 0) ? "" : "-");
        sb.append(Math.abs(money) / 100);
        sb.append(".");
        sb.append((Math.abs(money) / 10) % 10);
        sb.append(Math.abs(money) % 10);
        return new Double(sb.toString());
    }

    public static Long yuanToFen(String f) {
        if (f == null || "".equals(f)) {
            return 0l;
        }
        return yuanToFen(toFloat(f));
    }

    public static Long yuanToFen(Float f) {
        if (f == null) {
            return 0l;
        }
        BigDecimal bd = new BigDecimal(f * 100);
        return bd.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
    }

    public static boolean isNullOrZero(Number num) {
        if (num == null || num.longValue() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 四舍五入 保留一位小数
     * 如果为null,取默认值
     * 
     * @param d
     * @param defaultStr
     * @return
     */
    public static double roundNumber(Number d, double defaultStr) {
        Double result = roundNumber(d, 1, BigDecimal.ROUND_HALF_UP);
        return result == null ? defaultStr : result;
    }

    /**
     * 四舍五入 保留一位小数
     * 
     * @param d
     * @return
     */
    public static Double roundNumber(Number d) {
        return roundNumber(d, 1, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 按照BigDecimal定义的规则取小数点后面n位数
     * 
     * @param d
     * @param dot
     * @param roundMode
     * @return
     */
    public static Double roundNumber(Number d, int dot, int roundMode) {
        if (d == null) {
            return null;
        }
        BigDecimal bd = null;
        if (d instanceof Double) {
            bd = new BigDecimal(d.doubleValue());
        } else if (d instanceof Float) {
            bd = new BigDecimal(d.floatValue());
        } else {
            bd = new BigDecimal(d.longValue());
        }
        return bd.setScale(dot, roundMode).doubleValue();
    }  
}
