package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueme.backend.model.entity.ThemeMusiclists;

public interface ThemeMusiclistsJpaRepository extends JpaRepository<ThemeMusiclists, Long> {
  List<ThemeMusiclists> findByThemeId(Long id);
}
