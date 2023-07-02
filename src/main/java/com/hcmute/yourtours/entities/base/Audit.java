package com.hcmute.yourtours.entities.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.yourtours.libs.util.TimeUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public abstract class Audit<U extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private U createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    @Builder.Default
    private LocalDateTime createdDate = getLocalDateTimeGMT7();

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    @JsonIgnore
    private U lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    @Builder.Default
    private LocalDateTime lastModifiedDate = getLocalDateTimeGMT7();


    public static LocalDateTime getLocalDateTimeGMT7() {
        LocalDateTime localDateTime = LocalDateTime.now();

        // Convert it to GMT+7 (Indochina Time)
        ZoneId gmtPlus7Zone = ZoneId.of("GMT+7");
        ZonedDateTime gmtPlus7DateTime = localDateTime.atZone(gmtPlus7Zone);

        // Format the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = gmtPlus7DateTime.format(formatter);
        return TimeUtil.toLocalDateTime(formattedDateTime);
    }
}
