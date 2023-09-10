package com.blueme.backend.controller;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.musicdto.MusicInfoResDto;
import com.blueme.backend.dto.playedmusicdto.PlayedMusicsSaveReqDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.service.PlayedMusicsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-010
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
   *  재생된 음악 등록
   */
  @PostMapping("/add")
  public Long savePlayedMusic(@RequestBody PlayedMusicsSaveReqDto request) {
    log.info("playedmusics post save start by userID = {}", request.getUserId());
    return playedMusicsService.savePlayedMusic(request);
  }

}

