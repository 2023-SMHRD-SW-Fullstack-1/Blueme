package com.blueme.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
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

/**
 * PlayedMusicsService는 사용자가 재생한 음악 서비스 클래스입니다.
 * <p>
 * 이 클래스에서는 사용자가 재생한 음악 정보를 조회하고 등록하는 기능을 제공합니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023.09-18
 */
@Service
@RequiredArgsConstructor
public class PlayedMusicsService {

  private final PlayedMusicsJpaRepository playedMusicsJpaRepository;
  private final UsersJpaRepository usersJpaRepository;
  private final MusicsJpaRepository musicsJpaRepository;

  /**
   * 특정 사용자의 최근 재생된 음악 목록을 조회합니다.
   *
   * @param userId 사용자 ID
   * @return 해당 사용자의 최근 재생된 음악 목록. 최신 순으로 정렬됨 (MusicInfoResDto 리스트)
   */
  @Transactional(readOnly = true)
  public List<MusicInfoResDto> getPlayedMusic(Long userId) {
    PageRequest pageRequest = PageRequest.of(0, 20);
    List<Musics> musicsList = playedMusicsJpaRepository.findDistinctMusicByUserId(userId, pageRequest);
    return musicsList.stream().map(MusicInfoResDto::new).collect(Collectors.toList());
  }

  /**
   * 특정 사용자가 재생한 음악을 등록합니다.
   * <p>
   * 이미 등록된 경우에는 기존 정보를 삭제하고 새로 등록합니다.
   * </p>
   *
   * @param request 사용자 ID와 음악 ID를 포함하는 요청 객체 (PlayedMusicsSaveReqDto)
   * @return 저장된 재생된 음악의 ID (Long)
   */
  @Transactional
  public Long savePlayedMusic(PlayedMusicsSaveReqDto request) {
    Users user = usersJpaRepository.findById(request.getParsedUserId())
        .orElseThrow(() -> new UserNotFoundException(request.getParsedUserId()));
    Musics music = musicsJpaRepository.findById(request.getParsedMusicId())
        .orElseThrow(() -> new MusicNotFoundException(request.getParsedMusicId()));
    PlayedMusics playedMusics = playedMusicsJpaRepository.findDistinctByUserAndMusic(user, music);
    if (playedMusics == null) {
      return playedMusicsJpaRepository.save(PlayedMusics.builder().user(user).music(music).build()).getId();
    } else {
      playedMusicsJpaRepository.delete(playedMusics);
      return playedMusicsJpaRepository.save(PlayedMusics.builder().user(user).music(music).build()).getId();
    }

  }
}
