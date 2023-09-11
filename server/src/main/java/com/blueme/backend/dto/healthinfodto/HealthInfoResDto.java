package com.blueme.backend.dto.healthinfodto;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.blueme.backend.model.entity.HealthInfos;

import lombok.Getter;

@Getter
public class HealthInfoResDto {
  public String healthInfoId;
  public String createdAt;
  public String avgHeartRate;
  public String calorie;
  public String avgSpeed;
  public String stepsPerMinute;

  public HealthInfoResDto(HealthInfos healthInfo) {
    this.healthInfoId = healthInfo.getId().toString();
    this.createdAt = healthInfo.getCreatedAt().toString();
    this.avgHeartRate = formatToInt(healthInfo.getHeartrate());
    this.stepsPerMinute = formatToInt(healthInfo.getStep());
    this.calorie = healthInfo.getCalorie();
    this.avgSpeed = formatToDouble(healthInfo.getSpeed());
  }

  // 스피드 소수점두자리까지 포매팅
  public String formatToDouble(String value){
    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.CEILING);
    Double speedValue = Double.parseDouble(value);
    return df.format(speedValue);
  }

  // 정수만 나오게 포매팅
  public String formatToInt(String value){
    DecimalFormat df = new DecimalFormat("#");
    df.setRoundingMode(RoundingMode.CEILING);
    Double val = Double.parseDouble(value);
    return df.format(val);
  }

}


