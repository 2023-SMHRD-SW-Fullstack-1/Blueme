package com.blueme.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.admindto.MusicInfoTop10ResDto;
import com.blueme.backend.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ADMIN 컨트롤러로 ADMIN 관련 행위를 처리합니다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-10-02
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin2")
public class AdminController {
  private final AdminService adminService;

  /**
   * 조회수 Top10 음악 조회하는 메소드입니다.
   * 
   * @return 음악정보 목록 (List<MusicInfoTop10RestDto>)
   */
  @GetMapping("/top10")
  public ResponseEntity<List<MusicInfoTop10ResDto>> getAdminTop10MusicInfo() {
    log.info("start getAdminMusicInfo");
    List<MusicInfoTop10ResDto> result = adminService.getTop10MusicInfo();
    log.info("end getAdminMusicInfo");
    if (result.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } else {
      return ResponseEntity.ok().body(result);
    }
  }

  /**
   * date를 기반으로 그 날의 새로운 회원수를 조회하는 메서드입니다.
   * 
   * @param date 날짜 (String)
   * @return 새로 가입한 회원수(Long)
   */
  @GetMapping("/newclients/{date}")
  public ResponseEntity<Long> getNewClients(@PathVariable String date) {
    log.info("start getNewClients with no end message");
    return ResponseEntity.ok().body(adminService.getNewClients(date));
  }

  /**
   * date 를 기반으로 그 날의 새로운 추천음악 수를 조회하는 메서드입니다.
   * 
   * @param date 날짜 (String)
   * @return 그 날의 새로운 GPT 추천목록수
   */
  @GetMapping("/newrecmusiclists/{date}")
  public ResponseEntity<Long> getRecMusics(@PathVariable String date) {
    log.info("start getRecMusics with no end message");
    return ResponseEntity.ok().body(adminService.getRecMusics(date));
  }

  /**
   * date 를 기반으로 그 날의 새로운 건강정보 수를 조회하는 메서드입니다.
   * 
   * @param date 날짜 (String)
   * @return 그 날의 새로운 건강정보 수
   */
  @GetMapping("/newhealthinfos/{date}")
  public ResponseEntity<Long> gethealthInfos(@PathVariable String date) {
    log.info("start getRecMusics with no end message");
    return ResponseEntity.ok().body(adminService.gethealthInfos(date));
  }
}
