package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.musiclistsdto.RecMusicListSaveDto;
import com.blueme.backend.service.MusicListsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-07
설명: 음악리스트 관련 컨트롤러
*/

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/musiclist")
@CrossOrigin("http://localhost:3000")
public class MusicListsController {

	private final MusicListsService musicListsService;

	/**
	 * post 추천음악 리스트 등록
	 */
	@PostMapping("/recommendations")
	public Long saveMusicRecList(@RequestBody RecMusicListSaveDto requestDto) {
		log.info("Starting Recommendation MusicList save for userId = {}", requestDto.getUserId());
		return musicListsService.save(requestDto);
	}
}
