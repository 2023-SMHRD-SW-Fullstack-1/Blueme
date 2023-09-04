package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blueme.backend.service.MusicService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class MusicController {
	
	private final MusicService musicService;
	
	/**
	 *  post 다중 음악 등록
	 */
	@PostMapping("/admin/addmusic")
	public Long saveMusic(@RequestParam("files") MultipartFile[] files) {
		// @RequestBody는 application/json 형식의 HTTP 요청 본문만 처리 가능
		// 파일업로드를 위해서는 RequestParam 사용
		log.info("Starting save music");
		Long musicId = musicService.save(files);
		log.info("Recommendation music save completed", musicId);
		return musicId;
	}
	
}
