package com.blueme.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.blueme.backend.dto.healthinfodto.HealthInfoResDto;
import com.blueme.backend.dto.healthinfodto.HealthInfoSaveReqDto;
import com.blueme.backend.model.entity.HealthInfos;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.HealthInfosJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-11
설명: 워치로부터 받는 건강정보 서비스
*/

@Slf4j
@RequiredArgsConstructor
@Service
public class HealthInfosService {
  
  private final HealthInfosJpaRepository healthInfosJpaRepository;
  private final UsersJpaRepository usersJpaRepository;

  /*
   * get 건강정보 조회
   */
  public HealthInfoResDto getHealthInfo(Long userId){
    Optional<HealthInfos> healthInfo = healthInfosJpaRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
    return healthInfo.isPresent() ? new HealthInfoResDto(healthInfo.get()) : null;
  }

  /*
   * post 건강정보 등록
   */
  public Long saveHealthInfo(HealthInfoSaveReqDto request){

    Users user = usersJpaRepository.findByEmail(request.getUserEmail())
      .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다 "));

    return healthInfosJpaRepository.save(request.toEntity(user)).getId();

  }

}
