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
@Table(name = "book_home_surcharge_detail")
public class BookingSurchargeDetail extends Persistence {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "surcharge_id", columnDefinition = "BINARY(16)")
    private UUID surchargeId;

    @ManyToOne
    @JoinColumn(
            name = "booking",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_association_booking_surcharge_booking"),
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "BINARY(16)"
    )
    private BookHomes bookHome;

    @Column(name = "booking", columnDefinition = "BINARY(16)")
    private UUID booking;

    @Column(name = "cost_of_surcharge")
    private Double costOfSurcharge;

}
