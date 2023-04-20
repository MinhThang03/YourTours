package com.hcmute.yourtours.models.homes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.yourtours.enums.CommonStatusEnum;
import com.hcmute.yourtours.enums.RefundPolicyEnum;
import com.hcmute.yourtours.models.common.NameDataModel;
import com.hcmute.yourtours.models.images_home.ImageHomeDetail;
import com.hcmute.yourtours.models.rooms_of_home.models.NumberOfRoomsModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class HomeInfo extends NameDataModel<UUID> {

    private String wifi;

    private String passWifi;

    private String ruleOthers;

    private LocalTime timeCheckInStart;

    private LocalTime timeCheckInEnd;

    private LocalTime timeCheckOut;

    private String guide;

    private String addressDetail;

    private String provinceCode;

    private Integer rank;

    @Min(value = 0, message = "Giá tiền không được phép nhỏ hơn 0")
    @NotNull
    private Double costPerNightDefault;

    private RefundPolicyEnum refundPolicy;

    private CommonStatusEnum status;

    @Min(value = 0, message = "Số lượng khách không được phép nhỏ hơn 0")
    private Integer numberOfGuests;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long view;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long numberOfBooking;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long favorite;

    private String thumbnail;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double averageRate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long numberOfReviews;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastModifiedDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<NumberOfRoomsModel> roomsImportant;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer numberOfBed;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isFavorite;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String provinceName;

    @NotNull
    private List<ImageHomeDetail> imagesOfHome;

}
