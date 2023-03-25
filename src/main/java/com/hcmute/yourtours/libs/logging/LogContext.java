package com.hcmute.yourtours.libs.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class LogContext {

    private static final Map<String, LogContainer> loggingMap = new ConcurrentHashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .registerModule(new JavaTimeModule().addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
    private LogContext() {

    }

    public static void push(LogType logType, Object data) {
        try {
            String sessionId = MDC.get("sessionId");
            String phone = MDC.get("phone");
            String userId = MDC.get("userId");
            String fullName = MDC.get("fullName");
            String path = MDC.get("path");
            if (sessionId != null) {
                if (!loggingMap.containsKey(sessionId)) {
                    loggingMap.put(sessionId, new LogContainer(sessionId, userId, phone, fullName, path));
                }
                loggingMap.get(sessionId).getLogs().add(new LogObject(logType, data));
            }
        } catch (Exception e) {
            log.warn("LogContext.push data {} error in {}: {}", data, e.getClass().getSimpleName(), e.getMessage());
        }
    }

    public static String pop(String sessionId) {
        if (loggingMap.containsKey(sessionId)) {
            LogContainer logContainer = loggingMap.get(sessionId);
            loggingMap.remove(sessionId);
            try {
                return mapper.writeValueAsString(logContainer);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
