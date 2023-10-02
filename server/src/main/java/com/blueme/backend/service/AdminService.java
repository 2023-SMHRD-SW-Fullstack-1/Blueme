package com.blueme.backend.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blueme.backend.dto.admindto.MusicInfoTop10ResDto;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * ADMIN 관련 서비스 입니다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-10-02
 */
@RequiredArgsConstructor
@Service
public class AdminService {

  private final MusicsJpaRepository musicsJpaRepository;
  private final UsersJpaRepository usersJpaRepository;

  /**
   * 조회수 상위랭크 10개 조회하는 메서드
   * 
   * @return 음악정보 목록 (List<MusicInfoTop10ResDto>)
   */
  @Transactional(readOnly = true)
  public List<MusicInfoTop10ResDto> getTop10MusicInfo() {
    PageRequest pageRequest = PageRequest.of(0, 10);
    return musicsJpaRepository.findTop10OrderByHitDesc(pageRequest).stream().map(MusicInfoTop10ResDto::new)
        .collect(Collectors.toList());
  }

  /**
   * date기반으로 그 날의 새로운 회원수를 조회하는 메서드입니다.
   * 
   * @return 새로 가입한 회원수 (Long)
   */
  @Transactional(readOnly = true)
  public Long getNewClients(String date) {
    Instant instant = Instant.parse(date); // Instant 객체로 변환
    ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
    LocalDateTime startDateTime = LocalDateTime.ofInstant(instant, seoulZoneId);
    LocalDateTime endDateTime = startDateTime.plusDays(1);

    Long cnt = usersJpaRepository.countUsersRegisteredBetween(startDateTime.toLocalDate().atStartOfDay(),
        endDateTime.toLocalDate().atStartOfDay());
    return cnt;
  }
}
