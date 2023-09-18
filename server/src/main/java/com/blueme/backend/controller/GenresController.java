package com.blueme.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

/*
작성자:  손지연
날짜(수정포함): 2023-09-13
설명: 회원가입 시 선호장르 관련 컨트롤러
*/

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
public class GenresController {
	
	private final GenresService genresService;
	
	/**
	 * 	get 모든 장르 조회 (장르ID, 장르명, 장르이미지)
	 */
	@GetMapping("/SelectGenre")
	public ResponseEntity<List<GenreInfoDto>> getAllGenre(){
		log.info("Starting to get all genres!");
		List<GenreInfoDto> genres = genresService.getAllGenre();
		return new ResponseEntity<>(genres, HttpStatus.OK);
	}
	
	/**
	 * 	post 사용자가 선택한 장르 저장
	 */
	@PostMapping("/SaveFavGenre")
	public Long saveFavGenre(@RequestBody FavGenreReqDto requestDto) {
		log.info("Starting to save user's favorite genre");
		Long userId = genresService.saveFavGenre(requestDto);
		log.info("GenreController requestDto : {}",userId);
		return userId;
		
	}
	/**
	 *	patch 선호장르 수정
	 */
	@PatchMapping("/updateFavGenre")
	public Long updateFavGenre(@RequestBody FavGenreReqDto requestDto) {
		log.info("Starting to update user's favorite genre");
		List<Long> genreIds = requestDto.getGenreIds().stream().map(Long::parseLong).collect(Collectors.toList());
		Long userId = genresService.updateFavGenre(Long.parseLong(requestDto.getFavChecklistId()), genreIds);
		log.info("GenreController requestDto : {}", userId);
		return userId;
	}
}
