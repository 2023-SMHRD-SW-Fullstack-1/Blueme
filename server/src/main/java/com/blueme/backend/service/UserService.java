package com.blueme.backend.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.blueme.backend.dto.userdto.UserLoginDto;
import com.blueme.backend.dto.userdto.UserRegisterDto;
import com.blueme.backend.model.entity.User;
import com.blueme.backend.model.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
	private final UserJpaRepository userJpaRepository;
	
	/**
	 *  유저 등록
	 */
	@Transactional
	public Long save(UserRegisterDto requestDto) {
		log.info("userService method save start...");
		User user = userJpaRepository.findByEmail(requestDto.getEmail());
		return (user == null) ? userJpaRepository.save(requestDto.toEntity()).getId() : -1L;
	}
	
	/**
	 *  유저 로그인확인
	 */
	@Transactional
	public Long login(UserLoginDto requestDto) {
		log.info("userService method login start...");
		User user = userJpaRepository.findByEmailAndPassword(requestDto.getEmail(), requestDto.getPassword());
		return (user == null) ? -1L : user.getId();
		
	}

}
