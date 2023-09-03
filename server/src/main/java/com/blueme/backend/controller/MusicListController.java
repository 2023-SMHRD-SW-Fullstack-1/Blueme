package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.musiclistdto.RecMusicListSaveDto;
import com.blueme.backend.service.MusicListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MusicListController {
	
	private final MusicListService musicListService;
	
	/**
	 *  post 추천음악 리스트 등록
	 */
	@PostMapping("/musiclist/recommendations")
	public Long saveMusicRecList(@RequestBody RecMusicListSaveDto requestDto) {
		log.info("Starting Recommendation MusicList save for userId = {}", requestDto.getUserId());
		Long reqMusicListId = musicListService.save(requestDto);
		log.info("Recommendation MusicList save completed with reqMusicListId = {}", reqMusicListId);
		return reqMusicListId;
	}
	
	
}
