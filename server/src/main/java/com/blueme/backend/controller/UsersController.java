package com.blueme.backend.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

/**
 * UsersController는 회원 관련 컨트롤러 클래스입니다.
 * <p>
 * 이 클래스는 회원 등록, 회원 탈퇴, 회원 정보 수정, 마이페이지 정보 조회 기능을 처리합니다.
 * </p>
 * 
 * @author 김혁, 손지연
 * @version 1.0
 * @since 2023-09-27
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UsersController {
	
	@Autowired
	private UsersService usersService;
	private final UsersJpaRepository usersJpaRepository;
	
	/**
	 * 회원 가입을 처리하기 위한 POST 메서드입니다.
	 * 
	 * @param requestDto 사용자 등록 요청 DTO (UsersRegisterDto)
	 * @return userId를 포함한 ResponseEntity, 실패 시 메시지와 함께 INTERNAL_SERVER_ERROR 상태의 ResponseEntity 반환
	 * 
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> singup(@RequestBody UsersRegisterDto requestDto){
		log.info("Starting user registration for email {}", requestDto.getEmail());
		Long userId = usersService.signUp(requestDto);
		log.info("User registration completed with ID {}", userId);
		if (userId == null) {
			return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration failed due to server error.");
		} else {
			return ResponseEntity.ok().body(userId);
		}
	}
	
	/**
	 * 회원 탈퇴를 처리하기 위한 DELETE 메서드입니다.
	 * 
	 * @param requestDto 사용자 탈퇴 요청 DTO (UserDeleteDto)
	 * @return 성공 시 : 회원 고유 ID를 포함한 ResponseEntity, 실패 시 -1을 포함한 ResponseEntity 반환
	 */
	@DeleteMapping("/deactivate")
	public ResponseEntity<Long> deactivate(@RequestBody UsersDeleteDto requestDto) {
		Long userId = usersService.deactivate(requestDto);
		log.info("User delete completed with ID {}", userId);
		return ResponseEntity.ok().body(userId);
	}
	
	/**
	 * 회원 정보를 수정하기 위한 PATCH 메서드입니다.
	 * 
	 * @param requestDto 사용자 정보 수정 요청 DTO (UsersUpdateDto)
	 * @param principal 현재 인증된 사용자의 정보 (PrincipalDetails)
	 * @return 성공 시, 수정된 횐원의 고유 ID를 포함한 ResponseEntity 반환
	 * @throws IOException 입출력 처리 중 발생할 수 있는 예외
	 */
	@PatchMapping("/update")
	public ResponseEntity<Long> update(@RequestBody UsersUpdateDto requestDto
			, @AuthenticationPrincipal PrincipalDetails principal) throws IOException{
		Optional<Users> user = usersJpaRepository.findById(principal.getId());
		log.info("Starting user update for ID : {}", principal.getId());
		Long userId = usersService.update(user,requestDto);
		return ResponseEntity.ok().body(userId);
	}
	
	/**
	 * 마이페이지 정보를 가져오기 위한 GET 메서드입니다.
	 * 
	 * @param principal 현재 인증된 사용자의 정보
	 * @return 성공 시 UserProfileDto 목록을 포함한 ResposneEntity 반환,
	 *         실패 시 UNAUTHORIZED 상태의 ResponseEntity 반환,
	 * 		   데이터가 없는 경우 NO_CONTENT 상태의 ResponseEntity 반환 
	 */
	@GetMapping("/Mypage")
	public ResponseEntity<List<UserProfileDto>> myProfile(@AuthenticationPrincipal PrincipalDetails principal) {
		if (principal == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		List<UserProfileDto> users = usersService.myprofile(principal.getId());
		    if (users.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    } else {
		        return new ResponseEntity<>(users, HttpStatus.OK);
		    }
	}
}
