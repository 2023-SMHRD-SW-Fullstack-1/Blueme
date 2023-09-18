package com.blueme.backend.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueme.backend.model.entity.Users;

/**
 * Users 테이블과 연결해주는 JPARepository
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-07
 */
public interface UsersJpaRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByEmail(String email);

	Optional<Users> findByNickname(String nickname);

	// Users findByEmailAndPasswordAndActiveStatus(String email, String password,
	// String activeStatus);

	Users findByEmailAndActiveStatus(String email, String activeStatus);

	Optional<Users> findByRefreshToken(String refreshToken);

	Optional<Users> findByPlatformTypeAndSocialId(String platformType, String id);

}
