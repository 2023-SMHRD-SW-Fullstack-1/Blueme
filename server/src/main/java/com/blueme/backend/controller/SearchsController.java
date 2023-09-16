package com.blueme.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.searchdto.SearchSaveReqDto;
import com.blueme.backend.dto.searchdto.SearchsRecentResDto;
import com.blueme.backend.dto.searchdto.SearchsResDto;
import com.blueme.backend.service.SearchsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * 작성자 : 김혁
 * 작성일 : 2023-09-15
 * 설명   : 검색 컨트롤러
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchsController {

  private final SearchsService searchsService;

  /*
   * post 검색 등록(없으면 등록, 있으면 업데이트)
   */
  @PutMapping("")
  public ResponseEntity<Long> saveSearch(@RequestBody SearchSaveReqDto request) {
    log.info("save recentSearch starting for userId = {}", request.getUserId());
    Long searchId = searchsService.saveSearch(request);
    log.info("save recentSearch finished for userId = {}", request.getUserId());
    return new ResponseEntity<Long>(searchId, HttpStatus.CREATED);
  }

  /*
   * get 검색 조회
   */
  @GetMapping("/{userId}")
  public ResponseEntity<List<SearchsRecentResDto>> getRecentSearch(@PathVariable("userId") Long userId) {
    log.info("get recentSearch starting for userId = {}", userId);
    List<SearchsRecentResDto> results = searchsService.getRecentSearch(userId);
    if (results.isEmpty()) {
      return new ResponseEntity<List<SearchsRecentResDto>>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<List<SearchsRecentResDto>>(results, HttpStatus.OK);
  }

  /*
   * get 음악 검색
   */
  @GetMapping("/music/{keyword}")
  public ResponseEntity<List<SearchsResDto>> getSearchs(@PathVariable("keyword") String keyword) {
    log.info("get search starting for keyword = {}", keyword);
    List<SearchsResDto> results = searchsService.getSearchs(keyword);
    if (results.isEmpty()) {
      return new ResponseEntity<List<SearchsResDto>>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<List<SearchsResDto>>(results, HttpStatus.OK);
  }

}
