package com.blueme.backend.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
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
		log.info("Recommendation music save completed with music ID = {}", musicId);
		return musicId;
	}
	
	/**
	 *  get musicId에 해당하는 음악 정보 조회
	 */
	/* 파일 다운로드 스트리밍에 부족합
	@GetMapping("/{id}")
	public ResponseEntity<StreamingResponseBody> streamMusic(@PathVariable("id") Long id) throws FileNotFoundException {
    log.info("Starting get musicId = {}", id);
		InputStream musicStream = musicsService.loadMusicStream(id);

    return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + ".mp3\"")
            .body(outStream -> {
                byte[] buffer = new byte[4096];
                int bytesRead;

                try {
                    while ((bytesRead = musicStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }
                    musicStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error: " + e.getMessage());
                }
            });
}*/
		/**
	   *  get musicId에 해당하는 음악 데이터 조회 (HTTP Range Request) , 음악 조회수 증가
	   */
		@GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> streamAudio(@PathVariable("id") String id, 
        @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {    
    		return musicsService.getAudioResource(id, rangeHeader);
    }

		/**
	   *  get musicId에 해당하는 음악 정보조회
	   */
		@GetMapping("/info/{id}")
    public MusicInfoResDto musicInfo(@PathVariable("id") String id){    
    		return musicsService.getMusicInfo(id);
    }

}
