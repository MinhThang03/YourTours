package com.hcmute.yourtours.libs.logging;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LogObject {

    private final LogType logType;
    private final Object data;
    private final LocalDateTime time;

    public LogObject(LogType logType, Object data) {
        this.logType = logType;
        this.data = data;
        this.time = LocalDateTime.now();
    }

}
