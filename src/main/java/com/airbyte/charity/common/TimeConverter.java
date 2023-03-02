package com.airbyte.charity.common;

import com.github.eloyzone.jalalicalendar.DateConverter;
import com.github.eloyzone.jalalicalendar.JalaliDate;
import com.github.eloyzone.jalalicalendar.JalaliDateFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeConverter {
    public static final String DEFAULT_PATTERN_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String UPDATED_PATTERN_FORMAT = "yyyy/MM/dd";

    public static String convert(Date date, String patternFormat) {
        if (date == null) return null;
        SimpleDateFormat formatter = null;

        if (date.getHours() == 0 && date.getMinutes() == 0 && date.getSeconds() == 0) {
            formatter = new SimpleDateFormat(UPDATED_PATTERN_FORMAT, Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getDefault());
        } else {
            formatter = new SimpleDateFormat(patternFormat, Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getDefault());
        }

        return formatter.format(date.getTime());
    }

    public static Date convertStringToInstant(String str, String patternFormat) {
        if (str == null || str.isEmpty()) return null;

        String[] strings = str.split(" ");

        if (strings.length == 1) {
            str = str + " 00:00:00";
        }

        SimpleDateFormat formatter = new SimpleDateFormat(patternFormat);
        Date date = null;
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException("can't parse this string to instant with patternFormat!!!");
        }
        return date;
    }

    public static String jalaliToGeorgian(String jalali) {
        String[] dateList = jalali.split("/");

        DateConverter dateConverter = new DateConverter();
        ZoneId defaultZoneId = ZoneId.systemDefault();

        LocalDate localdate = dateConverter.jalaliToGregorian(Integer.parseInt(dateList[0]), Integer.parseInt(dateList[1]), Integer.parseInt(dateList[2]));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(Date.from(localdate.atStartOfDay(defaultZoneId).toInstant()));
    }

    public static String georgianToJalali(String georgian) {
        String[] dateList = georgian.split("/");

        DateConverter dateConverter = new DateConverter();

        JalaliDate jalaliDate = dateConverter.gregorianToJalali(Integer.parseInt(dateList[0]), Integer.parseInt(dateList[1]), Integer.parseInt(dateList[2]));

        JalaliDateFormatter formatter = new JalaliDateFormatter("yyyy/mm/dd");
        return jalaliDate.format(formatter);
    }

    public static String georgianToJalaliWithHour(String georgian) {
        String[] date = georgian.split(" ");
        String[] dateList = date[0].split("/");

        DateConverter dateConverter = new DateConverter();

        JalaliDate jalaliDate = dateConverter.gregorianToJalali(Integer.parseInt(dateList[0]), Integer.parseInt(dateList[1]), Integer.parseInt(dateList[2]));

        JalaliDateFormatter formatter = new JalaliDateFormatter("yyyy/mm/dd");
        String response =  jalaliDate.format(formatter);
        response = response + " " + date[1];
        return response;
    }

}
