package com.dsmh.dashaoins.acl.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author TeamScorpio
 * @since 2022/4/16
 */
public class DateTimeUtils {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());

    public static String formatInstant(Instant instant) {
        return TIME_FORMATTER.format(instant);
    }


    public static String formatDate(LocalDate localDate) {
       return DATE_FORMATTER.format(localDate);
    }

    public static Instant instantOf(String date, DateTimeFormatter dateTimeFormatter) {
        return LocalDateTime.parse(date, dateTimeFormatter).atZone(ZoneId.systemDefault()).toInstant();
    }

    public static Instant instantOf(String dateStr) {
        return instantOf(dateStr, TIME_FORMATTER);
    }

    public static void main(String[] args) {
        Instant instant = instantOf("2022-05-04 00:00:00", TIME_FORMATTER);
        System.out.println(formatInstant(instant));

    }

}
