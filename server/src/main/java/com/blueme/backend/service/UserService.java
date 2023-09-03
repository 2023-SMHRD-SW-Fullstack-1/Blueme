package com.blueme.backend.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.blueme.backend.model.entity.User;
import com.blueme.backend.model.repository.UserJpaRepository;
import com.blueme.backend.web.dto.userdto.UserRegisterDto;

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
}
