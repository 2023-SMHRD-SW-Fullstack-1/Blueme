package com.blueme.backend.controller;

import com.blueme.backend.dto.recmusiclistsdto.RecMusiclistsDetailResDto;
import com.blueme.backend.dto.recmusiclistsdto.RecMusiclistsRecent10ResDto;
import com.blueme.backend.dto.recmusiclistsdto.RecMusiclistsResDto;
import com.blueme.backend.service.RecMusiclistsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
작성자: 김혁
날짜(수정포함): 2023-09-12
설명: 추천음악 관련 컨트롤러
*/

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/recMusiclist")
@CrossOrigin("*")
public class RecMusiclistsController {

  private final RecMusiclistsService recMusiclistsService;

  /*
   * get 추천음악 조회
   */
  // @GetMapping("/{userId}")
  // public List<RecMusiclistsResDto> getAllRecMusiclists(@PathVariable("userId")
  // String userId) {
  // log.info("start register RecMusiclist for userId = {}", userId);
  // return recMusiclistsService.getAllRecMusiclists(userId);
  // }

  /*
   * (사용자에 해당하는) 최근 추천리스트 조회
   */
  @GetMapping("/recent/{userId}")
  public RecMusiclistsResDto getRecentRecMusiclists(@PathVariable("userId") String userId) {
    log.info("starting getRecentRecMusiclists for userId = {}", userId);
    return recMusiclistsService.getRecentRecMusiclists(userId);
  }

  /*
   * 최근 추천리스트 10개 조회
   */
  @GetMapping("/recent10")
  public ResponseEntity<List<RecMusiclistsRecent10ResDto>> getRecent10RecMusiclists() {
    log.info("starting getRecent10RecMusiclists");
    List<RecMusiclistsRecent10ResDto> list = recMusiclistsService.getRecent10RecMusiclists();
    if (list.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(list, HttpStatus.OK);
    }
  }

  /*
   * 테스트용
   */
  @PostMapping("/chatGPT/{userId}")
  public Long registerRecMusiclist(@PathVariable("userId") String userId) {
    log.info("start chatGPT TEST for userId = {}", userId);
    return recMusiclistsService.registerRecMusiclist(userId);
  }

}
