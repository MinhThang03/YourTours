package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.NameData;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "room_categories")
public class RoomCategories extends NameData {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2r")
    @Column(name = "room_category_id", columnDefinition = "varchar(36)")
    @Type(type = "uuid-char")
    private UUID roomCategoryId;

    @Column(name = "important")
    private Boolean important;

    @Column(name = "config_bed")
    private Boolean configBed;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;

}
