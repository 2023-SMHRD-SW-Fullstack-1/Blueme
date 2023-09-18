package com.blueme.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueme.backend.model.entity.Themes;

/**
 * Themes 테이블과 연결해주는 JPA Repository
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-07
 */
public interface ThemesJpaRepository extends JpaRepository<Themes, Long> {

}
