package com.hcmute.yourtours.models.rooms_of_home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class RoomOfHomeCreateModel implements Serializable {

    private UUID categoryId;

    @Min(value = 0, message = "Không được phép nhỏ hơn 0")
    private Integer number;
}
