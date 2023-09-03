package com.blueme.backend.web;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.service.UserService;
import com.blueme.backend.web.dto.userdto.UserRegisterDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
	
	private final UserService userService;
	
	/**
	 *  post 유저 등록
	 */
	@PostMapping("/user/signup")
	public Long registerNewUser(@RequestBody UserRegisterDto requestDto) {
		log.info("Starting user registration for ", requestDto.getEmail());
		Long userId = userService.save(requestDto);
		log.info("User registration completed with ID ", userId);
		return userId;
	}
	
}
