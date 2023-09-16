package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.Searchs;

public interface SearchsJpaRepository extends JpaRepository<Searchs, Long> {
  List<Searchs> findByUserIdOrderByCreatedAtDesc(Long userId);

  Searchs findByUserIdAndMusicId(Long userId, Long musicId);
}
