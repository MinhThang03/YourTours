package com.hcmute.yourtours.models.booking.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GetPageEvaluateResponse {
    private String avatar;
    private String fullName;
    private Double rates;
    private String comment;
    private UUID userId;
    private UUID homeId;
    private UUID bookingId;
    private String lateModifiedDate;
}
