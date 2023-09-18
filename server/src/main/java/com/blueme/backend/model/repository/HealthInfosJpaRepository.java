package com.blueme.backend.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

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

}
