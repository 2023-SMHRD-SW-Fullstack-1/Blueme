package com.blueme.backend.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blueme.backend.dto.likemusicsDto.LikemusicReqDto;
import com.blueme.backend.model.entity.LikeMusics;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.LikeMusicsJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeMusicsService {
  

  private final LikeMusicsJpaRepository likeMusicsJpaRepository;

  /**
  * put	저장된 음악 토글 (이미 있는 저장음악일시 삭제, 없을시 등록)
  */
  @Transactional
  public Long toggleLikeMusics(LikemusicReqDto requestDto) {
    Users user = requestDto.toEntityUser();
    Musics music = requestDto.toEntityMusic();

    LikeMusics likeMusics = likeMusicsJpaRepository.findByUserIdAndMusicId(user.getId(), music.getId());

    if (likeMusics!= null) {
      likeMusicsJpaRepository.delete(likeMusics);
    } else {
      likeMusics = new LikeMusics(music, user);
      likeMusicsJpaRepository.save(likeMusics);
    }

    return (likeMusics == null)? -1L : likeMusics.getMusic().getId();
  }


}
