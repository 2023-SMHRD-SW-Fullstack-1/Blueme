package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueme.backend.model.entity.RecMusiclists;

public interface RecMusicListsJpaRepository extends JpaRepository<RecMusiclists, Long> {
  List<RecMusiclists> findByUserId(Long userId);

  RecMusiclists findFirstByUserIdOrderByCreatedAtDesc(Long userId);
}
