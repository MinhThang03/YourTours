package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.Persistence;
import com.hcmute.yourtours.enums.GenderEnum;
import com.hcmute.yourtours.enums.RoleEnum;
import com.hcmute.yourtours.enums.UserStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends Persistence {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birthday")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(name = "address")
    private String address;

    @Column(name = "role_access")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;
}
