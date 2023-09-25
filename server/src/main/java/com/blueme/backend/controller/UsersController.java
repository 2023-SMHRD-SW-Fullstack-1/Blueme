package com.blueme.backend.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.usersdto.UserProfileDto;
import com.blueme.backend.dto.usersdto.UsersDeleteDto;
import com.blueme.backend.dto.usersdto.UsersRegisterDto;
import com.blueme.backend.dto.usersdto.UsersUpdateDto;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.security.config.PrincipalDetails;
import com.blueme.backend.service.UsersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁, 손지연
날짜(수정포함): 2023-09-16
설명: 회원관련 컨트롤러
*/

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin("http://172.30.1.13:3000")
public class UsersController {
	
	@Autowired
	private UsersService usersService;
	private final UsersJpaRepository usersJpaRepository;
	
	/**
	 *  post 유저 등록 ( 이메일 중복시 -1 반환 )
	 */
//	@PostMapping("/signup")
//	public Long registerNewUser(@RequestBody UsersRegisterDto requestDto) {
//		log.info("Starting user registration for email {}", requestDto.getEmail());
//		Long userId = usersService.save(requestDto);
//		log.info("User registration completed with ID {}", userId);
//		return userId;
//	}
	
	@PostMapping("/signup")
	public Long singup(@RequestBody UsersRegisterDto requestDto) throws Exception {
		log.info("Starting user registration for email {}", requestDto.getEmail());
		Long userId = usersService.signUp(requestDto);
		log.info("User registration completed with ID {}", userId);
		return userId;
	}
	
	/**
	 *  post 유저 로그인 ( 불일치시(+비활성화계정) -1 반환, 일치시 유저의고유ID반환 )
	 */
	//@PostMapping("/login")
	//public Long login(@RequestBody UsersLoginDto requestDto) {
	//	log.info("Starting user login for email {}", requestDto.getEmail());
	//	Long userId = usersService.login(requestDto);
	//	log.info("User registration login completed with ID {}", userId);
	//	return userId;
	//}
	
	/**
	 *  delete 유저 탈퇴 ( 실패시 -1 반환, 성공시 유저의고유ID반환 ), activeStatus 컬럼 "N"으로 변경
	 */
	@DeleteMapping("/deactivate")
	public Long deactivate(@RequestBody UsersDeleteDto requestDto) {
		Long userId = usersService.deactivate(requestDto);
		log.info("User delete completed with ID {}", userId);
		return userId;
	}
	
	/**
	 *  PATCH 유저 수정 
	 * @return 
	 * @throws IOException 
	 */
	@PatchMapping("/update")
	public Long update(@RequestBody UsersUpdateDto requestDto
			, @AuthenticationPrincipal PrincipalDetails principal) throws IOException{
		Optional<Users> user = usersJpaRepository.findById(principal.getId());
		log.info("Starting user update for id{}", principal.getId());
		Long userId = usersService.update(user,requestDto);
		return userId;
	}
	
	/**
	 *  get 마이페이지 정보
	 */
	@GetMapping("/Mypage")
	public ResponseEntity<List<UserProfileDto>> myProfile(@AuthenticationPrincipal PrincipalDetails principal) {
		List<UserProfileDto> users = usersService.myprofile(principal.getId());
		
		    if (users.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    } else {
		        return new ResponseEntity<>(users, HttpStatus.OK);
		    }
	}
}
