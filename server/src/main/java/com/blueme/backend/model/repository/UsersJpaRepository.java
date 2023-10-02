package com.blueme.backend.model.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blueme.backend.model.entity.Users;

/**
 * Users 테이블과 연결해주는 JPARepository
 * 
 * @author 손지연, 김혁
 * @version 1.0
 * @since 2023-09-07
 */
public interface UsersJpaRepository extends JpaRepository<Users, Long> {

     /**
      * 이메일에 해당하는 사용자를 반환
      *
      * @param email 이메일
      */
     Optional<Users> findByEmail(String email);

     /**
      * 이메일과 활성 상태에 해당하는 사용자를 반환
      *
      * @param email        이메일
      * @param activeStatus 활성 상태
      */
     Users findByEmailAndActiveStatus(String email, String activeStatus);

     /**
      * 리프레시 토큰에 해당하는 사용자를 반환
      *
      * @param refreshToken 리프레시 토큰
      */
     Optional<Users> findByRefreshToken(String refreshToken);

     /**
      * 플랫폼 유형과 소셜 ID에 해당하는 사용자를 반환
      *
      * @param platformType 플랫폼 유형
      * @param id           소셜 ID
      */
     Optional<Users> findByPlatformTypeAndSocialId(String platformType, String id);

     /**
      * 특정 날짜에 가입한 회원수를 조회하는 메서드입니다.
      * 
      * @param start 해당날짜 시작 (LocalDateTime)
      * @param end   해당날짜 끝 (LocalDateTime)
      * @return 가입한 회원수 (Long)
      */
     @Query("SELECT count(u) FROM Users u WHERE u.createdAt >= :start AND u.createdAt < :end")
     Long countUsersRegisteredBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
