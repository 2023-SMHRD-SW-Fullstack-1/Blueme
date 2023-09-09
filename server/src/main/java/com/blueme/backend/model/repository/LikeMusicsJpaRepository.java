package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.LikeMusics;

@Repository
public interface LikeMusicsJpaRepository extends JpaRepository<LikeMusics, Long> {
  
  LikeMusics findByUserIdAndMusicId(Long userId, Long musicId);

  List<LikeMusics> findByUserId(Long userId);

}
