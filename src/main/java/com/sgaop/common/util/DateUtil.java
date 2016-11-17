package com.sgaop.common.util;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/17 0017
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {

    public static String createAt(Date date) {
        if (date == null)
            return "未知";
        long now = System.currentTimeMillis() / 1000;
        long t = date.getTime() / 1000;
        long diff = now - t;
        if (diff < 5) {
            return "刚刚";
        }
        if (diff < 60) {
            return diff + "秒前";
        }
        if (diff < 60 * 60) {
            return (diff / 60) + "分钟前";
        }
        if (diff < 24 * 60 * 60) {
            return (diff / 60 / 60) + "小时";
        }
        return (diff / 24 / 60 / 60) + "天前";
    }
}
