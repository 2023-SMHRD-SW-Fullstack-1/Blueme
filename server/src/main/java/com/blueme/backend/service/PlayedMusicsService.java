package com.blueme.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blueme.backend.dto.musicdto.MusicInfoResDto;
import com.blueme.backend.dto.playedmusicdto.PlayedMusicsSaveReqDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.PlayedMusics;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.repository.PlayedMusicsJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.service.exception.MusicNotFoundException;
import com.blueme.backend.service.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

/*
작성자: 김혁
날짜(수정포함): 2023-09-10
설명: 사용자가 재생한 음악 서비스
*/

@Service
@RequiredArgsConstructor
public class PlayedMusicsService {

  private final PlayedMusicsJpaRepository playedMusicsJpaRepository;
  private final UsersJpaRepository usersJpaRepository;
  private final MusicsJpaRepository musicsJpaRepository;

  /*
   * 최근재생된 음악 조회
   */
  @Transactional(readOnly = true)
  public List<MusicInfoResDto> getPlayedMusic(Long userId) {
    List<Musics> musicsList = playedMusicsJpaRepository.findDistinctMusicByUserId(userId);
    return musicsList.stream().map(MusicInfoResDto::new).collect(Collectors.toList());
  }

  /*
   * 재생된 음악 등록 (이미있을경우 재등록, 없을경우 등록)
   */
  @Transactional
  public Long savePlayedMusic(PlayedMusicsSaveReqDto request) {
    Users user = usersJpaRepository.findById(request.getParsedUserId())
        .orElseThrow(() -> new UserNotFoundException(request.getParsedUserId()));
    Musics music = musicsJpaRepository.findById(request.getParsedMusicId())
        .orElseThrow(() -> new MusicNotFoundException(request.getParsedMusicId()));
    PlayedMusics playedMusics = playedMusicsJpaRepository.findByUserAndMusic(user, music);
    if (playedMusics == null) {
      return playedMusicsJpaRepository.save(PlayedMusics.builder().user(user).music(music).build()).getId();
    } else {
      playedMusicsJpaRepository.delete(playedMusics);
      return playedMusicsJpaRepository.save(PlayedMusics.builder().user(user).music(music).build()).getId();
    }

  }
}
