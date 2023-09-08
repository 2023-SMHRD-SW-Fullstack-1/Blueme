package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.Musics;

public interface MusicsJpaRepository extends JpaRepository<Musics, Long>{
  List<Musics> findByTitleContaining(String keyword);
}
