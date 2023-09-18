package com.blueme.backend.controller;

import com.blueme.backend.dto.recmusiclistsdto.RecMusiclistsDetailResDto;
import com.blueme.backend.dto.recmusiclistsdto.RecMusiclistsRecent10ResDto;
import com.blueme.backend.dto.recmusiclistsdto.RecMusiclistsResDto;
import com.blueme.backend.model.entity.RecMusiclistDetails;
import com.blueme.backend.dto.recmusiclistsdto.RecMusiclistsSelectDetailResDto;
import com.blueme.backend.model.entity.RecMusiclistDetails;
import com.blueme.backend.service.RecMusiclistsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 추천 음악 관련 컨트롤러입니다.
 * 이 클래스에서는 추천 음악 목록의 조회, 상세 조회, 등록 기능을 제공합니다.
 *
 * @author 김혁
 * @version 1.1
 * @since 2023-09-14
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/recMusiclist")
@CrossOrigin("*")
public class RecMusiclistsController {

  private final RecMusiclistsService recMusiclistsService;

  /**
   * 특정 사용자의 모든 추천 음악 목록을 조회하는 GET 메서드입니다.
   *
   * @param userId 사용자 ID (String)
   * @return 해당 사용자의 모든 추천 음악 목록 (RecMusiclistsResDto 리스트)
   */
  @GetMapping("/{userId}")
  public ResponseEntity<List<RecMusiclistsResDto>> getAllRecMusiclists(@PathVariable("userId") String userId) {
    log.info("start register RecMusiclist for userId = {}", userId);
    List<RecMusiclistsResDto> recMusiclistsDtos = recMusiclistsService.getAllRecMusiclists(userId);
    log.info("ending register RecMusiclist for userId = {}", userId);
    if (recMusiclistsDtos.isEmpty()) {
      return ResponseEntity.status(404).build();
    } else {
      return ResponseEntity.ok().body(recMusiclistsDtos);
    }
  }

  /**
   * 특정 사용자의 최근 추천 음악 목록을 조회하는 GET 메서드입니다.
   *
   * @param userId 사용자 ID (String)
   * @return 해당 사용자의 최근 추천 음악 목록 (RecMusiclistsResDto)
   */
  @GetMapping("/recent/{userId}")
  public ResponseEntity<RecMusiclistsResDto> getRecentRecMusiclists(@PathVariable("userId") String userId) {
    log.info("starting getRecentRecMusiclists for userId = {}", userId);
    RecMusiclistsResDto recMusiclistsDto = recMusiclistsService.getRecentRecMusiclists(userId);
    log.info("ending getRecentRecMusiclists for userId = {}", userId);
    if (recMusiclistsDto == null) {
      return ResponseEntity.status(404).build();
    } else {
      return ResponseEntity.ok().body(recMusiclistsDto);
    }
  }

  /**
   * 특정 추천 음악 목록의 상세 정보를 조회하는 GET 메서드입니다.
   *
   * @param recMusiclistId 조회하려는 추천 음악 목록의 ID (String)
   * @return 해당 추천 음악 목록의 상세 정보 (RecMusiclistsSelectDetailResDto)
   */
  @GetMapping("/detail/{recMusiclistId}")
  public ResponseEntity<RecMusiclistsSelectDetailResDto> getRecMusiclistDetail(
      @PathVariable("recMusiclistId") String recMusiclistId) {
    log.info("starting getRecMusiclistDetail for recMusiclistId = {}", recMusiclistId);
    RecMusiclistsSelectDetailResDto detailResDto = recMusiclistsService.getRecMusiclistDetail(recMusiclistId);
    log.info("ending getRecMusiclistDetail for recMusiclistId = {}", recMusiclistId);
    if (detailResDto == null) {
      return ResponseEntity.status(404).build();
    } else {
      return ResponseEntity.ok().body(detailResDto);
    }
  }

  /**
   * 추천 음악 목록 중 가장최근 10개를 조회하는 GET 메서드입니다.
   *
   * @return 최근에 생성된 추천 음악 목록 중 상위 10개 (RecMusiclistsRecent10ResDto 리스트)
   */
  @GetMapping("/recent10")
  public ResponseEntity<List<RecMusiclistsRecent10ResDto>> getRecent10RecMusiclists() {
    log.info("starting getRecent10RecMusiclists");
    List<RecMusiclistsRecent10ResDto> list = recMusiclistsService.getRecent10RecMusiclists();
    log.info("ending getRecent10RecMusiclists");
    if (list.isEmpty()) {
      return ResponseEntity.status(404).build();
    } else {
      return ResponseEntity.ok().body(list);
    }
  }

  /**
   * GPT를 이용하여 추천 음악 목록을 등록하는 POST 메서드입니다.
   *
   * @param userId 사용자 ID (String)
   * @return 등록된 새로운 추천 음악 목록의 ID (Long)
   */
  @PostMapping("/chatGPT/{userId}")
  public ResponseEntity<Long> registerRecMusiclist(@PathVariable("userId") String userId) {
    log.info("start chatGPT register for userId = {}", userId);
    Long recMusiclistId = recMusiclistsService.registerRecMusiclist(userId);
    log.info("ending chatGPT register for userId = {}", userId);
    if (recMusiclistId == null) {
      return ResponseEntity.status(404).build();
    } else {
      return ResponseEntity.ok().body(recMusiclistId);
    }
  }

}
