package com.hcmute.yourtours.entities.base;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class NameData extends Persistence {

    @Lob
    private String description;

    private String name;
}
