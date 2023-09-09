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
import java.util.List;
import java.util.stream.Collectors;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.blueme.backend.dto.musicdto.MusicInfoResDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.utils.FileStorageUtil;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-08
설명: 음악 단일 관련 서비스
*/

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicsService {
	
	private final MusicsJpaRepository musicsJpaRepository;

    /*
     * 음악 페이징 조회
     */
    @Transactional
    public Page<Musics> findAll(Pageable pageable) {
        Page<Musics> musicsPage = musicsJpaRepository.findAll(pageable);
        return musicsPage;
    }

    /*
     * 음악 조회
     */
    @Transactional
    public List<Musics> searchMusic(String keyword) {
        return musicsJpaRepository.findByTitleContaining(keyword);
    }
	
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
            // 메타데이터 추출 하는 로직
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
               throw new RuntimeException("메타데이터 추출 실패", e); 
            }
        }
		return lastId;
	}

    /*
     * 음악파일전송 테스트 (StreamingResponseBody 사용)
     */
    @Transactional
    public StreamingResponseBody streamMusic(String id){
        try {
            Musics music = musicsJpaRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 음악이 없습니다."));

            // 파일 경로 설정
            Path filePath = Paths.get("\\usr\\blueme\\musics\\"+music.getFilePath()+".mp3");
            File file = filePath.toFile();
            return outputStream -> {
                int nRead;
                //byte[] data = new byte[1024];
                byte[] data = new byte[256];
                try (InputStream inputStream = new FileInputStream(file)) {
                    while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                        outputStream.write(data, 0, nRead);
                    }
                } catch (IOException e) {
                    
                }
            };
            
        } catch (Exception e) {
            throw e;
        }
    }


    /*
     * 음악 파일 전송(파일, RangeRequest 두종류) + 재생이므로 조회수 증가
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
                log.debug("파일이 존재하지 않습니다 경로 = {}", file.getAbsolutePath());
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

            // 조회수 증가
            music.setHit(music.getHit() + 1);

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
           throw new RuntimeException("오디오 스트림 전송 실패", e); 
        } finally {
           if(raf != null){
               try{
                   raf.close();
               }catch(IOException e){
                   throw new RuntimeException("Random Access File 닫기 실패", e); 
               }
           }
        }
    }

    /*
     * 음악 정보 전송
     */
    @Transactional(readOnly = true)
    public MusicInfoResDto getMusicInfo(String id){
        try {
            Musics music = musicsJpaRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 음악이 없습니다."));
            // 앨범재킷 파일 불러오기
            // 파일 경로 설정
            Path filePath = Paths.get("\\usr\\blueme\\jackets\\"+music.getJacketFilePath()+".jpg");
            File file = filePath.toFile();
            
            // 경로에 파일이 없을 경우
            if (!file.exists()) {
                log.debug("재킷파일이 존재하지 않습니다 경로 = {}", file.getAbsolutePath());
            }
            // base64로 변환
            ImageConverter<File, String> converter = new ImageToBase64();
            String base64 = null;
            base64 = converter.convert(file);
            MusicInfoResDto res = new MusicInfoResDto(music, base64);

            return res;
        } catch (Exception e) {
           throw new RuntimeException("재킷파일 전송 실패", e); 
        }
    }

}
