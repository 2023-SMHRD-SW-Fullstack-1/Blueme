package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.RecMusiclists;

@Repository
public interface RecMusicListsJpaRepository extends JpaRepository<RecMusiclists, Long> {
  List<RecMusiclists> findByUserId(Long userId);
}
