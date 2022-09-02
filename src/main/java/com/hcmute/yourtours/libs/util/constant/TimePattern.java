package com.hcmute.yourtours.libs.util.constant;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public enum TimePattern {
    dd_MM_yyyy("dd/MM/yyyy"),
    dd_MM_yyyy_HH_mm_ss("dd/MM/yyyy HH:mm:ss"),
    yyyy_MM_dd("yyyy-MM-dd");
    public final DateTimeFormatter formatter;
    private final String value;

    TimePattern(String value) {
        this.value = value;
        this.formatter = new DateTimeFormatterBuilder().appendPattern(value)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
    }

}
