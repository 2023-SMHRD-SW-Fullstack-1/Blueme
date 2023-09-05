package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blueme.backend.service.MusicsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
@CrossOrigin("http://localhost:3000")
public class MusicsController {
	
	private final MusicsService musicsService;
	
	/**
	 *  post 다중 음악 등록
	 */
	@PostMapping("/admin/addmusic")
	public Long saveMusic(@RequestParam("files") MultipartFile[] files) {
		// @RequestBody는 application/json 형식의 HTTP 요청 본문만 처리 가능
		// 파일업로드를 위해서는 RequestParam 사용
		log.info("Starting save music");
		Long musicId = musicsService.save(files);
		log.info("Recommendation music save completed", musicId);
		return musicId;
	}
	
}
