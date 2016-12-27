package com.sgaop.common.util;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * 数字格式化工具类
 *
 * @author LiuJunGuang
 * @date 2013-1-26上午10:44:08
 */
public class Numbers {
    /**
     * 格式化数字
     *
     * @param number    待格式化数据
     * @param precision 小数位数
     * @return 格式结果
     */
    public static String format(double number, int precision) {
        if (number == 0) {
            String temp = "0.";
            for (int i = 0; i < precision; i++) {
                temp += "0";
            }
            return temp;
        }
        number = keepPrecision(number, precision);
        String base = "###,###.";
        for (int i = 0; i < precision; i++) {
            base += "#";
        }
        NumberFormat formatter = new DecimalFormat(base);
        String target = formatter.format(number);
        int ps = 0;
        if (target.split("\\.").length > 1) {
            ps = target.split("\\.")[1].length();
        } else if (precision == 0) {
            return target.substring(0, target.length() - 1);
        } else {
            target += ".";
        }
        if (ps != precision) {
            for (int i = 0; i < precision - ps; i++) {
                target += "0";
            }
        }
        return target;
    }

    /**
     * 格式化数字
     *
     * @param number    待格式化数据
     * @param precision 小数位数
     * @return 格式化结果
     */
    public static String format(float number, int precision) {
        if (number == 0) {
            String temp = "0.";
            for (int i = 0; i < precision; i++) {
                temp += "0";
            }
            return temp;
        }
        number = keepPrecision(number, precision);
        String base = "###,###.";
        for (int i = 0; i < precision; i++) {
            base += "#";
        }
        NumberFormat formatter = new DecimalFormat(base);
        String target = formatter.format(number);
        int ps = 0;
        if (target.split("\\.").length > 1) {
            ps = target.split("\\.")[1].length();
        } else if (precision == 0) {
            return target.substring(0, target.length() - 1);
        } else {
            target += ".";
        }
        if (ps != precision) {
            for (int i = 0; i < precision - ps; i++) {
                target += "0";
            }
        }
        return target;
    }

    /**
     * 格式化数字
     *
     * @param number    待格式化数据
     * @param precision 小数位数
     * @return 格式化结果
     */
    public static String format(Number number, int precision) {
        return format(number.toString(), precision);
    }

    /**
     * 格式化数字
     *
     * @param num       待格式化数据
     * @param precision 小数位数
     * @return 格式化结果
     */
    public static String format(String num, int precision) {
        return format(Double.parseDouble(num), precision);

    }

    /**
     * 会导致栈溢出 FIXED
     *
     * @param number    待格式化数据
     * @param precision 小数位数
     * @return 格式结果
     */
    @Deprecated
    public static String formatPrecission(String number, int precision) {
        return format(number, precision);
    }

    /**
     * 对double类型的数值保留指定位数的小数。<br>
     * 该方法舍入模式：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。<br>
     * <b>注意：</b>如果精度要求比较精确请使用 keepPrecision(String number, int precision)方法
     *
     * @param number    要保留小数的数字
     * @param precision 小数位数
     * @return double 如果数值较大，则使用科学计数法表示
     */
    public static double keepPrecision(double number, int precision) {
        BigDecimal bg = new BigDecimal(number);
        return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 对float类型的数值保留指定位数的小数。<br>
     * 该方法舍入模式：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。<br>
     * <b>注意：</b>如果精度要求比较精确请使用 keepPrecision(String number, int precision)方法
     *
     * @param number    要保留小数的数字
     * @param precision 小数位数
     * @return float 如果数值较大，则使用科学计数法表示
     */
    public static float keepPrecision(float number, int precision) {
        BigDecimal bg = new BigDecimal(number);
        return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 格式化为指定位小数的数字,返回未使用科学计数法表示的具有指定位数的字符串。<br>
     * 该方法舍入模式：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。<br>
     * 如果给定的数字没有小数，则转换之后将以0填充；例如：int 123 1 --> 123.0<br>
     * <b>注意：</b>如果精度要求比较精确请使用 keepPrecision(String number, int precision)方法
     *
     * @param number
     * @param precision 小数精确度总位数,如2表示两位小数
     * @return 返回数字格式化后的字符串表示形式(注意返回的字符串未使用科学计数法)
     */
    public static String keepPrecision(Number number, int precision) {
        return keepPrecision(String.valueOf(number), precision);
    }

    /**
     * 格式化为指定位小数的数字,返回未使用科学计数法表示的具有指定位数的字符串。
     * 该方法舍入模式：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。
     * <p>
     * <pre>
     * 	"3.1415926", 1			--> 3.1
     * 	"3.1415926", 3			--> 3.142
     * 	"3.1415926", 4			--> 3.1416
     * 	"3.1415926", 6			--> 3.141593
     * 	"1234567891234567.1415926", 3	--> 1234567891234567.142
     * </pre>
     *
     * @param number
     * @param precision 小数精确度总位数,如2表示两位小数
     * @return 返回数字格式化后的字符串表示形式(注意返回的字符串未使用科学计数法)
     */
    public static String keepPrecision(String number, int precision) {
        BigDecimal bg = new BigDecimal(number);
        return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    public static void main(String[] args) {
        System.err.println(keepPrecision(0.000000, 2));
    }
}