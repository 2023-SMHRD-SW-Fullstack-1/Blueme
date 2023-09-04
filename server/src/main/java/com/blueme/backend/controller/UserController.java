package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.userdto.UserLoginDto;
import com.blueme.backend.dto.userdto.UserRegisterDto;
import com.blueme.backend.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
	
	private final UserService userService;
	
	/**
	 *  post 유저 등록 ( 이메일 중복시 -1 반환 )
	 */
	@PostMapping("/user/signup")
	public Long registerNewUser(@RequestBody UserRegisterDto requestDto) {
		log.info("Starting user registration for {}", requestDto.getEmail());
		Long userId = userService.save(requestDto);
		log.info("User registration completed with ID {}", userId);
		return userId;
	}
	
	/**
	 *  post 유저 로그인 확인 ( 불일치시 -1 반환, 일치시 유저의ID반환 )
	 */
	@PostMapping("/user/login")
	public Long login(@RequestBody UserLoginDto requestDto) {
		log.info("Starting user login for {}", requestDto.getEmail());
		Long userId = userService.login(requestDto);
		log.info("User registration login with ID {}", userId);
		return userId;
	}
	
	
}
