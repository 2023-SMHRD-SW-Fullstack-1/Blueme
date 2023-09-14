package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.Musics;

public interface MusicsJpaRepository extends JpaRepository<Musics, Long>{
  List<Musics> findByTitleContaining(String keyword);
  
  Musics findByArtistFilePath(String artistFilePath);
  
  @Query("SELECT m FROM Musics m GROUP BY m.artist")
  List<Musics> findByArtist();
}
