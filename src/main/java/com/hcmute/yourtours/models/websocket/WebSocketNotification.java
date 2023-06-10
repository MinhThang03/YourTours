package com.hcmute.yourtours.models.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WebSocketNotification {
    private String title;
    private int number;
}
