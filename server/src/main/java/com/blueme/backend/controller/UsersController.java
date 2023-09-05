package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.usersdto.UsersDeleteDto;
import com.blueme.backend.dto.usersdto.UsersLoginDto;
import com.blueme.backend.dto.usersdto.UsersRegisterDto;
import com.blueme.backend.service.UsersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UsersController {
	
	private final UsersService usersService;
	
	/**
	 *  post 유저 등록 ( 이메일 중복시 -1 반환 )
	 */
	@PostMapping("/signup")
	public Long registerNewUser(@RequestBody UsersRegisterDto requestDto) {
		log.info("Starting user registration for email {}", requestDto.getEmail());
		Long userId = usersService.save(requestDto);
		log.info("User registration completed with ID {}", userId);
		return userId;
	}
	
	/**
	 *  post 유저 로그인 ( 불일치시(+비활성화계정) -1 반환, 일치시 유저의고유ID반환 )
	 */
	@PostMapping("/login")
	public Long login(@RequestBody UsersLoginDto requestDto) {
		log.info("Starting user login for email {}", requestDto.getEmail());
		Long userId = usersService.login(requestDto);
		log.info("User registration login completed with ID {}", userId);
		return userId;
	}
	
	/**
	 *  delete 유저 탈퇴 ( 실패시 -1 반환, 성공시 유저의고유ID반환 ), activeStatus 컬럼 "N"으로 변경
	 */
	@DeleteMapping("/deactivate")
	public Long delete(@RequestBody UsersDeleteDto requestDto) {
		log.info("Starting user delete for email{}", requestDto.getEmail());
		Long userId = usersService.deactivate(requestDto);
		log.info("User delete completed with ID {}", userId);
		return userId;
	}
	
	
}
