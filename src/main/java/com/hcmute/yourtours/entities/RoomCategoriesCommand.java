package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.NameData;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
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

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<RoomsOfHomeCommand> roomsOfHome;


    @Override
    protected void preWrite() {
        super.preWrite();
        if (roomCategoryId == null) {
            roomCategoryId = UUID.randomUUID();
        }
    }
}
