package com.blueme.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blueme.backend.dto.searchdto.SearchSaveReqDto;
import com.blueme.backend.dto.searchdto.SearchsRecentResDto;
import com.blueme.backend.dto.searchdto.SearchsResDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.Searchs;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.repository.SearchsJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.service.exception.MusicNotFoundException;
import com.blueme.backend.service.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

/*
 * 작성자 : 김혁
 * 작성일 : 2023-09-15
 * 설명   : 검색 서비스
 */

@RequiredArgsConstructor
@Service
public class SearchsService {

  private final SearchsJpaRepository searchsJpaRepository;
  private final UsersJpaRepository usersJpaRepository;
  private final MusicsJpaRepository musicsJpaRepository;

  /*
   * post 검색 등록
   */
  @Transactional
  public Long saveSearch(SearchSaveReqDto request) {
    Users user = usersJpaRepository.findById(request.getParsedUserId())
        .orElseThrow(() -> new UserNotFoundException(request.getParsedUserId()));
    Musics music = musicsJpaRepository.findById(request.getParsedMusicId())
        .orElseThrow(() -> new MusicNotFoundException(request.getParsedMusicId()));

    return searchsJpaRepository.save(Searchs.builder()
        .user(user)
        .music(music)
        .build()).getId();
  }

  /*
   * get 검색 목록 조회
   */
  @Transactional(readOnly = true)
  public List<SearchsRecentResDto> getRecentSearch(Long userId) {
    return searchsJpaRepository.findByUserIdOrderByCreatedAtDesc(userId)
        .stream().map(SearchsRecentResDto::new).collect(Collectors.toList());
  }

  /*
   * 음악 검색
   */
  @Transactional(readOnly = true)
  public List<SearchsResDto> getSearchs(String keyword) {
    return musicsJpaRepository.findTop20ByTitleContainingOrArtistContaining(keyword, keyword)
        .stream().map(SearchsResDto::new).collect(Collectors.toList());
  }
}
