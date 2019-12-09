package com.springbootjpa.codeGod.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {
    public static String shortTimeFormat = "yyyyMMddHHmmss";
    public static String normalTimeFormat = "yyyy-MM-dd HH:mm:ss";
    public static String longTimeFormat = "yyyyMMddHHmmssSSS";
    public static String shortDateFormat = "yyyyMMdd";
    public static String normalDateFormat = "yyyy-MM-dd";

    public static String formatDate(String format, Date date) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    public static Date convertToDate(String str, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = sf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String convertToNormalDateString(String shortTimeString) {
        if (shortTimeString.length() > 8) {
            shortTimeString = shortTimeString.substring(0, 8);
        }

        Date date = convertToDate(shortTimeString, shortDateFormat);

        SimpleDateFormat sf = new SimpleDateFormat(normalDateFormat);
        return sf.format(date);
    }

    public static String convertToNormalTimeString(String shortTimeString) {
        Date date = convertToDate(shortTimeString, shortTimeFormat);

        SimpleDateFormat sf = new SimpleDateFormat(normalTimeFormat);
        return sf.format(date);
    }

    public static Date getTodayTimeZero() {
        String str = getNowDateShortString();
        return convertToDate(str, shortDateFormat);
    }

    public static Date getFirstDayOfMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, 1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
    }

    public static String getFirstDayOfMonthStr() {
        Date date = getFirstDayOfMonth();
        return formatDate(shortTimeFormat, date);
    }

    public static String getNowTimeShortString() {
        return formatDate(shortTimeFormat, new Date());
    }

    public static String getNowTimeLongString() {
        return formatDate(longTimeFormat, new Date());
    }

//    public static void main(String[] args) {
//        System.out.println(DateTimeUtils.getNowTimeLongString());;
//    }

    public static String getNowTimeNormalString() {
        return formatDate(normalTimeFormat, new Date());
    }

    public static String getNowDateShortString() {
        return formatDate(shortDateFormat, new Date());
    }

    public static String getNowDateNormalString() {
        return formatDate(normalDateFormat, new Date());
    }

    public static String convertAsShortTimeFormat(String origin) {
        return origin == null ? origin : origin.replaceAll("[-|:|\\s|年|月|日|时|分|秒|/]", "");
    }

    //月最后一天
    public static String getMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 给指定日期加一年
     * @param date
     * @param addyear
     * @return
     */
    public static Date getNextYear(String date,int addyear) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        try {
            parse = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse);
        cal.add(Calendar.YEAR, addyear);
        return cal.getTime();
    }

    public static String getMaturityTime(){
        return DateTimeUtils.formatDate(DateTimeUtils.normalDateFormat, DateTimeUtils.getNextYear(DateTimeUtils.getNowTimeNormalString(), 1));
    }
    public static String getMaturityTime(String dateTime,Integer addYear){
        return DateTimeUtils.formatDate(DateTimeUtils.normalDateFormat, DateTimeUtils.getNextYear(dateTime, addYear));
    }
    public static String getNowHzTime(Date now){
        SimpleDateFormat f=new SimpleDateFormat("yyyy年MM月dd日 kk点mm分");
        return f.format(now);
    }

    public static String getNowHzTimeNoss(String str){
        Date date = DateTimeUtils.convertToDate(str, "yyyy-MM-dd HH:mm");
        SimpleDateFormat f=new SimpleDateFormat("yyyy年MM月dd日 kk点mm分");
        return f.format(date);
    }
/*    public static void main(String[] args) {
        *//*Date now=new Date();
        Date date = DateTimeUtils.convertToDate("2019-09-02 08:00", "yyyy-MM-dd HH:mm");
        SimpleDateFormat f=new SimpleDateFormat("yyyy年MM月dd日 kk点mm分");
        System.out.println(f.format(date));*//*
    }*/
}
