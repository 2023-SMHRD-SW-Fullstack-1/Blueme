package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.healthinfodto.HealthInfoResDto;
import com.blueme.backend.dto.healthinfodto.HealthInfoSaveReqDto;
import com.blueme.backend.model.entity.HealthInfos;
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
   * get 건강정보 조회
   */
  @GetMapping("/get/{userId}")
  public HealthInfoResDto getHealthInfo(@PathVariable("userId") String id) {
    log.info("start getHealthInfo for id = {}", id);
    return healthInfosService.getHealthInfo(Long.parseLong(id));
  }


  /*
   * post 건강정보 등록
   */

  @PostMapping("/add")
    public Long saveHealthInfo(@RequestBody HealthInfoSaveReqDto request) {
      log.info("start saveHealthInfo for userEmail = {}", request.getUserEmail());
      return healthInfosService.saveHealthInfo(request);
    }
}
