package com.hcmute.yourtours.libs.util;

import com.hcmute.yourtours.libs.util.constant.TimePattern;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;

public class TimeUtil {
    public static String toStringDate(LocalDateTime date) {
        try {
            return toStringDate(date, TimePattern.dd_MM_yyyy_HH_mm_ss);
        } catch (DateTimeException exception) {
            return null;
        }
    }

    public static String toStringDate(LocalDateTime date, TimePattern pattern) {
        try {
            return pattern.formatter.format(date);
        } catch (DateTimeException exception) {
            return null;
        }
    }

    public static String toStringDate(Long date) {
        try {
            return toStringDate(date, TimePattern.dd_MM_yyyy_HH_mm_ss);
        } catch (DateTimeException exception) {
            return null;
        }
    }

    public static String toStringDate(Long date, TimePattern pattern) {
        try {
            return toLocalDateTime(date).format(pattern.formatter);
        } catch (DateTimeException exception) {
            return null;
        }
    }

    public static Long toTimeStamp(String dateString) throws DateTimeParseException {
        return toTimeStamp(dateString, TimePattern.dd_MM_yyyy_HH_mm_ss);
    }

    public static Long toTimeStamp(String dateString, TimePattern pattern) {
        return toTimeStamp(LocalDateTime.parse(dateString, pattern.formatter));
    }

    public static Long toTimeStamp(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return time.toInstant(ZoneOffset.UTC).toEpochMilli();
    }


    public static LocalDateTime toLocalDateTime(String stringDate) {
        return toLocalDateTime(stringDate, TimePattern.dd_MM_yyyy_HH_mm_ss);
    }

    public static LocalDateTime toLocalDateTime(String stringDate, TimePattern pattern) {
        try {
            return LocalDateTime.parse(stringDate, pattern.formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static LocalDateTime toLocalDateTime(String stringDate, TimePattern pattern, LocalDateTime defaultValue) {
        try {
            return toLocalDateTime(stringDate, pattern);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static LocalDateTime toLocalDateTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
    }


}
