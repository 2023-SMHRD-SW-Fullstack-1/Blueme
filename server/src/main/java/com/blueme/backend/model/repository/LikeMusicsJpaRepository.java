package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueme.backend.model.entity.LikeMusics;

/**
 * LikeMusics 테이블과 연결해주는 JPA Repositories
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-07
 */
public interface LikeMusicsJpaRepository extends JpaRepository<LikeMusics, Long> {

  /**
   * 사용자ID와 음악ID를 기반으로 좋아요한 음악을 조회하는 메서드
   * 
   * @param userId  사용자ID
   * @param musicId 음악ID
   * @return 저장한음악 (LikeMusics), 없으면 null 반환
   */
  LikeMusics findByUserIdAndMusicId(Long userId, Long musicId);

  /**
   * 사용자ID를 기반으로 좋아요한 음악 조회하는 메서드
   * 
   * @param userId 사용자ID
   * @return 좋아요한 음악 목록 반환 (List<LikeMusics>)
   */
  List<LikeMusics> findByUserId(Long userId);
}
