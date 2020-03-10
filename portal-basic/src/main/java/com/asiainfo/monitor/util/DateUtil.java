package com.asiainfo.monitor.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * @Author: LiuJH
 * @Date: 2020-02-27
 * @Description:
 */
public class DateUtil {

    private static DateTimeFormatter yyyymmdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static DateTimeFormatter yyyy = DateTimeFormatter.ofPattern("yyyy");


    public static String dateToYYYYMMDDString(Date date) {
        return getLocalDateTime(date).format(yyyymmdd);
    }

    public static String epochDayToYYYYMMDDString(int epochDay) {
        return dateToYYYYMMDDString(getDateByEpochDay(epochDay));
    }


    public static String dateToYYYYString(Date date) {
        return getLocalDateTime(date).format(yyyy);
    }


    public static LocalDateTime getLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        return instant.atZone(zoneId).toLocalDateTime();
    }

    public static Date getDateByEpochDay(int epochDay) {
        return Date.from(LocalDate.ofEpochDay(epochDay).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static int dateToEpochDay(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        return (int) instant.atZone(zoneId).toLocalDate().toEpochDay();
    }

    /**
     * 获取月初的epochDay
     *
     * @param epochDay 指定月份里的任意一天
     * @return
     */
    public static int getMonthStartEpochDay(int epochDay) {
        LocalDate date = LocalDate.ofEpochDay(epochDay);

        int currentDayOfMonth = date.getDayOfMonth();
        return epochDay - currentDayOfMonth + 1;
    }

    /**
     * 获取月底的epochDay
     *
     * @param epochDay 指定月份里的任意一天
     * @return
     */
    public static int getMonthEndEpochDay(int epochDay) {
        LocalDate date = LocalDate.ofEpochDay(epochDay);

        int monthDays = date.getMonth().maxLength();
        int currentDayOfMonth = date.getDayOfMonth();

        return epochDay + monthDays - currentDayOfMonth;
    }

}
