package com.blueme.backend.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.blueme.backend.config.FilePathConfig;
import com.blueme.backend.dto.musicdto.MusicInfoResDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.utils.FileStorageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-13
설명: 음악 관련 서비스
*/

@Slf4j
@Service
@RequiredArgsConstructor
public class MusicsService {

    private final MusicsJpaRepository musicsJpaRepository;

    /*
     * 음악 페이징 조회
     */
    @Transactional(readOnly = true)
    public Page<Musics> findAll(Pageable pageable) {
        return musicsJpaRepository.findAll(pageable);
    }

    /*
     * get 음악 검색
     */
    @Transactional(readOnly = true)
    public List<Musics> searchMusic(String keyword) {
        return musicsJpaRepository.findByTitleContaining(keyword);
    }

    /**
     * post 음악 다중등록
     */
    @Transactional
    public Long save(MultipartFile[] files) {
        FileStorageUtil fileStorage = new FileStorageUtil();
        Long lastId = -1L;
        for (MultipartFile file : files) {
            String filePath = storeFile(fileStorage, file);
            lastId = extractMetadataAndSave(filePath);
        }
        return lastId;
    }

    private String storeFile(FileStorageUtil fileStorage, MultipartFile file) {
        return fileStorage.storeFile(file);
    }

    // 메타데이터 추출
    private Long extractMetadataAndSave(String filePath) {
        try {
            AudioFile audioFile = AudioFileIO.read(new File(filePath));
            Tag tag = audioFile.getTag();
            Musics music = buildMusic(tag, filePath);
            return musicsJpaRepository.save(music).getId();
        } catch (Exception e) {
            throw new RuntimeException("메타데이터 추출 실패", e);
        }
    }

    private Musics buildMusic(Tag tag, String filePath) {
        String artist = tag.getFirst(FieldKey.ARTIST);
        String album = tag.getFirst(FieldKey.ALBUM);
        String title = tag.getFirst(FieldKey.TITLE);
        String genre = tag.getFirst(FieldKey.GENRE);

        return Musics.builder()
                .title(title)
                .album(album)
                .artist(artist)
                .genre1(genre)
                .filePath(filePath)
                .build();
    }

    /*
     * get 음악 파일 전송(파일, RangeRequest 두종류) + 재생이므로 조회수 증가
     * Header에 Range설정하지 않은경우 일반 Audio 타입 데이터 반환
     */
    @Transactional
    public ResponseEntity<InputStreamResource> getAudioResource(String id, String rangeHeader) {
        Musics music = findMusicById(id);
        increaseHit(music);

        File file = getFile(music.getFilePath() + ".mp3");
        long fileSize = file.length();

        return rangeHeader == null ? sendFullData(fileSize, file) : sendPartialData(rangeHeader, fileSize, file);
    }

    private Musics findMusicById(String id) {
        return musicsJpaRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 음악이 없습니다."));
    }

    // 조회수 증가
    private void increaseHit(Musics music) {
        music.setHit(music.getHit() + 1);
    }

    private File getFile(String filePath) {
        Path path = Paths.get(FilePathConfig.MUSIC_PATH + filePath);
        File file = path.toFile();
        if (!file.exists()) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다. 경로 = " + file.getAbsolutePath());
        }
        return file;
    }

    private HttpHeaders createHeaders(long fileSize) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "audio/mpeg");
        headers.add("Accept-Ranges", "bytes");
        return headers;
    }

    private ResponseEntity<InputStreamResource> sendFullData(long fileSize, File file) {
        HttpHeaders headers = createHeaders(fileSize);

        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("파일 전송 실패", e);
        }
    }

    // 부분데이터 전송(실제로 구현x)
    private ResponseEntity<InputStreamResource> sendPartialData(String rangeHeader, long fileSize, File file) {
        String[] ranges = rangeHeader.replace("bytes=", "").split("-");
        long startRange = Long.parseLong(ranges[0]);
        long endRange = ranges.length > 1 ? Long.parseLong(ranges[1]) : fileSize - 1;

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            long length = endRange - startRange + 1;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(length);
            headers.add("Content-Range", "bytes " + startRange + "-" + endRange + "/" + fileSize);

            raf.seek(startRange);

            byte[] buffer = new byte[(int) length];
            raf.read(buffer, 0, (int) length);

            InputStream is = new ByteArrayInputStream(buffer);

            InputStreamResource resource = new InputStreamResource(is);

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).headers(headers).body(resource);

        } catch (IOException e) {
            throw new RuntimeException("부분 데이터 전송 실패", e);
        }
    }

    /*
     * get 음악 정보 전송
     */
    @Transactional(readOnly = true)
    public MusicInfoResDto getMusicInfo(String id) {
        Musics music = musicsJpaRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 음악이 없습니다."));
        return new MusicInfoResDto(music);
    }

    /*
     * 카운트에 해당하는 랜덤한음악 리스트 반환
     */
    @Transactional(readOnly = true)
    public List<Musics> getRandomEntities(int count) {
        return musicsJpaRepository.findRandomMusics(count);
    }

}
