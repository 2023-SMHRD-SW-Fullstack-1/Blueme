package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blueme.backend.model.entity.Musics;

/**
 * Musics 테이블과 연결해주는 JPA Repository
 * 
 * @author 김혁, 손지연
 * @version 1.0
 * @since 2023-09-07
 */
public interface MusicsJpaRepository extends JpaRepository<Musics, Long>, JpaSpecificationExecutor<Musics> {
  /**
   * 음악 조회시 제목필드에 키워드가 포함된 음악 목록을 조회하는 메서드
   * 
   * @param keyword 키워드 (String)
   * @return 음악 목록 List<Musics>
   */
  List<Musics> findByTitleContaining(String keyword);

  /**
   * 음악 조회시 제목 또는 가수명이 포함된 음악 목록을 조회하는 메서드
   * 
   * @param titleKeyword  제목 키워드 (String)
   * @param artistKeyword 가수 키워드 (String)
   * @return 음악 목록 List<Musics>
   */
  List<Musics> findTop20ByTitleContainingOrArtistContaining(String titleKeyword,
      String artistKeyword);

  /**
   * count개수의 랜덤한 음악 조회하는 메서드
   * 
   * @param count 조회할 음악 개수 (int)
   * @return 음악 목록 List<Musics>
   */
  @Query(value = "SELECT * FROM musics ORDER BY RAND() LIMIT :count", nativeQuery = true)
  List<Musics> findRandomMusics(@Param("count") int count);

  /**
   * 태그 필드에 keyword 를 가진 단어가 포함된 음악 리스트를 조회하는 메서드
   * 
   * @param keyword 태그 키워드 (String)
   * @return 음악 목록 List<Musics>
   */
  @Query("SELECT m FROM Musics m WHERE m.tag LIKE CONCAT('%', :keyword, '%') ORDER BY function('RAND')")
  List<Musics> findTop10ByTagContaining(@Param("keyword") String keyword, Pageable pageable);

  @Query(nativeQuery = true, value = "SELECT * FROM musics WHERE id IN :ids ORDER BY RAND() LIMIT 60")
  List<Musics> findRandomMusics(@Param("ids") List<Long> ids);

  /**
   * 아티스트 파일 경로에 해당하는 첫번째 음악을 조회하는 메서드
   *
   * @param artistFilePath 아티스트 파일 경로 (string)
   */
  Musics findTop1ByArtistFilePath(String artistFilePath);

  /**
   * 각 아티스트에 대한 고유한 음악 리스트를 그룹화하여 조회하는 메서드
   */
  @Query("SELECT m FROM Musics m GROUP BY m.artist")
  List<Musics> findByArtist();

  /**
   * 주어진 키워드가 포함된 고유한 아티스트를 찾아 그룹화하여 조회하는 메서드
   *
   * @param keyword 검색 키워드 (String)
   */
  @Query(value = "SELECT DISTINCT * FROM musics m WHERE m.artist LIKE %:keyword% GROUP BY m.artist", nativeQuery = true)
  List<Musics> findByDistinctArtist(@Param("keyword") String keyword);

  /**
   * 조회수 Top10 음악 정보 조회하는 메서드
   * 
   * @return 음악 목록 (List<Musics>)
   */
  @Query("SELECT m FROM Musics m ORDER BY m.hit DESC")
  List<Musics> findTop10OrderByHitDesc(Pageable pageable);

}
