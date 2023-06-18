package com.hcmute.yourtours.models.user.request;

import com.hcmute.yourtours.enums.UserStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class UpdateUserStatusRequest {

    @NotNull
    private UUID id;

    private UserStatusEnum status;
}
