package com.blueme.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blueme.backend.dto.likemusicsDto.LikemusicIsSaveReqDto;
import com.blueme.backend.dto.likemusicsDto.LikemusicReqDto;
import com.blueme.backend.dto.musicdto.MusicInfoResDto;
import com.blueme.backend.model.entity.LikeMusics;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.LikeMusicsJpaRepository;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;

import lombok.RequiredArgsConstructor;

/*
작성자: 김혁
날짜(수정포함): 2023-09-13
설명: 음악저장 관련 서비스
*/

/**
 * LikeMusicsService는 저장음악 서비스 클래스입니다.
 * 이 클래스에서는 건강정보 조회 및 등록 기능을 제공합니다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-13
 */
@RequiredArgsConstructor
@Service
public class LikeMusicsService {

  private final LikeMusicsJpaRepository likeMusicsJpaRepository;
  private final UsersJpaRepository usersJpaRepository;
  private final MusicsJpaRepository musicsJpaRepository;

  /**
   * 사용자의 음악 저장 여부를 조회합니다.
   *
   * @param requestDto LikeMusics 저장여부 조회 요청 DTO (LikemusicIsSaveReqDto)
   * @return 저장된 LikeMusics의 ID (Long)
   */
  @Transactional(readOnly = true)
  public Long isSaveOne(LikemusicIsSaveReqDto requestdDto) {
    LikeMusics likeMusic = likeMusicsJpaRepository.findByUserIdAndMusicId(Long.parseLong(requestdDto.getUserId()),
        Long.parseLong(requestdDto.getMusicId()));
    return likeMusic == null ? -1L : likeMusic.getId();
  }

  /**
   * 사용자 ID와 음악ID를 기반으로 음악저장을 수행합니다.
   *
   * @param requestDto 음악저장을 위한 저장요청 DTO (LikemusicReqDto)
   * @return 저장된 LikeMusics의 ID (Long)
   */
  @Transactional
  public Long toggleLikeMusics(LikemusicReqDto requestDto) {
    Users user = usersJpaRepository.findById(Long.parseLong(requestDto.getUserId()))
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    Musics music = musicsJpaRepository.findById(Long.parseLong(requestDto.getMusicId()))
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음악입니다."));
    LikeMusics likeMusics = likeMusicsJpaRepository.findByUserIdAndMusicId(user.getId(), music.getId());

    if (likeMusics != null) { // 삭제
      likeMusicsJpaRepository.delete(likeMusics);
      return -1L;
    } else { // 등록
      likeMusics = new LikeMusics(music, user);
      likeMusicsJpaRepository.save(likeMusics);
      return likeMusics.getId();
    }

  }

  /**
   * 사용자ID를 기반으로 음악리스트 조회를 수행합니다.
   *
   * @param requestDto 음악리스트 조회를 위한 userId (String)
   * @return 저장된 음악정보 리스트 DTO (List<MusicInfoResDto>)
   */
  @Transactional(readOnly = true)
  public List<MusicInfoResDto> getMusicsByUserId(String userId) {
    List<LikeMusics> likeMusics = likeMusicsJpaRepository.findByUserId(Long.parseLong(userId));
    return likeMusics.stream().map((lm) -> new MusicInfoResDto(lm.getMusic())).collect(Collectors.toList());
  }

}
