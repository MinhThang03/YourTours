package com.hcmute.yourtours.models.discount_campaign;

import com.hcmute.yourtours.models.common.NameDataModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class DiscountCampaignInfo extends NameDataModel<UUID> {

    @NotNull
    @Min(value = 0, message = "Phần trăm không được nhỏ hơn 0")
    private Double percent;

    @NotNull
    private LocalDate dateStart;

    @NotNull
    private LocalDate dateEnd;

    private UUID homeId;

    private String codeName;
}
