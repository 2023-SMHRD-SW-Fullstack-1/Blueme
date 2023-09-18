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

/**
 * MusicListsController는 음악목록 컨트롤러 클래스입니다.
 * 이 클래스에서는 REST API 엔드포인트를 제공하여 추천음악 목록 등록 기능을 처리합니다.
 * RecMusicList컨트롤러 로의 이전으로 사용하지 않습니다.
 *
 * @author 김혁
 * @version 1.0
 * @since 2023-09-07
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/musiclist")
@CrossOrigin("http://localhost:3000")
public class MusicListsController {

	private final MusicListsService musicListsService;

	/**
	 * 추천음악 리스트 등록을 위한 POST 메서드입니다.
	 * 현재 RecMusics컨트롤러 로 이전으로 사용하지 않습니다.
	 *
	 * @param requestDto 추천음악 정보 저장DTO (RecMusicListSaveDto)
	 * @return 저장된 음악리스트의 ID (Long)
	 */
	@PostMapping("/recommendations")
	public Long saveMusicRecList(@RequestBody RecMusicListSaveDto requestDto) {
		log.info("Starting Recommendation MusicList save for userId = {}", requestDto.getUserId());
		return musicListsService.save(requestDto);
	}
}
