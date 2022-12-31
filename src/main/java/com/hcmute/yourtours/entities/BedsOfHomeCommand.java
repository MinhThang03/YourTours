package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.Persistence;
import lombok.*;
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
@Table(name = "beds_of_home")
public class BedsOfHomeCommand extends Persistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "bed_of_home_id", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID bedOfHomeId;


    @Column(name = "bed_category_id", columnDefinition = "BINARY(16)")
    private UUID categoryId;


    @Column(name = "room_of_home_id", columnDefinition = "BINARY(16)")
    private UUID roomOfHomeId;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "bed_category_relation_id") // thông qua khóa ngoại address_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private BedCategoriesCommand category;

    @ManyToOne
    @JoinColumn(name = "room_of_home_relation_id") // thông qua khóa ngoại address_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RoomsOfHomeCommand rooms;

    @Override
    protected void preWrite() {
        super.preWrite();
        if (bedOfHomeId == null) {
            bedOfHomeId = UUID.randomUUID();
        }
    }
}
