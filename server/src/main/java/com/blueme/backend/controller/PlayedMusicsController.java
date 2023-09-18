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

/**
 * PlayedMusicsController는 재생된 음악 컨트롤러 입니다.
 * 이 클래스에서는 재생된 음악 조회, 등록 기능을 처리합니다.
 *
 * @author 김혁
 * @version 1.0
 * @since 2023-09-11
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/playedmusic")
public class PlayedMusicsController {

  private final PlayedMusicsService playedMusicsService;

  /**
   * 재생된 음악 조회를 위한 GET 매서드입니다.
   * 
   * @param userId 사용자 ID
   * @return 재생된 음악정보 목록, 일치하는 정보가 없을경우 status 404
   */
  @GetMapping("/get/{userId}")
  public ResponseEntity<List<MusicInfoResDto>> getPlayedMusic(@PathVariable("userId") Long userId) {
    log.info("playedmusics get start by userID = {}", userId);
    List<MusicInfoResDto> musicList = playedMusicsService.getPlayedMusic(userId);
    log.info("playedmusics get end by userID = {}", userId);
    if (musicList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } else {
      return ResponseEntity.ok(musicList);
    }
  }

  /**
   * 재생된 음악 등록을 위한 POST 메서드입니다.
   * 
   * @param request 사용자ID 와 음악ID (PlayedMusicsSaveReqDto)
   * @return 재생된음악 ID (Long)
   */
  @PostMapping("/add")
  public ResponseEntity<Long> savePlayedMusic(@RequestBody PlayedMusicsSaveReqDto request) {
    log.info("playedmusics post save start by userID = {}", request.getUserId());
    Long savedId = playedMusicsService.savePlayedMusic(request);
    log.info("playedmusics post save end by userID = {}", request.getUserId());
    return new ResponseEntity<>(savedId, HttpStatus.CREATED);
  }

}
