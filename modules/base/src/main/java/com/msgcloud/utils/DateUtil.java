package com.msgcloud.utils;


import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 */
public class DateUtil {
    // 默认日期格式
    public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";
    public static final String DATE_DEFAULT_FORMAT_CN = "yyyy年MM月dd日";
    // 默认时间格式
    public static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";
    public static final String TIME_DEFAULT_FORMAT_CN = "HH时mm分ss秒";

    public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_DEFAULT_FORMAT_CN = "yyyy年MM月dd日 HH时mm分ss秒";

    public static final String DATE_COMBINE_FORMAT = "yyyyMMdd";
    public static final String TIME_COMBINE_FORMAT = "HHmmss";
    public static final String DATETIME_COMBINE_FORMAT = "yyyyMMddHHmmss";

    // 日期格式化
    private static DateFormat dateFormat = null;
    private static DateFormat dateFormatCN = null;

    // 时间格式化
    private static DateFormat timeFormat = null;
    private static DateFormat timeFormatCN = null;

    private static DateFormat dateTimeFormat = null;
    private static DateFormat dateTimeFormatCN = null;

    private static DateFormat dateCombineFormat = null;
    private static DateFormat timeCombineFormat = null;
    private static DateFormat dateTimeCombineFormat = null;

    private static Calendar gregorianCalendar = null;

