package com.hcmute.yourtours.models.booking.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateCommentRequest {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID bookId;
    private Double rates;
    private String comment;
}
