package com.hcmute.yourtours.models.booking.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateCommentRequest {
    private UUID bookId;
    private Double rates;
    private String comment;
}
