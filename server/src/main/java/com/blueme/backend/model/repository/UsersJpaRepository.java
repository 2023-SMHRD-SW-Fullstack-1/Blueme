package com.blueme.backend.model.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.Users;

@Repository
public interface UsersJpaRepository extends JpaRepository<Users, Long>{
	
	Optional<Users> findByEmail(String email);
	
//	Optional<Users> findByNickname(String nickname);
	
	Users findByEmailAndPasswordAndActiveStatus(String email, String password, String activeStatus);
	
	Optional<Users> findByRefreshToken(String refreshToken);
	
}
