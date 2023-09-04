package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.musicdto.MusicSaveDto;
import com.blueme.backend.service.MusicService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MusicController {
	
	private final MusicService musicService;
	
	/**
	 *  post 다중 음악 등록
	 */
	@PostMapping("/admin/addmusic")
	public Long saveMusic(@RequestBody MusicSaveDto requestDto) {
		log.info("Starting save music for userId = {}");
		Long musicId = musicService.save(requestDto);
		log.info("Recommendation music save completed with musicId = {}", musicId);
		return musicId;
	}
	
}