    static {
        dateFormat = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        dateFormatCN = new SimpleDateFormat(DATE_DEFAULT_FORMAT_CN);
        timeFormat = new SimpleDateFormat(TIME_DEFAULT_FORMAT);
        timeFormatCN = new SimpleDateFormat(TIME_DEFAULT_FORMAT_CN);
        dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
        dateTimeFormatCN = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT_CN);
        dateCombineFormat = new SimpleDateFormat(DATE_COMBINE_FORMAT);
        timeCombineFormat = new SimpleDateFormat(TIME_COMBINE_FORMAT);
        dateTimeCombineFormat = new SimpleDateFormat(DATETIME_COMBINE_FORMAT);
        gregorianCalendar = new GregorianCalendar();
    }

    /**
     * 日期格式化yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Date formatDate(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期格式化yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getDateFormat(Date date) {
        if (date == null) return "";
        return dateFormat.format(date);
    }

    public static String getDateFormatCN(Date date) {
        if (date == null) return "";
        return dateFormatCN.format(date);
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return HH:mm:ss
     */
    public static String getTimeFormat(Date date) {
        return timeFormat.format(date);
    }

    public static String getTimeFormatCN(Date date) {
        return timeFormatCN.format(date);
    }

    /**
     * 日期格式化yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getDateTimeFormat(Date date) {
        if (date == null) return "";
        return dateTimeFormat.format(date);
    }

    public static String getDateTimeFormatCN(Date date) {
        if (date == null) return "";
        return dateTimeFormatCN.format(date);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @return
     */
    public static String getDateFormat(Date date, String formatStr) {
        if (StringUtils.isNotBlank(formatStr)) {
            return new SimpleDateFormat(formatStr).format(date);
        }
        return null;
    }

    /**
     * 日期格式化
     *
     * @param date
     * @return
     */
    public static Date getDateFormat(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return
     */
    public static Date getDateTimeFormat(String date) {
        try {
            return dateTimeFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateTimeCombineFormat(String date) {
        try {
            return dateTimeCombineFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前日期(yyyy-MM-dd)
     *
     * @return
     */
    public static Date getNowDate() {
        return DateUtil.getDateFormat(dateFormat.format(new Date()));
    }

    public static String getNowDateString() {
        return dateFormat.format(new Date());
    }

    /**
     * 获取当前日期(yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */
    public static Date getNowDateTime() {
        return DateUtil.getDateTimeFormat(dateTimeFormat.format(new Date()));
    }

    public static String getNowDateTimeString() {
        return dateTimeFormat.format(new Date());
    }

    /**
     * 获取当前日期(yyyyMMddHHmmss)
     *
     * @return
     */
    public static Date getNowDateTimeCombine() {
        return DateUtil.getDateTimeCombineFormat(dateTimeCombineFormat.format(new Date()));
    }

    public static String getNowDateCombineString() {
        return dateCombineFormat.format(new Date());
    }

    public static String getNowTimeCombineString() {
        return timeCombineFormat.format(new Date());
    }

    public static String getNowDateTimeCombineString() {
        return dateTimeCombineFormat.format(new Date());
    }

    /**
     * 获取当前日期星期一日期
     *
     * @return date
     */
    public static Date getFirstDayOfWeek() {
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek()); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前日期星期日日期
     *
     * @return date
     */
    public static Date getLastDayOfWeek() {
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek() + 6); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期星期一日期
     *
     * @return date
     */
    public static Date getFirstDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek()); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期星期一日期
     *
     * @return date
     */
    public static Date getLastDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek() + 6); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前月的第一天
     *
     * @return date
     */
    public static Date getFirstDayOfMonth() {
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前月的最后一天
     *
     * @return
     */
    public static Date getLastDayOfMonth() {
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        gregorianCalendar.add(Calendar.MONTH, 1);
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        gregorianCalendar.add(Calendar.MONTH, 1);
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期前一天
     *
     * @param date
     * @return
     */
    public static Date getDayBefore(Date date) {
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day - 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期后一天
     *
     * @param date
     * @return
     */
    public static Date getDayAfter(Date date) {
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day + 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static String getNowYear() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.YEAR) + "";
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static String getNowMonth() {
        Calendar d = Calendar.getInstance();
        int month = d.get(Calendar.MONTH) + 1;
        if (month < 10) {
            return "0" + month;
        }
        return month + "";
    }

    /**
     * 获取当前是某个月的第几天
     *
     * @return
     */
    public static String getNowDay() {
        Calendar d = Calendar.getInstance();
        int day = d.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            return "0" + day;
        }
        return day + "";
    }

    /**
     * 获取当前是几点
     *
     * @return
     */
    public static String getNowHour() {
        Calendar d = Calendar.getInstance();
        int hour = d.get(Calendar.HOUR_OF_DAY);
        if (hour < 10) {
            return "0" + hour;
        }
        return hour + "";
    }

    public static int getNowWeekDay() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当月天数
     *
     * @return
     */
    public static int getNowMonthDay() {
        Calendar d = Calendar.getInstance();
        return d.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取时间段的每一天
     *
     * @return 日期列表
     */
    public static List<Date> getEveryDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        // 格式化日期(yy-MM-dd)
        startDate = DateUtil.getDateFormat(DateUtil.getDateFormat(startDate));
        endDate = DateUtil.getDateFormat(DateUtil.getDateFormat(endDate));
        List<Date> dates = new ArrayList<Date>();
        gregorianCalendar.setTime(startDate);
        dates.add(gregorianCalendar.getTime());
        while (gregorianCalendar.getTime().compareTo(endDate) < 0) {
            // 加1天
            gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(gregorianCalendar.getTime());
        }
        return dates;
    }

    /**
     * 获取提前多少个月
     *
     * @param monty
     * @return
     */
    public static Date getFirstMonth(int monty) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -monty);
        return c.getTime();
    }

    /**
     * 加/减N年
     */
    public static Date AddYear(Date date, int year) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 加/减N月
     */
    public static Date AddMonth(Date date, int month) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 加/减N日
     */
    public static Date AddDay(Date date, int day) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }

    /**
     * 加/减N小时
     */
    public static Date AddHour(Date date, int hour) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    /**
     * 加/减N分钟
     */
    public static Date AddMinute(Date date, int minute) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 加/减N秒
     */
    public static Date AddSecond(Date date, int second) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }


    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date calendarToDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();//日历类的实例化
        calendar.set(year, month - 1, day);//设置日历时间，月份必须减一
        Date date = calendar.getTime(); // 从一个 Calendar 对象中获取 Date 对象
        return date;
    }

    // 获取当前时间戳 秒
    public static int getTimestamp() {
        return Integer.parseInt(String.valueOf(new Date().getTime() / 1000));
    }
}