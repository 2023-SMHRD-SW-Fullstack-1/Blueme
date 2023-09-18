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
   * get 저장음악리스트 조회
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

  /*
   * get 저장음악리스트 상세조회
   */
  @GetMapping("/get/detail/{savedMusiclistId}")
  public ResponseEntity<SavedMusiclistsGetResDto> getSavedMusiclistDetail(
      @PathVariable("savedMusiclistId") String savedMusiclistId) {
    log.info("Starting getSavedMusiclistDetail for savedMusiclistId: {}", savedMusiclistId);
    return new ResponseEntity<>(savedMusiclistsService.getSavedMusiclistDetail(savedMusiclistId), HttpStatus.OK);
  }

  /*
   * post 저장음악리스트 등록
   */
  @PostMapping("/add")
  public ResponseEntity<Long> addSavedMusiclist(@RequestBody SavedMusiclistsSaveReqDto request) {
    log.info("Starting savedMusiclist for userId: {}", request.getUserId());
    Long savedMusiclistId = savedMusiclistsService.save(request);
    if (savedMusiclistId == -1L) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else
      return new ResponseEntity<>(savedMusiclistId, HttpStatus.CREATED);
  }

}
