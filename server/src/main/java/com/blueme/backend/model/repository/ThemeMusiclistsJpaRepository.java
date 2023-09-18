package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueme.backend.model.entity.ThemeMusiclists;

/**
 * ThemeMusiclists 테이블과 연결해주는 JPARepository
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-07
 */
public interface ThemeMusiclistsJpaRepository extends JpaRepository<ThemeMusiclists, Long> {

  /**
   * 테마ID를 기반으로 테마 목록을 조회합니다.
   * 
   * @param id
   * @return 테마음악리스트 목록 (List<ThemeMusiclists>)
   */
  List<ThemeMusiclists> findByThemeId(Long id);
}
