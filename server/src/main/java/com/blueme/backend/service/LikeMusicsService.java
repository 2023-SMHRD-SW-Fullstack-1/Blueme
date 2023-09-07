package com.blueme.backend.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blueme.backend.dto.likemusicsDto.LikemusicReqDto;
import com.blueme.backend.model.entity.LikeMusics;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.LikeMusicsJpaRepository;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeMusicsService {
  

  private final LikeMusicsJpaRepository likeMusicsJpaRepository;
  private final UsersJpaRepository usersJpaRepository;
  private final MusicsJpaRepository musicsJpaRepository;

  /**
  * put	저장된 음악 토글 (이미 있는 저장음악일시 삭제, 없을시 등록)
  */
  @Transactional
  public Long toggleLikeMusics(LikemusicReqDto requestDto) {
    Users user = usersJpaRepository.findById(Long.parseLong(requestDto.getUserId()))
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    Musics music = musicsJpaRepository.findById(Long.parseLong(requestDto.getMusicId()))
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음악입니다."));

    LikeMusics likeMusics = likeMusicsJpaRepository.findByUserIdAndMusicId(user.getId(), music.getId());

    if (likeMusics!= null) {
      likeMusicsJpaRepository.delete(likeMusics);
      return -1L;
    } else {
      likeMusics = new LikeMusics(music, user);
      likeMusicsJpaRepository.save(likeMusics);
    }
      return likeMusics.getId();
  }


}
