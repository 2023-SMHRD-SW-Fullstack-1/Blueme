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

/**
 * SearchsController는 검색 관련 컨트롤러입니다.
 * 이 클래스에서는 최근검색 등록, 최근검색 조회, 음악 검색을 처리합니다.
 *
 * @author 김혁
 * @version 1.0
 * @since 2023-09-15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchsController {

  private final SearchsService searchsService;

  /**
   * 최근검색 등록을 위한 PUT 메서드입니다.
   * 이미 최근검색 목록에 있을경우 재등록, 없을경우 등록합니다.
   * 
   * @param request userID와 musicID 를 담은 객체 (SearchsRecentResDto)
   * @return 저장된 검색아이디를 반환합니다.
   */
  @PutMapping("")
  public ResponseEntity<Long> saveSearch(@RequestBody SearchSaveReqDto request) {
    log.info("save recentSearch starting for userId = {}", request.getUserId());
    Long searchId = searchsService.saveSearch(request);
    log.info("save recentSearch finished for userId = {}", request.getUserId());
    return new ResponseEntity<Long>(searchId, HttpStatus.CREATED);
  }

  /**
   * 최근검색 조회를 위한 GET 메서드입니다.
   * 
   * @param userId 사용자ID
   * @return 최근검색정보를 담은 객체 리스트를 반환합니다 (List<SearchsRecentResDto>), 최근검색이 없을시 404
   *         상태코드를 반환합니다.
   */
  @GetMapping("/{userId}")
  public ResponseEntity<List<SearchsRecentResDto>> getRecentSearch(@PathVariable("userId") Long userId) {
    log.info("get recentSearch starting for userId = {}", userId);
    List<SearchsRecentResDto> results = searchsService.getRecentSearch(userId);
    log.info("get recentSearch finished for userId = {}", userId);
    if (results.isEmpty()) {
      return new ResponseEntity<List<SearchsRecentResDto>>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<List<SearchsRecentResDto>>(results, HttpStatus.OK);
  }

  /**
   * 음악 검색을 위한 GET 메서드입니다.
   * 
   * @param keyword 사용자가 검색하고자 하는 키워드
   * @return 검색결과 리스트를 반환합니다. (List<SearchsResDto>), 검색결과가 없을시 NO_CONTENT 상태코드를
   *         반환합니다.
   */
  @GetMapping("/music/{keyword}")
  public ResponseEntity<List<SearchsResDto>> getSearchs(@PathVariable("keyword") String keyword) {
    log.info("get search starting for keyword = {}", keyword);
    List<SearchsResDto> results = searchsService.getSearchs(keyword);
    log.info("get search finished for keyword = {}", keyword);
    if (results.isEmpty()) {
      return new ResponseEntity<List<SearchsResDto>>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<List<SearchsResDto>>(results, HttpStatus.OK);
  }

}
