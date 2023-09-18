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

/**
 * SearchsService는 검색 관련 서비스 클래스입니다.
 * <p>
 * 이 클래스에서는 검색 등록, 최근 검색 목록 조회, 음악 검색 기능을 제공합니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-15
 */
@RequiredArgsConstructor
@Service
public class SearchsService {

  private final SearchsJpaRepository searchsJpaRepository;
  private final UsersJpaRepository usersJpaRepository;
  private final MusicsJpaRepository musicsJpaRepository;

  /**
   * 새로운 최근 검색을 등록합니다.
   * <p>
   * 이미 최근검색한 목록에 있을경우 삭제후 재등록, 없을경우 등록합니다.
   * </p>
   *
   * @param request 사용자ID와 음악ID가 담긴 요청 객체 (SearchSaveReqDto)
   * @return 저장된 검색의ID (Long)
   */
  @Transactional
  public Long saveSearch(SearchSaveReqDto request) {
    Users user = usersJpaRepository.findById(request.getParsedUserId())
        .orElseThrow(() -> new UserNotFoundException(request.getParsedUserId()));
    Musics music = musicsJpaRepository.findById(request.getParsedMusicId())
        .orElseThrow(() -> new MusicNotFoundException(request.getParsedMusicId()));
    Searchs search = searchsJpaRepository.findByUserIdAndMusicId(user.getId(), music.getId());

    if (search == null) {
      return searchsJpaRepository.save(Searchs.builder()
          .user(user)
          .music(music)
          .build()).getId();
    } else {
      searchsJpaRepository.delete(search);
      return searchsJpaRepository.save(Searchs.builder()
          .user(user)
          .music(music)
          .build()).getId();
    }

  }

  /**
   * 특정사용자 ID를 기반으로 최근검색목록 조회를 수행합니다.
   * 
   * @param userId 사용자ID
   * @return 검색 정보가 담긴 목록 (List<SearchsRecentResDto>)
   */
  @Transactional(readOnly = true)
  public List<SearchsRecentResDto> getRecentSearch(Long userId) {
    return searchsJpaRepository.findByUserIdOrderByCreatedAtDesc(userId)
        .stream().map(SearchsRecentResDto::new).collect(Collectors.toList());
  }

  /**
   * 키워드를 기반으로 음악 검색을 수행합니다.
   * 
   * @param keyword
   * @return 검색 정보가 담긴 20개의 목록(List<SearchsResDto>)
   */
  @Transactional(readOnly = true)
  public List<SearchsResDto> getSearchs(String keyword) {
    return musicsJpaRepository.findTop20ByTitleContainingOrArtistContaining(keyword, keyword)
        .stream().map(SearchsResDto::new).collect(Collectors.toList());
  }
}
