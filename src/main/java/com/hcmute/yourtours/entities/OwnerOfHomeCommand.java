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
@Table(name = "owner_of_home")
public class OwnerOfHomeCommand extends Persistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "owner_of_home_id", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID ownerOfHomeId;

    @Column(name = "is_main_owner")
    private Boolean isMainOwner;

    @Column(name = "home_id", columnDefinition = "BINARY(16)")
    private UUID homeId;

    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "home_relation_id") // thông qua khóa ngoại address_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private HomesCommand home;

    @ManyToOne
    @JoinColumn(name = "user_relation_id") // thông qua khóa ngoại address_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserCommand user;

    @Override
    protected void preWrite() {
        super.preWrite();
        if (ownerOfHomeId == null) {
            ownerOfHomeId = UUID.randomUUID();
        }
    }
}

