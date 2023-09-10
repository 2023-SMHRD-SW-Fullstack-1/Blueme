package com.blueme.backend.service;

import org.springframework.stereotype.Service;

import com.blueme.backend.dto.healthinfodto.HealthInfoSaveReqDto;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.HealthInfosJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-10
설명: 워치로부터 받는 건강정보 서비스
*/

@Slf4j
@RequiredArgsConstructor
@Service
public class HealthInfosService {
  
  private final HealthInfosJpaRepository healthInfosJpaRepository;
  private final UsersJpaRepository usersJpaRepository;

  /*
   * post 건강정보 등록
   */
  public Long saveHealthInfo(HealthInfoSaveReqDto request){

    Users user = usersJpaRepository.findById(Long.parseLong(request.getUser_id()))
      .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    return healthInfosJpaRepository.save(request.toEntity(user)).getId();

  }

}
