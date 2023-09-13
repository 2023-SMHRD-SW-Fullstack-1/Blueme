package com.blueme.backend.controller;

import com.blueme.backend.dto.gptdto.ChatGptResDto;
import com.blueme.backend.dto.recmusiclistsdto.RecMusiclistsDetailResDto;
import com.blueme.backend.dto.recmusiclistsdto.RecMusiclistsResDto;
import com.blueme.backend.model.entity.RecMusiclists;
import com.blueme.backend.service.RecMusiclistsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    return recMusiclistsService.getRecentRecMusiclists(userId);
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
