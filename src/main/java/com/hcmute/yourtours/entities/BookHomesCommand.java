package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.Persistence;
import com.hcmute.yourtours.enums.BookRoomStatusEnum;
import com.hcmute.yourtours.enums.PaymentMethodMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "book_home")
public class BookHomesCommand extends Persistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "book_id", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID bookId;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethodMethodEnum paymentMethod;

    @Column(name = "visa_account")
    private String visaAccount;

    @Column(name = "home_id", columnDefinition = "BINARY(16)")
    private UUID homeId;

    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookRoomStatusEnum status;

    @Column(name = "total_cost")
    private Double totalCost;

    @Column(name = "percent")
    private Double percent;

    @Column(name = "cost_of_host")
    private Double costOfHost;

    @Column(name = "cost_of_admin")
    private Double costOfAdmin;

    @Column(name = "money_payed")
    private Double moneyPayed;


    @Override
    protected void preWrite() {
        super.preWrite();
        if (bookId == null) {
            bookId = UUID.randomUUID();
        }
    }
}
