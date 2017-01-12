package com.sgaop.common.util;

import com.sgaop.basis.util.Logs;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/17 0017
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {
    protected static final Logger log = Logs.get();

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

    public static final String ENG_DATE_FROMAT = "EEE, d MMM yyyy HH:mm:ss z";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY = "yyyy";
    public static final String MM = "MM";
    public static final String DD = "dd";
    public static final String YYYY_MM_DD_HH_MM_ZH = "yyyy年MM月dd日  HH:mm";


    /**
     * 将timestamp转换成date
     *
     * @param tt
     * @return
     */
    public static Date timestampToDate(Timestamp tt) {
        return new Date(tt.getTime());
    }

    /**
     * 比较两个时间的大小
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean befor(Date a, Date b) {
        return a.before(b);
    }

    /**
     * @param date
     * @描述 —— 格式化日期对象
     */
    public static Date date2date(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String str = sdf.format(date);
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    /**
     * @param date
     * @描述 —— 时间对象转换成字符串
     */
    public static String date2string(Date date, String formatStr) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        strDate = sdf.format(date);
        return strDate;
    }

    /**
     * @param timestamp
     * @描述 —— sql时间对象转换成字符串
     */
    public static String timestamp2string(Timestamp timestamp, String formatStr) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        strDate = sdf.format(timestamp);
        return strDate;
    }

    /**
     * @param dateString
     * @param formatStr
     * @描述 —— 字符串转换成时间对象
     */
    public static Date string2date(String dateString, String formatStr) {
        Date formateDate = null;
        DateFormat format = new SimpleDateFormat(formatStr);
        try {
            formateDate = format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
        return formateDate;
    }


    /**
     * @param date
     * @描述 —— Date类型转换为Timestamp类型
     */
    public static Timestamp date2timestamp(Date date) {
        if (date == null)
            return null;
        return new Timestamp(date.getTime());
    }

    /**
     * 获取本月开始日期和今天结束日期
     *
     * @return
     */
    public static Date[] getNowMonth() {
        Date now = new Date();
        String date1 = getFullYear(now) + "-" + getMonth(now) + "-01 00:00:00";
        String date2 = getFullYear(now) + "-" + getMonth(now) + "-" + getDay(now) + " 23:59:59";
        Date[] dates = new Date[2];
        dates[0] = string2date(date1, YYYY_MM_DD_HH_MM_SS);
        dates[1] = string2date(date2, YYYY_MM_DD_HH_MM_SS);
        return dates;
    }

    /**
     * 获取近期num个月日期
     * num = -1;  是近三月
     *
     * @return
     */
    public static Date[] getNumMonth(int num) {
        Calendar clnow = Calendar.getInstance();
        Date now = new Date();
        String date1 = getFullYear(now) + "-" + (clnow.get(Calendar.MONTH) + num) + "-01 00:00:00";
        String date2 = getFullYear(now) + "-" + getMonth(now) + "-" + getDay(now) + " 23:59:59";
        Date[] dates = new Date[2];
        dates[0] = string2date(date1, YYYY_MM_DD_HH_MM_SS);
        dates[1] = string2date(date2, YYYY_MM_DD_HH_MM_SS);
        return dates;
    }


    /**
     * 获取上num个年度
     * num = -1;
     *
     * @return
     */
    public static Date[] getNumYear(int num) {
        Date now = new Date();
        String date1 = getFullYear(num) + "-01-01 00:00:00";
        String date2 = getFullYear(num) + "-12-31 23:59:59";
        Date[] dates = new Date[2];
        dates[0] = string2date(date1, YYYY_MM_DD_HH_MM_SS);
        dates[1] = string2date(date2, YYYY_MM_DD_HH_MM_SS);
        return dates;
    }

    /**
     * 获取上num个年度
     * num = -1;
     *
     * @return
     */
    public static Date getNumHour(int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - num);
        return calendar.getTime();
    }

    /**
     * 获取最近一周
     *
     * @return
     */
    public static Date[] getWeek(int num) {
        Date[] dates = new Date[2];
        dates[1] = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(dates[1]);
        cl.add(Calendar.WEEK_OF_YEAR, -num);
        dates[0] = cl.getTime();
        dates[0] = date2date(dates[0], YYYY_MM_DD_HH_MM_SS);
        dates[1] = date2date(dates[1], YYYY_MM_DD_HH_MM_SS);
        return dates;
    }

    /**
     * 获取最近几个月
     *
     * @param num
     * @return
     */
    public static Date[] getLastNumMonth(int num) {
        Date[] dates = new Date[2];
        dates[1] = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(dates[1]);
        cl.add(Calendar.MONTH, -num);//月
        dates[0] = cl.getTime();
        dates[0] = date2date(dates[0], YYYY_MM_DD_HH_MM_SS);
        dates[1] = date2date(dates[1], YYYY_MM_DD_HH_MM_SS);
        return dates;
    }


    /**
     * 年
     *
     * @param date
     * @return
     */
    public static String getFullYear(Date date) {
        return new SimpleDateFormat("yyyy").format(date);
    }

    /**
     * 取得当前年往前、往后的年
     *
     * @param num
     * @return
     */
    public static String getFullYear(int num) {
        Calendar now = Calendar.getInstance();
        return String.valueOf(now.get(Calendar.YEAR) + num);
    }


    /**
     * 月
     *
     * @param date
     * @return
     */
    public static String getMonth(Date date) {
        return new SimpleDateFormat("MM").format(date);
    }

    /**
     * 日
     *
     * @param date
     * @return
     */
    public static String getDay(Date date) {
        return new SimpleDateFormat("dd").format(date);
    }

    /**
     * 天
     *
     * @param date
     * @return
     */
    public static String getHour(Date date) {
        return new SimpleDateFormat("HH").format(date);
    }

    /**
     * 分
     *
     * @param date
     * @return
     */
    public static String getMinute(Date date) {
        return new SimpleDateFormat("mm").format(date);
    }

    /**
     * 秒
     *
     * @param date
     * @return
     */
    public static String getSecond(Date date) {
        return new SimpleDateFormat("ss").format(date);
    }

    /**
     * @param time
     * @描述 —— 指定时间距离当前时间的中文信息
     */
    public static String getLnow(long time) {
        Calendar cal = Calendar.getInstance();
        long timel = cal.getTimeInMillis() - time;
        if (timel / 1000 < 60) {
            return "1分钟以内";
        } else if (timel / 1000 / 60 < 60) {
            return timel / 1000 / 60 + "分钟前";
        } else if (timel / 1000 / 60 / 60 < 24) {
            return timel / 1000 / 60 / 60 + "小时前";
        } else {
            return timel / 1000 / 60 / 60 / 24 + "天前";
        }
    }


    /**
     * 根据身份证号码获取年龄
     *
     * @param IDCardNum 身份证
     * @return 年龄
     */
    public static int getAge(String IDCardNum) {
        int year, month, day, idLength = IDCardNum.length();
        Calendar cal1 = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        if (idLength == 18) {
            year = Integer.parseInt(IDCardNum.substring(6, 10));
            month = Integer.parseInt(IDCardNum.substring(10, 12));
            day = Integer.parseInt(IDCardNum.substring(12, 14));
        } else if (idLength == 15) {
            year = Integer.parseInt(IDCardNum.substring(6, 8)) + 1900;
            month = Integer.parseInt(IDCardNum.substring(8, 10));
            day = Integer.parseInt(IDCardNum.substring(10, 12));
        } else {
            System.out.println("This ID card number is invalid!");
            return -1;
        }
        cal1.set(year, month, day);
        return getYearDiff(today, cal1);
    }

    private static int getYearDiff(Calendar cal, Calendar cal1) {
        int m = (cal.get(cal.MONTH)) - (cal1.get(cal1.MONTH));
        int y = (cal.get(cal.YEAR)) - (cal1.get(cal1.YEAR));
        return (y * 12 + m) / 12;
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(specifiedDay);
        } catch (ParseException e) {
            log.debug(e); 
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(c.getTime());
        return dayBefore;
    }


    /**
     * @param start
     * @param end
     * @param format DateUtil.getDistanceTime(start,end,"{D}天{H}小时{M}分{S}秒{MS}毫秒")
     * @return
     */
    public static String getDistanceTime(long start, long end, String format) {
        long diff;
        if (start < end) {
            diff = end - start;
        } else {
            diff = start - end;
        }
        long day = diff / (24 * 60 * 60 * 1000);
        long hour = (diff / (60 * 60 * 1000) - day * 24);
        long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - sec * 1000);

        System.out.println(day + "天" + hour + "小时" + min + "分" + sec + "秒" + ms + "毫秒");
        format = format.replaceAll("\\{D\\}", String.valueOf(day));
        format = format.replaceAll("\\{H\\}", String.valueOf(hour));
        format = format.replaceAll("\\{M\\}", String.valueOf(min));
        format = format.replaceAll("\\{S\\}", String.valueOf(sec));
        format = format.replaceAll("\\{MS\\}", String.valueOf(ms));
        return format;
    }
}
