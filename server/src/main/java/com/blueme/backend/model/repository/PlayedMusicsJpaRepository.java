package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.PlayedMusics;
import com.blueme.backend.model.entity.Users;

/**
 * PlayedMusics 테이블과 연결해주는 JPA Repository
 *
 * @author 김혁
 * @version 1.0
 * @since 2023-09-11
 */
public interface PlayedMusicsJpaRepository extends JpaRepository<PlayedMusics, Long> {
  /**
   * 사용자 ID와 음악 ID를 기반으로 특정 재생 음악 정보를 조회합니다.
   *
   * @param UserId  사용자 ID
   * @param MusicId 음악 ID
   * @return 일치하는 재생 음악 정보. 일치하는 정보가 없을 경우 null
   */
  PlayedMusics findByUserIdAndMusicId(Long UserId, Long MusicId);

  /**
   * 특정 사용자가 재생한 고유의 음악 목록을 최신 순으로 반환합니다. (20개 제한)
   *
   * @param userId 사용자 ID
   * @return 해당 사용자가 재생한 고유의 음악 목록. 최신 순으로 정렬됨
   */
  @Query("SELECT DISTINCT pm.music FROM PlayedMusics pm WHERE pm.user.id = ?1 ORDER BY pm.createdAt DESC")
  List<Musics> findDistinctMusicByUserId(Long userId, Pageable pageable);

  /**
   * 사용자ID와 음악ID를 기반으로 재생된 음악 정보를 조회합니다.
   *
   * @param user  Users 객체
   * @param music Musics 객체
   * @return 일치하는 재생된 음악 정보. 일치하는 정보가 없을 경우 null
   */
  @Query("SELECT DISTINCT pm FROM PlayedMusics pm WHERE pm.user = :user AND pm.music = :music")
  PlayedMusics findDistinctByUserAndMusic(@Param("user") Users user, @Param("music") Musics music);

}
