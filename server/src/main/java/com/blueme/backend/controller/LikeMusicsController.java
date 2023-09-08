package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.likemusicsDto.LikemusicIsSaveReqDto;
import com.blueme.backend.dto.likemusicsDto.LikemusicReqDto;
import com.blueme.backend.service.LikeMusicsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/likemusics")
@CrossOrigin("http://localhost:3000")
public class LikeMusicsController {
  
  private final LikeMusicsService likeMusicsService;

  /*
   * post 사용자가 단일음악 저장 했는지 조회
   */
  @PostMapping("/issave")
  public Long isSaveOne(@RequestBody LikemusicIsSaveReqDto requestDto){
    log.info("Starting LikeMusics isSaveOne for userId {}", requestDto.getUserId());
    return likeMusicsService.isSaveOne(requestDto);
  }

  /**
   *  post 음악 저장 토글 (저장시 고유 ID값 반환, 실패시 -1 반환)
   */
  @PutMapping("/toggleLike")
  public Long login(@RequestBody LikemusicReqDto requestDto) {
    log.info("Starting LikeMusics toggle for userId {}", requestDto.getUserId());
    Long likeMusicsId = likeMusicsService.toggleLikeMusics(requestDto);
    log.info("Likemusics toggle completed with ID {}", likeMusicsId);
    return likeMusicsId ;
  }



}
