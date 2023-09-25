package com.blueme.backend.security.login.service;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.UsersJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {
	
	private final UsersJpaRepository usersJpaRepository;
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		log.info("loadUserByUsername start ...");
		Users user = usersJpaRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다."));
		
		return User.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.roles(user.getRole().name())
				.build();
	}	

}
