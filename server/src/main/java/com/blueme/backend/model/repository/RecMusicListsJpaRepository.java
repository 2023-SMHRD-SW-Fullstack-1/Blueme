package com.blueme.backend.model.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.RecMusiclists;

/**
 * RecMusicLists DB 와 연결하여 주는 JPA Repository
 * 
 * @author 김혁
 * @version 1.1
 * @since 2023-09-07
 */
@Repository
public interface RecMusicListsJpaRepository extends JpaRepository<RecMusiclists, Long> {

  /**
   * 사용자 ID를 기반으로 추천음악목록을 조회하는 메서드
   * 
   * @param userId 사용자ID
   * @return 추천음악목록 (List<RecMusiclist>)
   */
  List<RecMusiclists> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

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

  /**
   * 추천음악 최신 10개를 조회하는 메서드 (현재 사용자 제외)
   * 
   * @param userId 유저ID
   * @return 최신 추천음악 10개 목록 (List<RecMusiclists>)
   */
  @Query(value = "SELECT * FROM rec_musiclists WHERE user_id != :userId ORDER BY created_at DESC LIMIT 10", nativeQuery = true)
  List<RecMusiclists> findTop10ByUserIdNotOrderByCreatedAtDesc(@Param("userId") Long userId);

  /**
   * 특정 날짜의 GPT추천목록수를 조회하는 메서드입니다.
   * 
   * @param start 해당날짜 시작 (LocalDateTime)
   * @param end   해당날짜 끝 (LocalDateTime)
   * @return 추천받은 GPT 추천목록 수
   */
  @Query("SELECT count(r) FROM RecMusiclists r WHERE r.createdAt >= :start AND r.createdAt < :end")
  Long countRecMusiclistsRegisteredBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
