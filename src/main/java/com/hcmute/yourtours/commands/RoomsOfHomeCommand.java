package com.hcmute.yourtours.commands;

import com.hcmute.yourtours.commands.base.NameData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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


    @Override
    protected void preWrite() {
        super.preWrite();
        if (roomOfHomeId == null) {
            roomOfHomeId = UUID.randomUUID();
        }
    }
}
