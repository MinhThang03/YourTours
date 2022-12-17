package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.NameData;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.enums.DiscountHomeEnum;
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
@Table(name = "discount_home_categories")
public class DiscountHomeCategoriesCommand extends NameData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "discount_category_id", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID discountCategoryId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DiscountHomeEnum type;

    @Column(name = "num_date_default")
    private Integer numDateDefault;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;

    @Override
    protected void preWrite() {
        super.preWrite();
        if (discountCategoryId == null) {
            discountCategoryId = UUID.randomUUID();
        }
    }
}
