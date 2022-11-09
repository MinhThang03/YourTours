package com.hcmute.yourtours.models.homes;

import com.hcmute.yourtours.models.amenities_of_home.AmenityOfHomeDetail;
import com.hcmute.yourtours.models.images_home.ImageHomeDetail;
import com.hcmute.yourtours.models.rooms_of_home.models.RoomOfHomeCreateModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class HomeDetail extends HomeInfo {
    @NotNull
    private List<ImageHomeDetail> imagesOfHome;

    @NotNull
    private List<RoomOfHomeCreateModel> roomsOfHome;

    @NotNull
    private List<AmenityOfHomeDetail> amenitiesOfHome;

    private Double userRate;

    private boolean isBooked;
}
