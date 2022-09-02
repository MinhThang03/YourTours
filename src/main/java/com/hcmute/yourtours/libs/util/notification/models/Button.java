package com.hcmute.yourtours.libs.util.notification.models;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Button implements Serializable {
    private String id;
    private String text;
    private String icon;
}
