package com.blueme.backend.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.utils.FileStorageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicsService {
	
	private final MusicsJpaRepository musicsJpaRepository;
	
	/**
	 *  post 음악 다중등록
	 */
	@Transactional
	public Long save (MultipartFile[] files){
		FileStorageUtil fileStorage = new FileStorageUtil();
		
		// 오류나면 저장안될시 -1 반환 저장시 마지막 음악의ID값 반환
		Long lastId = -1L;
		
		for (MultipartFile file : files) {
            String filePath = fileStorage.storeFile(file);
            
            try {
                AudioFile audioFile = AudioFileIO.read(new File(filePath));
                Tag tag = audioFile.getTag();
                String artist = tag.getFirst(FieldKey.ARTIST);
                String album = tag.getFirst(FieldKey.ALBUM);
                String title = tag.getFirst(FieldKey.TITLE);
                String genre = tag.getFirst(FieldKey.GENRE);
                // String bpm = tag.getFirst(FieldKey.BPM);
                //String mood = tag.getFirst(FieldKey.MOOD);
                //String year = tag.getFirst(FieldKey.YEAR);
                Musics music = Musics.builder().title(title).album(album).artist(artist).genre1(genre)
                		.filePath(filePath).build();
                
                lastId = musicsJpaRepository.save(music).getId();
                
            } catch (Exception e) {
               throw new RuntimeException("Failed to extract metadata from the file", e); 
            }
        }
		return lastId;
	}
	
    /**
	 *  get musicId에 해당하는 음악 정보 조회
	 */
    /*  파일 전송 -> 스트리밍에 부적합
    @Transactional
    public InputStream loadMusicStream(Long id) {
        try {
            Musics music = musicsJpaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Music not found"));

            Path filePath = Paths.get(music.getFilePath());
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    */
    /**
	 *  get musicId에 해당하는 음악 정보 조회 (Range로 나눠서 전송) - RandomAccessFIle 사용x
	 */
    /* 
    @Transactional
    public ResponseEntity<InputStreamResource> getAudioResource(String id, String rangeHeader) {
        try {
            
            // 음악 찾고 음악이 없으면 NOT_FOUND 보낸다.
            Musics music = musicsJpaRepository.findById(Long.parseLong(id)).orElse(null);
            if (music == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Path filePath = Paths.get(music.getFilePath());
            
            if (!Files.exists(filePath)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            long fileSize = Files.size(filePath);

			// 헤더 Parser
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "audio/mpeg");
			headers.add("Accept-Ranges", "bytes");

			if (rangeHeader == null) { // rangeHeader 가 null 이면 전체 파일 보낸다.
				headers.setContentLength(fileSize);
				InputStreamResource resource = new InputStreamResource(Files.newInputStream(filePath));
				return new ResponseEntity<>(resource, headers, HttpStatus.OK);
			} else { // Range request - 파일의 각 부분 보냄
				String[] ranges = rangeHeader.replace("bytes=", "").split("-");
				long startRange = Long.parseLong(ranges[0]);
				long endRange = ranges.length > 1 ? Long.parseLong(ranges[1]) : fileSize - 1;

				long length = endRange - startRange + 1;
				headers.setContentLength(length);
				
                headers.add("Content-Range", "bytes " + startRange + "-" 
                        + endRange + "/" + fileSize);

                InputStream inputStream = Files.newInputStream(filePath);
                
                inputStream.skip(startRange); 

                InputStreamResource resource =
                        new InputStreamResource(new ByteArrayInputStream(inputStream.readNBytes((int) length)));

                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                        .headers(headers)
                        .body(resource); 

			}
        } catch (Exception e) {
           throw new RuntimeException("Failed to process audio stream", e); 
        }
    }
        */
    /*
     * 실제 서비스에 사용하기 위해서는 RandomAccessFile 사용해야함
     */
    @Transactional
    public ResponseEntity<InputStreamResource> getAudioResource(String id, String rangeHeader) {
        RandomAccessFile raf = null;
        try {
            Musics music = musicsJpaRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 음악이 없습니다."));
            if (music == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // 파일 경로 설정
            Path filePath = Paths.get("\\usr\\blueme\\musics\\"+music.getFilePath()+".mp3");
            
            File file = filePath.toFile();
            
            // 경로에 파일이 없을 경우
			if (!file.exists()) {
                log.debug("File does not exist: {}", file.getAbsolutePath());
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			raf = new RandomAccessFile(file, "r");
			long fileSize = raf.length();

			// Range Header Parser
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "audio/mpeg");
			headers.add("Accept-Ranges", "bytes");

			if (rangeHeader == null) { // 파일 전체 데이터 전송
				headers.setContentLength(fileSize);

				InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
				return new ResponseEntity<>(resource, headers, HttpStatus.OK);
				
			} else { // Range request - 부분전송
				String[] ranges = rangeHeader.replace("bytes=", "").split("-");
				long startRange = Long.parseLong(ranges[0]);
				long endRange = ranges.length > 1 ? Long.parseLong(ranges[1]) : fileSize - 1;

				long length = endRange - startRange + 1;
				
                headers.setContentLength(length);

                headers.add("Content-Range", "bytes " + startRange + "-" 
                        + endRange + "/" + fileSize);

                raf.seek(startRange); 

                byte[] buffer= new byte[(int)length];
                
                raf.read(buffer, 0, (int)length);

                InputStream is=new ByteArrayInputStream(buffer);
                
                InputStreamResource resource =
                        new InputStreamResource(is);

                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                        .headers(headers)
                        .body(resource); 
                
               }
        } catch (Exception e) {
           throw new RuntimeException("Failed to process audio stream", e); 
        } finally {
           if(raf != null){
               try{
                   raf.close();
               }catch(IOException e){
                   throw new RuntimeException("Failed to close Random Access File", e); 
               }
           }
        }
    }


}
