package com.blueme.backend.model.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.Users;

@Repository
public interface UsersJpaRepository extends JpaRepository<Users, Long>{
	Users findByEmail(String email);
	
	Users findByEmailAndPasswordAndActiveStatus(String email, String password, String activeStatus);
	
}
