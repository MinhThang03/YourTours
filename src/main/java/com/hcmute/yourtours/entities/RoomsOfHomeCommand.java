package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.NameData;
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
@Table(name = "rooms_of_home")
public class RoomsOfHomeCommand extends NameData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "room_of_home_id", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID roomOfHomeId;

    @Column(name = "description")
    private String description;


    @Column(name = "home_id", columnDefinition = "BINARY(16)")
    private UUID homeId;

    @Column(name = "room_category_id", columnDefinition = "BINARY(16)")
    private UUID categoryId;

    @Column(name = "order_flag")
    private Integer orderFlag;

    @OneToMany(mappedBy = "rooms", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<BedsOfHomeCommand> bedsOfHomeCommands;

    @ManyToOne
    @JoinColumn(name = "category_relation_id") // thông qua khóa ngoại address_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RoomCategoriesCommand category;


    @Override
    protected void preWrite() {
        super.preWrite();
        if (roomOfHomeId == null) {
            roomOfHomeId = UUID.randomUUID();
        }
    }
}
