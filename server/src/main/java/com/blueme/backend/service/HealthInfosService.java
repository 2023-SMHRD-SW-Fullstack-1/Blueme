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

/*
작성자: 김혁
날짜(수정포함): 2023-09-11
설명: 워치로부터 받는 건강정보 서비스
*/

@RequiredArgsConstructor
@Service
public class HealthInfosService {

  private final HealthInfosJpaRepository healthInfosJpaRepository;
  private final UsersJpaRepository usersJpaRepository;

  /*
   * get 건강정보 조회
   */
  @Transactional(readOnly = true)
  public HealthInfoResDto getHealthInfo(Long userId) {
    HealthInfos healthInfo = healthInfosJpaRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
    return healthInfo == null ? null : new HealthInfoResDto(healthInfo);
  }

  /*
   * post 건강정보 등록
   */
  public Long saveHealthInfo(HealthInfoSaveReqDto request) {
    Users user = usersJpaRepository.findByEmail(request.getUserEmail())
        .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다 email = " + request.getUserEmail()));
    return healthInfosJpaRepository.save(request.toEntity(user)).getId();

  }

}
