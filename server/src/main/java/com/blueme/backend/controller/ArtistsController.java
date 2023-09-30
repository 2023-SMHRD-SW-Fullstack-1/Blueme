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

/**
 * ArtistsController는 선호가수(아티스트) 관련 컨트롤러 클래스입니다.
 * <p>
 * 이 클래스는 가수 추천, 선호가수 저장, 가수 검색, 선호가수 수정 기능을 처리합니다.
 * </p>
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-27
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArtistsController {

	private final ArtistsService artistsService;


	/**
	 * 가수 정보 조회를 위한 GET 메서드입니다.
	 * 
	 * @return 가수 정보 목록 (List<ArtistInfoDto>)
	 */
	@GetMapping("/Artistrecommend")
	public ResponseEntity<List<ArtistInfoDto>> artistrecommend() {
		log.info("Starting to get all artists!");
		List<ArtistInfoDto> artists = artistsService.getAllArtist();
		return ResponseEntity.ok().body(artists);
	}

	/**
	 * 사용자가 선택한 가수(선호가수)를 저장하기 위한 POST 메서드입니다.
	 * 
	 * @param requestDto 사용자가 선택한 가수 요청 DTO (FavArtistReqDto)
	 * @return 사용자 ID (Long)
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
	 * 가수 검색을 위한 GET 메서드입니다.
	 * 
	 * @param keyword 검색 키워드
	 * @return 가수 정보 목록 (List<ArtistInfoDto>)
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
	 * 사용자가 선택한 가수(선호가수)를 수정하기 위한 PATCH 메서드입니다.
	 * 
	 * @param requestDto 사용자가 새롭게 선택한 가수 요청 DTO (FavArtistReqDto)
	 * @return 사용자 ID (Long)
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
