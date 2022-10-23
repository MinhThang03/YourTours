package com.hcmute.yourtours.aws.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class UploadMediaResponse {

    @NotNull
    @NotBlank
    private String previewUrl;

    @NotNull
    @NotBlank
    private LocalDateTime createDate;
}
