package com.hcmute.yourtours.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.yourtours.commands.base.Persistence;
import com.hcmute.yourtours.enums.GenderEnum;
import com.hcmute.yourtours.enums.RoleEnum;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user")
public class UserCommand extends Persistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;


    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "userid", columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

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

    @Column(name = "actived")
    private Boolean actived;

    @Override
    protected void preWrite() {
        super.preWrite();
        if (userId == null) {
            userId = UUID.randomUUID();
        }
    }
}
