package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.NameData;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "rooms_of_home")
public class RoomsOfHome extends NameData {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "description")
    private String description;


    @ManyToOne
    @JoinColumn(
            name = "home_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_home"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private Homes home;

    @Column(name = "home_id", columnDefinition = "BINARY(16)")
    private UUID homeId;


    @ManyToOne
    @JoinColumn(
            name = "room_category_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_category"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private RoomCategories category;

    @Column(name = "room_category_id", columnDefinition = "BINARY(16)")
    private UUID categoryId;

    @Column(name = "order_flag")
    private Integer orderFlag;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    @Fetch(FetchMode.SUBSELECT)
    private List<BedsOfHome> beds;

}
