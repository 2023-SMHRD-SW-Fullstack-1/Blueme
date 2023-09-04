package com.blueme.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
	
	User findByEmailAndPassword(String email, String password);
	
}
