package com.blueme.backend.model.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blueme.backend.model.entity.HealthInfos;

/**
 * HealthInfos 테이블과 연결해주는 JPA Repositories
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-07
 */
public interface HealthInfosJpaRepository extends JpaRepository<HealthInfos, Long> {

  /**
   * 사용자ID를 기반으로 최신 한개의 건강정보 조회를 수행하는 메서드
   * 
   * @param userId 사용자ID
   * @return 건강정보 (HealthInfos) , 없으면 null반환
   */
  HealthInfos findFirstByUserIdOrderByCreatedAtDesc(Long userId);

  /**
   * 특정 날짜에 건강정보수를 조회하는 메서드입니다.
   * 
   * @param start 해당날짜 시작 (LocalDateTime)
   * @param end   해당날짜 끝 (LocalDateTime)
   * @return 새로등록한 건강정보 수 (Long)
   */
  @Query("SELECT count(h) FROM HealthInfos h WHERE h.createdAt >= :start AND h.createdAt < :end")
  Long countHealthInfosRegisteredBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
