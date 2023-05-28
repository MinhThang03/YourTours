package com.hcmute.yourtours.models.user.response;

import com.hcmute.yourtours.enums.LanguageEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingLanguageResponse {
    private LanguageEnum language;
}
