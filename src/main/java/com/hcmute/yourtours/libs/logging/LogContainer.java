package com.hcmute.yourtours.libs.logging;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LogContainer {
    private final String userId;
    private final String phone;
    private final String fullName;
    private final String sessionId;
    private final String path;
    private final List<LogObject> logs = new ArrayList<>();

    public LogContainer(String sessionId, String userId, String phone, String fullName, String path) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.phone = phone;
        this.fullName = fullName;
        this.path = path;
    }
}
