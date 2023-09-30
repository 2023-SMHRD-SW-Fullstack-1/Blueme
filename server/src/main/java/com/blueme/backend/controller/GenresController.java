package com.blueme.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.genredto.FavGenreReqDto;
import com.blueme.backend.dto.genredto.GenreInfoDto;
import com.blueme.backend.service.GenresService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * GenresController는 장르 관련 컨트롤러 클래스입니다.
 * <p>
 * 이 클래스는 장르 추천, 선호장르 저장, 장르 검색, 선호장르 수정 기능을 처리합니다.
 * </p>
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-27
 */

@Slf4j
@RequiredArgsConstructor
@RestController
public class GenresController {
	
	private final GenresService genresService;
	
	/**
	 * 장르 정보 조회를 위한 GET 메서드입니다.
	 * 
	 * @return 장르 정보 목록 (List<GenreInfoDto>)
	 */
	@GetMapping("/SelectGenre")
	public ResponseEntity<List<GenreInfoDto>> getAllGenre(){
		log.info("Starting to get all genres!");
		List<GenreInfoDto> genres = genresService.getAllGenre();
		return ResponseEntity.ok().body(genres);
	}
	
	/**
	 * 사용자가 선택한 장르(선호장르)를 저장하기 위한 POST 메서드입니다.
	 * 
	 * @param requestDto 사용자가 선택한 장르 요청 DTO (FavGenreReqDto)
	 * @return 사용자 ID (Long)
	 */
	@PostMapping("/SaveFavGenre")
	public ResponseEntity<Long> saveFavGenre(@RequestBody FavGenreReqDto requestDto) {
		log.info("Starting to save user's favorite genre!");
		Long userId = genresService.saveFavGenre(requestDto);
		log.info("Ending to save user's favorite genre for userId : {}",userId);
		if (userId == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(userId);
		}
		
	}

	/**
	 * 사용자가 선택한 장르(선호장르)를 수정하기 위한 메서드입니다.
	 * 
	 * @param requestDto 사용자가 새롭게 선택한 장르 요청 DTO (FavGenreReqDto)
	 * @return 사용자 ID (Long)
	 */
	@PatchMapping("/updateFavGenre")
	public ResponseEntity<Long> updateFavGenre(@RequestBody FavGenreReqDto requestDto) {
		log.info("Starting to update user's favorite genre");
		List<Long> genreIds = requestDto.getGenreIds().stream().map(Long::parseLong).collect(Collectors.toList());
		Long userId = genresService.updateFavGenre(Long.parseLong(requestDto.getFavChecklistId()), genreIds);
		if (userId == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok().body(userId);
		}
	}
}
