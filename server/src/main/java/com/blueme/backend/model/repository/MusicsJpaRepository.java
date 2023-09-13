package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blueme.backend.model.entity.Musics;

public interface MusicsJpaRepository extends JpaRepository<Musics, Long> {
  List<Musics> findByTitleContaining(String keyword);

  // 카운트 개수에 해당하는 랜덤한 음악 배열구하기
  @Query(value = "SELECT * FROM musics ORDER BY RAND() LIMIT :count", nativeQuery = true)
  List<Musics> findRandomMusics(@Param("count") int count);

}
