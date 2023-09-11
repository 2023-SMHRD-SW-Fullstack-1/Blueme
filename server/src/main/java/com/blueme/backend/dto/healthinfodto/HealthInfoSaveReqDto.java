package com.blueme.backend.dto.healthinfodto;

import com.blueme.backend.model.entity.HealthInfos;
import com.blueme.backend.model.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HealthInfoSaveReqDto {
	private String userEmail;
    private String heartrate;
    private String calorie;
    private String speed;
    private String step;

    public HealthInfos toEntity(Users user){
        return HealthInfos.builder().user(user).heartrate(heartrate).calorie(calorie).speed(speed).step(step).build();
    }

}
