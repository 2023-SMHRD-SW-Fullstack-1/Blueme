package com.blueme.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueme.backend.model.entity.ThemeTags;

/**
 * Theme_tags 테이블과 연결해주는 JPARepository
 * 
 */
public interface ThemeTagsJpaRepository extends JpaRepository<ThemeTags, Long> {

}
