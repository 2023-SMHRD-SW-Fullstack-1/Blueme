package com.blueme.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.artistdto.ArtistInfoDto;
import com.blueme.backend.dto.artistdto.FavArtistReqDto;
import com.blueme.backend.service.ArtistsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자:  손지연
날짜(수정포함): 2023-09-13
설명: 회원가입 시 선호가수(아티스트) 관련 컨트롤러
*/

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArtistsController {

	private final ArtistsService artistsService;

	/**
	 * get 모든 가수(아티스트) 조회 (가수명, 가수이미지)
	 */
	@GetMapping("/Artistrecommend")
	public ResponseEntity<List<ArtistInfoDto>> artistrecommend() {
		log.info("Starting to get all artists!");
		List<ArtistInfoDto> artists = artistsService.getAllArtist();
		return new ResponseEntity<>(artists, HttpStatus.OK);
	}

	/**
	 * post 사용자가 선택한 가수(아티스트) 저장
	 */
	@PostMapping("/SaveFavArtist")
	public Long saveFavArtist(@RequestBody FavArtistReqDto requestDto) {
		log.info("Starting to save user's favorite artist");
		Long userId = artistsService.saveFavArtist(requestDto);
		log.info("ArtistController requestDto : {}", userId);
		return userId;
	}

	/**
	 * get 가수(아티스트) 검색
	 * 
	 * @return
	 */
	@GetMapping("/searchArtist/{keyword}")
	public List<ArtistInfoDto> searchArtist(@PathVariable("keyword") String keyword) {
		log.info("Starting search music info");
		List<ArtistInfoDto> artists = artistsService.searchArtist(keyword);
		return artists;
	}

	/**
	 * patch 선호가수 수정
	 */
	@PatchMapping("/updateFavArtist")
	public Long updateFavArtist(@RequestBody FavArtistReqDto requestDto) {
		log.info("Starting to update user's favorite artist");
		List<Long> artistId = requestDto.getArtistIds().stream().map(Long::parseLong).collect(Collectors.toList());
		Long userId = artistsService.updateFavArtist(Long.parseLong(requestDto.getFavChecklistId()),artistId);
		return userId;
	}

}
