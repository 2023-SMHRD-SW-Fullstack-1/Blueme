package com.blueme.backend.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-13
설명: 음악저장 관련 서비스
*/

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeMusicsService {

  private final LikeMusicsJpaRepository likeMusicsJpaRepository;
  private final UsersJpaRepository usersJpaRepository;
  private final MusicsJpaRepository musicsJpaRepository;

  /*
   * get 사용자가 음악저장 했는지 조회
   */
  @Transactional
  public Long isSaveOne(LikemusicIsSaveReqDto requestdDto) {
    LikeMusics likeMusic = likeMusicsJpaRepository.findByUserIdAndMusicId(Long.parseLong(requestdDto.getUserId()),
        Long.parseLong(requestdDto.getMusicId()));
    return likeMusic == null ? -1L : likeMusic.getId();
  }

  /**
   * put 저장된 음악 토글 (이미 있는 저장음악일시 삭제, 없을시 등록)
   */
  @Transactional
  public Long toggleLikeMusics(LikemusicReqDto requestDto) {
    Users user = usersJpaRepository.findById(Long.parseLong(requestDto.getUserId()))
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    Musics music = musicsJpaRepository.findById(Long.parseLong(requestDto.getMusicId()))
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음악입니다."));

    LikeMusics likeMusics = likeMusicsJpaRepository.findByUserIdAndMusicId(user.getId(), music.getId());

    if (likeMusics != null) {
      likeMusicsJpaRepository.delete(likeMusics);
      return -1L;
    } else {
      likeMusics = new LikeMusics(music, user);
      likeMusicsJpaRepository.save(likeMusics);
    }
    return likeMusics.getId();
  }

  /*
   * get 사용자가 저장한 음악리스트 조회
   */
  @Transactional
  public List<MusicInfoResDto> getMusicsByUserId(String userId) {
    try {
      List<LikeMusics> likeMusics = likeMusicsJpaRepository.findByUserId(Long.parseLong(userId));
      List<MusicInfoResDto> musicInfoResDtos = new ArrayList<>();
      for (LikeMusics likeMusic : likeMusics) {
        Musics music = musicsJpaRepository.findById(likeMusic.getMusic().getId())
            .orElseThrow(() -> new IllegalArgumentException("해당하는 음악ID가 없습니다."));
        MusicInfoResDto res = new MusicInfoResDto(music);
        musicInfoResDtos.add(res);
      }
      return musicInfoResDtos;
    } catch (Exception e) {
      throw new RuntimeException("저장리스트 - 재킷파일 전송 실패", e);
    }
  }

}
