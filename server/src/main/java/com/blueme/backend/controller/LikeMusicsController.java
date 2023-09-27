package com.blueme.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.blueme.backend.dto.musicdto.MusicInfoResDto;
import com.blueme.backend.service.LikeMusicsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * LikeMusicsController는 저장음악 컨트롤러 클래스입니다.
 * <p>
 * 이 클래스는 REST API 엔드포인트를 제공하여 저장음악 조회, 확인 및 등록을 처리합니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-10
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/likemusics")
public class LikeMusicsController {

  private final LikeMusicsService likeMusicsService;

  /**
   * 음악 저장 여부 조회를 위한 POST 메서드입니다.
   * 클라이언트와 협의 후 GET 요청으로 변경 요망합니다.
   *
   * @param requestDto LikeMusics 저장여부 조회 요청 DTO (LikemusicIsSaveReqDto)
   * @return 저장된 LikeMusics의 ID (Long)
   */
  @PostMapping("/issave")
  public ResponseEntity<Long> isSaveOne(@RequestBody LikemusicIsSaveReqDto requestDto) {
    log.info("Starting LikeMusics isSaved with musicId {} for userId {}", requestDto.getMusicId(),
        requestDto.getUserId());
    Long likeMusicsId = likeMusicsService.isSaveOne(requestDto);
    log.info("Ending LikeMusics isSaved with musicId {} for userId {}", requestDto.getMusicId(),
        requestDto.getUserId());
    if (likeMusicsId == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok().body(likeMusicsId);
    }
  }

  /**
   * 음악저장을 위한 PUT 메서드입니다.
   *
   * @param requestDto 음악저장을 위한 저장요청 DTO (LikemusicReqDto)
   * @return 저장된 LikeMusics의 ID (Long)
   */
  @PutMapping("/toggleLike")
  public ResponseEntity<Long> toggleLike(@RequestBody LikemusicReqDto requestDto) {
    log.info("Starting LikeMusics toggle for userId {}", requestDto.getUserId());
    Long likeMusicsId = likeMusicsService.toggleLikeMusics(requestDto);
    log.info("Ending LikeMusics toggle for userId {}", requestDto.getUserId());
    if (likeMusicsId == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok().body(likeMusicsId);
    }
  }

  /**
   * 저장 음악리스트 조회를 위한 GET 메서드입니다.
   *
   * @param requestDto 음악리스트 조회를 위한 userId (String)
   * @return 저장된 음악정보 리스트 DTO (List<MusicInfoResDto>)
   */
  @GetMapping("/{userId}")
  public ResponseEntity<List<MusicInfoResDto>> getMusics(@PathVariable("userId") String userId) {
    log.info("Starting LikeMusics getMusics for userId {}", userId);
    List<MusicInfoResDto> musicInfoList = likeMusicsService.getMusicsByUserId(userId);
    log.info("Ending LikeMusics getMusics for userId {}", userId);
    if (musicInfoList.isEmpty()) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok().body(musicInfoList);
    }
  }
}
