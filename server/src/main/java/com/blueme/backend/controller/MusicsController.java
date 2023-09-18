package com.blueme.backend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import com.blueme.backend.dto.musicdto.MusicInfoResDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.service.MusicsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MusicsController는 음악 컨트롤러 클래스입니다.
 * 이 클래스는 음악 등록, 페이징조회, 정보조회, 검색, 음악데이터 전송을 처리합니다.
 *
 * @author 김혁
 * @version 1.0
 * @since 2023-09-09
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
@CrossOrigin("http://localhost:3000")
public class MusicsController {

	private final MusicsService musicsService;

	/**
	 * 음악 등록을 위한 POST 메서드입니다.
	 * <p>
	 * 다중 음악 등록을 지원합니다.
	 * </p>
	 * 
	 * @param files 음악 데이터 파일목록
	 * @return 저장된 음악의 ID (Long)
	 * @exception RuntimeException         빈 파일이 전송되었을 경우에 발생합니다.
	 * @exception IllegalArgumentException 파일 저장에 실패했을 경우 발생합니다.
	 */
	@PostMapping("/admin/addmusic")
	public ResponseEntity<Long> saveMusic(@RequestParam("files") MultipartFile[] files) {
		log.info("Starting save music");
		Long musicId = musicsService.save(files);
		log.info("Recommendation music save completed with music ID = {}", musicId);
		if (musicId == -1L) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} else {
			return ResponseEntity.ok().body(musicId);
		}
	}

	/**
	 * 음악 페이징 조회를 위한 GET 메서드입니다.
	 * 
	 * @param pageable 페이징 요청 객체
	 * @return 음악 목록 (Page<Musics>)
	 */
	@GetMapping("/page")
	public ResponseEntity<Page<Musics>> getMusic(Pageable pageable) {
		log.info("Starting paging music");
		Page<Musics> musics = musicsService.findAll(pageable);
		log.info("Ending music paging completed");
		if (musics.isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} else {
			return ResponseEntity.ok(musics);
		}
	}

	/**
	 * 음악 검색을 위한 GET 메서드입니다.
	 * 
	 * @param keyword 검색을 요청하는 문자열
	 * @return 검색에 해당하는 음악 목록 (List<Musics>)
	 */
	@GetMapping("/search")
	public ResponseEntity<List<Musics>> searchMusic(@RequestParam("keyword") String keyword) {
		log.info("Starting search music info with keyword = {}", keyword);
		List<Musics> musics = musicsService.searchMusic(keyword);
		log.info("Ending music search completed with keyword = {}", keyword);
		if (musics.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.ok(musics);
		}
	}

	/**
	 * 음악 데이터 정보 전송을 위한 GET 메서드 입니다.
	 * 정보 전송을 하면 음악을 조회했기 떄문에 음악 조회수 또한 증가합니다.
	 * 
	 * @param id          음악ID
	 * @param rangeHeader 요청 시 분할요청 들어왔을경우(요청안헀을 경우 한번에 전송)
	 * @return 오디오 Stream(InputStreamResource)
	 * @throws IOException
	 */
	@GetMapping("/{id}")
	public ResponseEntity<InputStreamResource> streamAudio(@PathVariable("id") String id,
			@RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {
		log.info("Start sending music data for id = {}", id);
		ResponseEntity<InputStreamResource> responseEntity = musicsService.getAudioResource(id, rangeHeader);
		log.info("Finished sending music data for id = {}", id);
		return responseEntity;
	}

	/**
	 * 음악 정보 조회를 위한 GET 메서드입니다.
	 * 
	 * @param id 음악ID
	 * @return 음악 정보 Dto (MusicInfoResDto)
	 */
	@GetMapping("/info/{id}")
	public ResponseEntity<MusicInfoResDto> musicInfo(@PathVariable("id") String id) {
		log.info("Starting send music info with musicId = {}", id);
		MusicInfoResDto musicInfo = musicsService.getMusicInfo(id);
		log.info("Ending send music info with musicId = {}", musicInfo.getMusicId());
		return ResponseEntity.ok().body(musicInfo);
	}
}
