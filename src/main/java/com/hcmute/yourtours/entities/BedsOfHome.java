package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.Persistence;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "beds_of_home")
public class BedsOfHome extends Persistence {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;


    @ManyToOne
    @JoinColumn(
            name = "bed_category_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_category"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private BedCategories category;

    @Column(name = "bed_category_id", columnDefinition = "BINARY(16)")
    private UUID categoryId;


    @ManyToOne
    @JoinColumn(
            name = "room_of_home_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_room"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private RoomsOfHome room;

    @Column(name = "room_of_home_id", columnDefinition = "BINARY(16)")
    private UUID roomOfHomeId;

    @Column(name = "amount")
    private Integer amount;
}
