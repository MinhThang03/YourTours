package com.hcmute.yourtours.models.user.request;

import com.hcmute.yourtours.enums.LanguageEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SettingLanguageRequest {
    @NotNull
    private LanguageEnum language;
}
