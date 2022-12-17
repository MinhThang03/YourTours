package com.hcmute.yourtours.models.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder
public class BookHomeDetail extends BookHomeInfo {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String homeName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String ownerName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double baseCost;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastModifiedDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double surchargeCost;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String userName;

}
