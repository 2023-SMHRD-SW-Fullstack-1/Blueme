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
		return ResponseEntity.ok().body(artists);
	}

	/**
	 * post 사용자가 선택한 가수(아티스트) 저장
	 */
	@PostMapping("/SaveFavArtist")
	public ResponseEntity<Long> saveFavArtist(@RequestBody FavArtistReqDto requestDto) {
		log.info("Starting to save user's favorite artist!");
		Long userId = artistsService.saveFavArtist(requestDto);
		log.info("Ending to save user's favorite artist for userId : {}", userId);
		if(userId == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(userId);
		}
	}

	/**
	 * get 가수(아티스트) 검색
	 * 
	 * @return
	 */
	@GetMapping("/searchArtist/{keyword}")
	public ResponseEntity<List<ArtistInfoDto>> searchArtist(@PathVariable("keyword") String keyword) {
		log.info("Starting search music info for keyword = {}", keyword);
		List<ArtistInfoDto> artists = artistsService.searchArtist(keyword);
		log.info("Finished search music info for keyword = {}", keyword);
		if(artists.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok().body(artists);
		}
	}

	/**
	 * patch 선호가수 수정
	 */
	@PatchMapping("/updateFavArtist")
	public ResponseEntity<Long> updateFavArtist(@RequestBody FavArtistReqDto requestDto) {
		log.info("Starting to update user's favorite artist");
		List<Long> artistId = requestDto.getArtistIds().stream()
				.map(Long::parseLong).collect(Collectors.toList());
		Long userId = artistsService.updateFavArtist(Long.parseLong(requestDto.getFavChecklistId()), artistId);
		if (userId == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok().body(userId);
		}
	}

}
