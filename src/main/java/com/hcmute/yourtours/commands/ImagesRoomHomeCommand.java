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
@Table(name = "images_room_home")
public class ImagesRoomHomeCommand extends Persistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "image_id", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID imageId;

    @Column(name = "path")
    private String path;

    @Column(name = "room_of_home_id", columnDefinition = "BINARY(16)")
    private UUID roomOfHomeId;


    @Override
    protected void preWrite() {
        super.preWrite();
        if (imageId == null) {
            imageId = UUID.randomUUID();
        }
    }
}
