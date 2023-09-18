package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueme.backend.model.entity.SavedMusiclists;

/**
 * SavedMusiclists DB 와 연결하여주는 JPARepository
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-07
 */
public interface SavedMusiclistsJpaRepository extends JpaRepository<SavedMusiclists, Long> {
  /**
   * 사용자ID를 기반으로 저장된 음악리스트 목록을 조회합니다.
   * 
   * @param userId 사용자ID
   * @return 저장한음악목록 (List<SavedMusiclists>)
   */
  List<SavedMusiclists> findByUserId(Long userId);
}
