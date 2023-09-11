package com.blueme.backend.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.HealthInfos;
import com.google.protobuf.Option;

@Repository
public interface HealthInfosJpaRepository extends JpaRepository<HealthInfos, Long>{
  Optional<HealthInfos> findFirstByUserIdOrderByCreatedAtDesc(Long userId);

}
