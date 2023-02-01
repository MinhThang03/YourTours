package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.Persistence;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "securities_of_home")
public class SecuritiesOfHome extends Persistence {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2r")
    @Column(name = "security_of_home_id", columnDefinition = "varchar(36)")
    @Type(type = "uuid-char")
    private UUID securityOfHomeId;

    @Column(name = "isHave")
    private Boolean isHave;

    @Column(name = "home_id", columnDefinition = "BINARY(16)")
    private UUID homeId;

    @Column(name = "security_category_id", columnDefinition = "BINARY(16)")
    private UUID categoryId;

}

