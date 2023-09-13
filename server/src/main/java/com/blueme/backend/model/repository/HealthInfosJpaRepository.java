package com.blueme.backend.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueme.backend.model.entity.HealthInfos;

public interface HealthInfosJpaRepository extends JpaRepository<HealthInfos, Long> {
  HealthInfos findFirstByUserIdOrderByCreatedAtDesc(Long userId);

}
