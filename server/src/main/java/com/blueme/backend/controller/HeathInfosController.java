package com.blueme.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.healthinfodto.HealthInfoResDto;
import com.blueme.backend.dto.healthinfodto.HealthInfoSaveReqDto;
import com.blueme.backend.service.HealthInfosService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * HealthInfosController는 건강정보 컨트롤러 클래스입니다.
 * 이 클래스는 REST API 엔드포인트를 제공하여 건강정보 조회 및 등록 기능을 처리합니다.
 *
 * @author 김혁
 * @version 1.0
 * @since 2023-09-10
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/healthinfo")
public class HeathInfosController {

  private final HealthInfosService healthInfosService;

  /**
   * 건강정보 조회를 위한 GET 메서드입니다.
   *
   * @param userId 사용자 ID
   * @return 건강정보 응답 DTO (HealthInfoResDto)
   */
  @GetMapping("/get/{userId}")
  public ResponseEntity<HealthInfoResDto> getHealthInfo(@PathVariable("userId") String userId) {
    log.info("start getHealthInfo for userid = {}", userId);
    HealthInfoResDto healthInfo = healthInfosService.getHealthInfo(Long.parseLong(userId));
    log.info("end getHealthInfo for userid = {}", userId);
    if (healthInfo == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(healthInfo);
    }
  }

  /**
   * 건강정보 등록을 위한 POST 메서드입니다.
   *
   * @param request 건강정보 저장 요청 DTO (HealthInfoSaveReqDto)
   * @return 저장된 건강정보의 ID (Long)
   */
  @PostMapping("/add")
  public ResponseEntity<Long> saveHealthInfo(@RequestBody HealthInfoSaveReqDto request) {
    log.info("start saveHealthInfo for userEmail = {}", request.getUserEmail());
    Long savedHealthInfoId = healthInfosService.saveHealthInfo(request);
    log.info("end saveHealthInfo for userid = {}", request.getUserEmail());
    if (savedHealthInfoId == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.status(HttpStatus.CREATED).body(savedHealthInfoId);
    }
  }

}
