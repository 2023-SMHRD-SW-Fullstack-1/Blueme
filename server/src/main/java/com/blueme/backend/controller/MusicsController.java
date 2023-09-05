package com.blueme.backend.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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
	
	/**
	 *  get musicId에 해당하는 음악 정보 조회
	 */
	@GetMapping("/{id}")
	public ResponseEntity<StreamingResponseBody> streamMusic(@PathVariable Long id) throws FileNotFoundException {
    
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
}

	
	
	
}
