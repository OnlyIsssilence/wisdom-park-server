package com.zhiliao.wpserver.utils;


import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Copyright 2019 OnlyIssilence, Inc. All rights reserved.
 *
 * @Author: MuYa
 * @Date: 2019/7/9
 * @Time: 0:06
 * @Desc:
 */
public class DateUtils {

    public static final String M_S = "MM月dd日";
    public static final String Y_M_S = "yyyy年MM月dd日";
    public static final String TIMESTAMP_Y_M_S = "yyyy-MM-dd";
    public static final String Y_M_S_h_m_s = "yyyy年MM月dd日 HH时mm分ss秒";

    public static final long DAY_MILLS = 24 * 60 * 60 * 1000;

    /**
     * 一年中的第几天
     *
     * @return
     */
    public static int getDayOfYear() {
        Date date = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);

        int i = ca.get(Calendar.DAY_OF_YEAR);

        return i;
    }

    /**
     * 一月中的第几天
     *
     * @return
     */
    public static int getDayOfMonth() {
        Date date = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int a = ca.get(Calendar.DAY_OF_MONTH);
        return a;
    }

    /**
     * 一天中的小时
     *
     * @return
     */
    public static int getDayOfHour() {
        Date date = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int a = ca.get(Calendar.HOUR_OF_DAY);
        return a;
    }

    /**
     * 获取年月日的秒，不包含时分秒 如2018-05-22 00:00:00
     *
     * @param time
     * @return
     */
    public static long getMillsWithymd(long time) {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(time);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTimeInMillis();
    }

    /**
     * 獲取間隔N天的時間
     *
     * @param days
     * @param isBefore true时间向前移 false时间往后 如今天13日，1 true为12
     * @return
     */
    public static long getIntervalMills(int days, boolean isBefore) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);

        if (isBefore) {
            return ca.getTimeInMillis() - DAY_MILLS;
        } else {
            return ca.getTimeInMillis() + DAY_MILLS;
        }
    }

    /**
     * 獲取間隔N天的時間
     *
     * @param days
     * @param isBefore
     * @return
     */
    public static Timestamp getIntervalTimestamp(int days, boolean isBefore) {
        return new Timestamp(getIntervalMills(days, isBefore));
    }

    /**
     * 获取今天是星期几 1是周天 2是周一...
     *
     * @return
     */
    public static int getDayOfWeek() {
        Calendar ca = Calendar.getInstance();
        return ca.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     * data Date类型的时间
     *
     * @param data
     * @param formatType
     * @return
     */
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * currentTime要转换的long类型的时间
     * formatType要转换的string类型的时间格式
     *
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static String longToString(long currentTime, String formatType) {
        // long类型转成Date类型
        Date date = new Date(currentTime);
        // date类型转成String
        String strTime = dateToString(date, formatType);
        return strTime;
    }

    /**
     * currentTime要转换的long类型的时间
     * formatType要转换的string类型的时间格式
     *
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static String longToString(String formatType) {
        // long类型转成Date类型
        Date date = new Date();
        // date类型转成String
        String strTime = dateToString(date, formatType);
        return strTime;
    }

    /**
     * strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
     * HH时mm分ss秒，
     * strTime的时间格式必须要与formatType的时间格式相同
     *
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String strTime, String formatType) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * currentTime要转换的long类型的时间
     * formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     *
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date longToDate(long currentTime, String formatType) throws ParseException {

        // 根据long类型的毫秒数生命一个date类型的时间
        Date dateOld = new Date(currentTime);
        // 把date类型的时间转换为string
        String sDateTime = dateToString(dateOld, formatType);
        // 把String类型转换为Date类型
        Date date = stringToDate(sDateTime, formatType);
        return date;
    }

    /**
     * strTime要转换的String类型的时间
     * formatType时间格式
     * strTime的时间格式和formatType的时间格式必须相同
     *
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static long stringToLong(String strTime, String formatType) throws ParseException {
        // String类型转成date类型
        Date date = stringToDate(strTime, formatType);
        if (date == null) {
            return 0;
        } else {
            // date类型转成long类型
            long currentTime = dateToLong(date);
            return currentTime;
        }
    }

    /**
     * date要转换的date类型的时间
     *
     * @param date
     * @return
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static Timestamp longToTimestamp(long time) {
        if (time < 0) {
            return null;
        }
        return new Timestamp(time);
    }

    public static String getDouPercent(double divisor, double dividend) {


        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();

        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(2);

        return nt.format(divisor / dividend);
    }

    public static String getIntPercent(int divisor, int dividend) {

        if (dividend == 0) {
            return "0.00%";
        }
        return getDouPercent((double) divisor, (double) dividend);
    }

}
