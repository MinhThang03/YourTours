package com.hcmute.yourtours.models.notification.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class TickViewRequest {
    @NotNull
    private UUID notificationId;
}
