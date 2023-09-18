package com.blueme.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.savedMusiclistsdto.SavedMusiclistsGetResDto;
import com.blueme.backend.dto.savedMusiclistsdto.SavedMusiclistsResDto;
import com.blueme.backend.dto.savedMusiclistsdto.SavedMusiclistsSaveReqDto;
import com.blueme.backend.service.SavedMusiclistsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 저장된 음악 목록 관련 컨트롤러입니다.
 * 이 클래스에서는 저장된 음악 목록의 조회, 상세 조회, 등록 기능을 제공합니다.
 *
 * @author 김혁
 * @version 1.0
 * @since 2023-09-14
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/savedMusiclist")
@CrossOrigin("*")
public class SavedMusiclistsController {

  private final SavedMusiclistsService savedMusiclistsService;

  /**
   * 특정 사용자의 모든 저장된 음악 목록을 조회하는 GET 메서드입니다.
   *
   * @param userId 사용자 ID (String)
   * @return 해당 사용자의 모든 저장된 음악 목록 (SavedMusiclistsResDto 리스트), 저장된 음악이 없을경우
   *         404 상태코드 반환
   */
  @GetMapping("/get/{userId}")
  public ResponseEntity<List<SavedMusiclistsResDto>> getSavedMusiclists(@PathVariable("userId") String userId) {
    log.info("Starting getSavedMusiclists for userId: {}", userId);
    List<SavedMusiclistsResDto> lists = savedMusiclistsService.getSavedMusiclists(userId);
    log.info("Ending getSavedMusiclists for userId: {}", userId);
    if (lists.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(savedMusiclistsService.getSavedMusiclists(userId), HttpStatus.OK);
    }
  }

  /**
   * 특정 저장된 음악 목록의 상세 정보를 조회하는 GET 메서드입니다.
   *
   * @param savedMusiclistId 조회하려는 저장된 음악 목록의 ID (String)
   * @return 해당 저장된 음악 목록의 상세 정보 (SavedMusiclistsGetResDto), 저장된 음악이 없을경우 404 상태코드
   *         반환
   */
  @GetMapping("/get/detail/{savedMusiclistId}")
  public ResponseEntity<SavedMusiclistsGetResDto> getSavedMusiclistDetail(
      @PathVariable("savedMusiclistId") String savedMusiclistId) {
    log.info("Starting getSavedMusiclistDetail for savedMusiclistId: {}", savedMusiclistId);
    return new ResponseEntity<>(savedMusiclistsService.getSavedMusiclistDetail(savedMusiclistId), HttpStatus.OK);
  }

  /**
   * 새로운 저장된 음악 목록을 등록하는 POST 메서드입니다.
   *
   * @param request 등록하려는 저장된 음악 목록 정보가 담긴 요청 객체 (SavedMusiclistsSaveReqDto)
   * @return 등록된 새로운 저장된 음악 목록의 ID (Long). 만약 요청 처리에 실패한다면 BAD_REQUEST 상태 코드 반환
   */
  @PostMapping("/add")
  public ResponseEntity<Long> addSavedMusiclist(@RequestBody SavedMusiclistsSaveReqDto request) {
    log.info("Starting savedMusiclist for userId: {}", request.getUserId());
    Long savedMusiclistId = savedMusiclistsService.save(request);
    log.info("Ending savedMusiclist for userId: {}", request.getUserId());
    if (savedMusiclistId == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else
      return new ResponseEntity<>(savedMusiclistId, HttpStatus.CREATED);
  }

}
