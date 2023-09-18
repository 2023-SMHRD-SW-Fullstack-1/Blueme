package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueme.backend.model.entity.Searchs;

/**
 * Searchs 테이블과 연결해주는 JPARepository
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-08-13
 */
public interface SearchsJpaRepository extends JpaRepository<Searchs, Long> {

  /**
   * 사용자ID를 기반으로 최신순으로 검색 목록을 조회하는 메서드
   * 
   * @param userId 사용자ID (Long)
   * @return 검색목록 (List<Searchs>)
   */
  List<Searchs> findByUserIdOrderByCreatedAtDesc(Long userId);

  /**
   * 사용자ID와 음악ID를 기반으로 검색 조회하는 메서드
   * <p>
   * 최신검색 등록시 중복된 검색이 있는지 확인합니다.
   * </p>
   * 
   * @param userId  사용자ID (Long)
   * @param musicId 음악ID (Long)
   * @return 검색 (Searchs)
   */
  Searchs findByUserIdAndMusicId(Long userId, Long musicId);
}
