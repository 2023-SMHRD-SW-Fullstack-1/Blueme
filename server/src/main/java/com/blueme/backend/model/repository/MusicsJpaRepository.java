package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blueme.backend.model.entity.Musics;

public interface MusicsJpaRepository extends JpaRepository<Musics, Long> {
  List<Musics> findByTitleContaining(String keyword);

  List<Musics> findTop20ByTitleContainingOrArtistContaining(String titleKeyword,
      String artistKeyword);

  // 카운트 개수에 해당하는 랜덤한 음악 배열구하기
  @Query(value = "SELECT * FROM musics ORDER BY RAND() LIMIT :count", nativeQuery = true)
  List<Musics> findRandomMusics(@Param("count") int count);
  
  Musics findTop1ByArtistFilePath(String artistFilePath);

  @Query("SELECT m FROM Musics m GROUP BY m.artist")
  List<Musics> findByArtist();

  @Query(value = "SELECT DISTINCT * FROM musics m WHERE m.artist LIKE %:keyword% GROUP BY m.artist", nativeQuery = true)
  List<Musics> findByDistinctArtist(@Param("keyword") String keyword);
}
