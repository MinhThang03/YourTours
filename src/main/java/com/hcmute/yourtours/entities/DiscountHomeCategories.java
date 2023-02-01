package com.hcmute.yourtours.entities;

import com.hcmute.yourtours.entities.base.NameData;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.enums.DiscountHomeEnum;
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
@Table(name = "discount_home_categories")
public class DiscountHomeCategories extends NameData {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2r")
    @Column(name = "discount_category_id", columnDefinition = "varchar(36)")
    @Type(type = "uuid-char")
    private UUID discountCategoryId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DiscountHomeEnum type;

    @Column(name = "num_date_default")
    private Integer numDateDefault;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommonStatusEnum status;

}
