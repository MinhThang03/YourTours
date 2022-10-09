package com.hcmute.yourtours.commands;

import com.hcmute.yourtours.commands.base.NameData;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "room_categories")
public class RoomCategoriesCommand extends NameData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;


    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "room_category_id", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID roomCategoryId;

    @Column(name = "important")
    private Boolean important;

    @Column(name = "config_bed")
    private Boolean configBed;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;


    @Override
    protected void preWrite() {
        super.preWrite();
        if (roomCategoryId == null) {
            roomCategoryId = UUID.randomUUID();
        }
    }
}
