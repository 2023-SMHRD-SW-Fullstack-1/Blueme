package com.blueme.backend.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.blueme.backend.dto.musicdto.MusicInfoResDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.service.MusicsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/*
작성자: 김혁
날짜(수정포함): 2023-09-09
설명: 음악 관련 컨트롤러
*/


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
		log.info("Recommendation music save completed with music ID = {}", musicId);
		return musicId;
	}

	/**
   *  get 음악 페이징 조회
	 */
	@GetMapping("/page")
	public Page<Musics> getMusic(Pageable pageable){
		log.info("Starting paging music");
		return musicsService.findAll(pageable);
	}

	/*
	 * 음악 검색
	 */
	@GetMapping("/search")
	public List<Musics> searchMusic(@RequestParam("keyword") String keyword) {
		log.info("Starting search music info");
		// 테스트
		List<Musics> musics = musicsService.searchMusic(keyword);
		System.out.println(musics.size());
		return musics;
		//return musicsService.searchMusic(keyword);
	}
	/*
	 * get 음악스트리밍 테스트 (기존방식과 차이 없으므로 삭제)
	 */
	// @GetMapping("/streaming/{id}")
	// public StreamingResponseBody streamMusic(@PathVariable("id") String id) {
  //   log.info("Starting streaming music for ID = {}", id);
  //   return musicsService.streamMusic(id);
  // }
	
	/**
	 *  get musicId에 해당하는 음악 데이터 조회 (HTTP Range Request) , 음악 조회수 증가
	 */
	@GetMapping("/{id}")
	public ResponseEntity<InputStreamResource> streamAudio(@PathVariable("id") String id, 
			@RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {    
			log.info("Starting send music data");
			return musicsService.getAudioResource(id, rangeHeader);
	}

	/**
	 *  get musicId에 해당하는 음악 정보조회
	 */
	@GetMapping("/info/{id}")
	public MusicInfoResDto musicInfo(@PathVariable("id") String id){
			log.info("Starting send music info");   
			return musicsService.getMusicInfo(id);
	}

}
