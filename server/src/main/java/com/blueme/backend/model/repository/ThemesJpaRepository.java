package com.blueme.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueme.backend.model.entity.Themes;

public interface ThemesJpaRepository extends JpaRepository<Themes, Long> {

}
