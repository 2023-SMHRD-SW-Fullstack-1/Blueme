package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueme.backend.model.entity.RecMusiclists;

/**
 * RecMusicLists DB 와 연결하여 주는 JPA Repository
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-07
 */
public interface RecMusicListsJpaRepository extends JpaRepository<RecMusiclists, Long> {

  /**
   * 사용자 ID를 기반으로 추천음악목록을 조회하는 메서드
   * 
   * @param userId 사용자ID
   * @return 추천음악목록 (List<RecMusiclist>)
   */
  List<RecMusiclists> findByUserId(Long userId);

  /**
   * 사용자ID를 기반으로 최신 추천음악 하나를 조회하는 메서드
   * 
   * @param userId 사용자ID
   * @return 추천음악 하나 (RecMusiclist), 없으면 null 반환
   */
  RecMusiclists findFirstByUserIdOrderByCreatedAtDesc(Long userId);

  /**
   * 추천음악 최신 10개를 조회하는 메서드
   * 
   * @return 최신 추천음악 10개 목록 (List<RecMusiclist>)
   */
  List<RecMusiclists> findTop10ByOrderByCreatedAtDesc();
}
