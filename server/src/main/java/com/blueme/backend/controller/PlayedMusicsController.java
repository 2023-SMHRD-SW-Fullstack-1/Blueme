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

import com.blueme.backend.dto.musicdto.MusicInfoResDto;
import com.blueme.backend.dto.playedmusicdto.PlayedMusicsSaveReqDto;
import com.blueme.backend.service.PlayedMusicsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-16
설명: 사용자가 재생한 음악 컨트롤러
*/

@RequiredArgsConstructor
@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/playedmusic")
public class PlayedMusicsController {

  private final PlayedMusicsService playedMusicsService;

  /*
   * 재생된 음악 조회
   */
  @GetMapping("/get/{userId}")
  public List<MusicInfoResDto> getPlayedMusic(@PathVariable("userId") Long userId) {
    log.info("playedmusics get start by userID = {}", userId);
    return playedMusicsService.getPlayedMusic(userId);
  }

  /**
   * 재생된 음악 등록
   */
  @PostMapping("/add")
  public ResponseEntity<Long> savePlayedMusic(@RequestBody PlayedMusicsSaveReqDto request) {
    log.info("playedmusics post save start by userID = {}", request.getUserId());
    Long savedId = playedMusicsService.savePlayedMusic(request);
    return new ResponseEntity<>(savedId, HttpStatus.CREATED);
  }

}
