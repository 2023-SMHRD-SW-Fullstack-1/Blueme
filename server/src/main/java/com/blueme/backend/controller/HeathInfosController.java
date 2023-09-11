package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.healthinfodto.HealthInfoSaveReqDto;
import com.blueme.backend.service.HealthInfosService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-10
설명: 워치로부터 받는 건강정보 컨트롤러
*/


@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/healthinfo")
public class HeathInfosController {

  private final HealthInfosService healthInfosService;

  /*
   * post 건강정보 등록
   */

  @PostMapping("/add")
    public Long saveHealthInfo(@RequestBody HealthInfoSaveReqDto request) {
      log.info("start saveHealthInfo for userEmail = {}", request.getUserEmail());
      String userEmail = request.getUserEmail();
      String heartRate = request.getHeartrate();
      String calorie = request.getCalorie();
      String speed = request.getSpeed();
      String step = request.getStep();
      
      System.out.println(userEmail);
      System.out.println(heartRate);
      System.out.println(calorie);
      System.out.println(speed);
      System.out.println(step);

      return healthInfosService.saveHealthInfo(request);
    }
}
