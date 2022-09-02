package com.hcmute.yourtours.libs.util.notification.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import com.hcmute.yourtours.libs.util.notification.annotation.CustomRequired;
import com.hcmute.yourtours.libs.util.notification.interfaces.INotificationService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class NotificationModel {

    private UUID id;

    @CustomRequired
    private String resource;

    @CustomRequired
    private String workSpaceId;

    @CustomRequired
    private String session;

    @CustomRequired
    private String userId;

    @CustomRequired
    private Channel channel;

    @CustomRequired(justFor = Channel.PUSH)
    private TargetType targetType;

    @CustomRequired
    @Builder.Default
    private List<String> targets = new ArrayList<>();

    @CustomRequired
    private String templateCode;

    @CustomRequired
    private String data;

    private String contentType;

    @CustomRequired(justFor = Channel.EMAIL)
    private Boolean htmlContent;

    @Builder.Default
    private List<Attachment> attachments = new ArrayList<>();

    @CustomRequired
    private String languageCode;

    @JsonIgnore
    @Builder.Default
    private List<String> missingFields = new ArrayList<>();

    @JsonIgnore
    private INotificationService sender;

    public boolean checkModel() {
        if (channel == null) {
            missingFields.add("channel");
            return false;
        }
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                CustomRequired customRequired = field.getAnnotation(CustomRequired.class);
                if (customRequired != null) {
                    Channel[] channels = customRequired.justFor();
                    if (channels.length == 0) {
                        checkField(field);
                    } else {
                        if (Arrays.stream(channels).anyMatch(item -> item == channel)) {
                            checkField(field);
                        }
                    }
                }
            }
            return missingFields.isEmpty();

        } catch (Exception e) {
            return false;
        }
    }

    private void checkField(Field field) throws IllegalAccessException {
        Object object = field.get(this);
        if (object instanceof String) {
            if (!StringUtils.hasText((String) object)) {
                missingFields.add(field.getName());
            }
        } else if (object instanceof List) {
            if (((List<?>) object).size() == 0) {
                missingFields.add(field.getName());
            }
        } else {
            if (object == null) {
                missingFields.add(field.getName());
            }
        }
    }
}
