package com.hcmute.yourtours.commands;

import com.hcmute.yourtours.commands.base.Persistence;
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
    private UUID bedOfHomeID;


    @Column(name = "bed_category_id", columnDefinition = "BINARY(16)")
    private UUID categoryId;


    @Column(name = "room_of_home_id", columnDefinition = "BINARY(16)")
    private UUID roomOfHomeID;

    @Column(name = "amount")
    private Integer amount;

    @Override
    protected void preWrite() {
        super.preWrite();
        if (bedOfHomeID == null) {
            bedOfHomeID = UUID.randomUUID();
        }
    }
}
