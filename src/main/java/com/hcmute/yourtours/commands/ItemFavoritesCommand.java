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
@Table(name = "item_favorites")
public class ItemFavoritesCommand extends Persistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "item_favorites_id", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID itemFavoritesId;


    @Column(name = "home_id", columnDefinition = "BINARY(16)")
    private UUID homeId;

    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;

    @Override
    protected void preWrite() {
        super.preWrite();
        if (itemFavoritesId == null) {
            itemFavoritesId = UUID.randomUUID();
        }
    }
}
