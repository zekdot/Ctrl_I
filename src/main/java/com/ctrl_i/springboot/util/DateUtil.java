package com.ctrl_i.springboot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期相关的工具类
 * Create by zekdot on 19-1-26.
 */
public class DateUtil {
    private static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 将日期转换成字符串
     *
     * @param date
     * @return
     */
    public static String date2Str(Date date) {
        return format1.format(date);
    }

    /**
     * 将日期时间转换成字符串
     *
     * @param datetime 日期时间
     * @return 字符串
     */
    public static String dateTime2Str(Date datetime) {
        return format2.format(datetime);
    }
    /**
     * 字符串转换日期
     *
     * @param str 日期字符串
     * @return
     * @throws ParseException
     */
    public static Date str2Date(String str) throws ParseException {
        return format1.parse(str);
    }

    /**
     * 获取两个Date相差秒数
     *
     * @param start
     * @param end
     * @return
     */
    public static int getIntervalSeconds(Date start, Date end) {
        return (int) ((end.getTime() - start.getTime()) / 1000);
    }

    /**
     * 获得两个Date的相差天数
     *
     * @param start 开始Date
     * @param end   结束Date
     * @return 天数
     */
    public static int getIntervalDays(Date start, Date end) {
        return getIntervalSeconds(start, end) / 3600 / 24;  //计算天数
    }

    public static void main(String args[]) {
//        Date start = null;
//        Date end = null;
//        try {
//            start = new java.sql.Date(DateUtil.str2Date("2019-01-26").getTime());
//            end = DateUtil.str2Date("2019-01-30");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println(date2Str(start));
//        System.out.println(date2Str(end));
//        System.out.println(getIntervalDays(start, end));
        System.out.println(dateTime2Str(new Date()));
    }
}
