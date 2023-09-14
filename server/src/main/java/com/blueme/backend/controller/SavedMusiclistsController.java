package com.blueme.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.savedMusiclistsdto.SavedMusiclistsSaveReqDto;
import com.blueme.backend.service.SavedMusiclistsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-14
설명: 저장한음악 목록 컨트롤러
*/

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/savedMusiclist")
@CrossOrigin("*")
public class SavedMusiclistsController {

  private final SavedMusiclistsService savedMusiclistsService;

  /*
   * post 저장음악리스트 등록
   */
  @PostMapping("/add")
  public ResponseEntity<Long> addSavedMusiclist(@RequestBody SavedMusiclistsSaveReqDto request) {
    log.info("Starting savedMusiclist for userId: {}", request.getUserId());
    Long savedId = savedMusiclistsService.save(request);
    return new ResponseEntity<>(savedId, HttpStatus.OK);
  }

}
