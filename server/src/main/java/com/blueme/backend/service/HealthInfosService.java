package com.blueme.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blueme.backend.dto.healthinfodto.HealthInfoResDto;
import com.blueme.backend.dto.healthinfodto.HealthInfoSaveReqDto;
import com.blueme.backend.model.entity.HealthInfos;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.HealthInfosJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * HealrgInfosService는 건강정보 서비스 클래스입니다.
 * 이 클래스에서는 건강정보 조회 및 등록 기능을 제공합니다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-09
 */
@RequiredArgsConstructor
@Service
public class HealthInfosService {

  private final HealthInfosJpaRepository healthInfosJpaRepository;
  private final UsersJpaRepository usersJpaRepository;

  /**
   * 사용자 ID를 기반으로 건강정보를 조회합니다.
   *
   * @param userId 사용자 ID
   * @return 건강정보 응답 DTO (HealthInfoResDto)
   */
  @Transactional(readOnly = true)
  public HealthInfoResDto getHealthInfo(Long userId) {
    HealthInfos healthInfo = healthInfosJpaRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
    return healthInfo == null ? null : new HealthInfoResDto(healthInfo);
  }

  /**
   * 건강정보를 등록합니다.
   *
   * @param request 건강정보 저장 요청 DTO (HealthInfoSaveReqDto)
   * @return 저장된 건강정보의 ID (Long)
   */
  public Long saveHealthInfo(HealthInfoSaveReqDto request) {
    Users user = usersJpaRepository.findByEmail(request.getUserEmail())
        .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다 email = " + request.getUserEmail()));
    return healthInfosJpaRepository.save(request.toEntity(user)).getId();

  }

}
