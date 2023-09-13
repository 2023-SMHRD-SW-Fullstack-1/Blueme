package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.PlayedMusics;

public interface PlayedMusicsJpaRepository extends JpaRepository<PlayedMusics, Long> {

  PlayedMusics findByUserIdAndMusicId(Long UserId, Long MusicId);

  // JPQL 사용
  @Query("SELECT DISTINCT pm.music FROM PlayedMusics pm WHERE pm.user.id = ?1 ORDER BY pm.createdAt DESC")
  List<Musics> findDistinctMusicByUserId(Long userId);

}
