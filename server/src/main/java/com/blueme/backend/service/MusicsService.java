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
import com.blueme.backend.service.exception.MusicNotFoundException;
import com.blueme.backend.utils.FileStorageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MusicsService는 음악 서비스 클래스입니다.
 * <p>
 * 이 클래스에서는 음악 등록, 페이징조회, 정보조회, 검색, 음악데이터 전송기능을 제공합니다.
 * </p>
 *
 * @author 김혁
 * @version 1.0
 * @since 2023-09-13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MusicsService {

    private final MusicsJpaRepository musicsJpaRepository;

    /**
     * 페이징 한 음악 목록을 반환합니다.
     * 
     * @param pageable 페이징 요청 객체
     * @return 음악 목록 (Page<Musics>)
     */
    @Transactional(readOnly = true)
    public Page<Musics> findAll(Pageable pageable) {
        return musicsJpaRepository.findAll(pageable);
    }

    /**
     * 사용자가 입력한 키워드에 맞는 음악 목록을 반환합니다.
     * 
     * @param keyword 검색을 요청하는 문자열
     * @return 검색에 해당하는 음악 목록 (List<Musics>)
     */
    @Transactional(readOnly = true)
    public List<Musics> searchMusic(String keyword) {
        return musicsJpaRepository.findByTitleContaining(keyword);
    }

    /**
     * 관리자가 음악을 등록하는 기능을 수행합니다.
     * 다중 등록이 가능합니다.
     * 
     * @param files 음악 데이터 파일 목록
     * @return 등록된 음악의 ID (Long)
     */
    @Transactional
    public Long save(MultipartFile[] files) {
        FileStorageUtil fileStorage = new FileStorageUtil();
        Long lastId = -1L;
        for (MultipartFile file : files) {
            String filePath = fileStorage.storeFile(file);
            lastId = extractMetadataAndSave(filePath);
        }
        return lastId;
    }

    /**
     * 주어진 파일 경로에서 오디오 파일의 메타데이터를 추출하고,
     * 추출된 정보를 바탕으로 음악을 저장합니다.
     *
     * @param filePath 오디오 파일의 경로
     * @return 저장된 음악의 ID
     */
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

    /**
     * Tag 객체와 파일 경로를 이용하여 Musics 객체를 생성합니다.
     *
     * @param tag      오디오 파일의 태그 정보
     * @param filePath 오디오 파일의 경로
     * @return 생성된 Musics 객체
     */
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

    /**
     * 음악 데이터를 반환합니다. 이 과정에서 조회수가 증가합니다.
     * 헤더에 Range 설정이 없는 경우 일반 Audio 타입 데이터를 반환합니다.
     *
     * @param id          음악 ID
     * @param rangeHeader Range 헤더 값
     * @return 전송된 음악 데이터와 함께 ResponseEntity 객체를 반환합니다.
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

    /**
     * 음악의 조회수를 증가시킵니다.
     *
     * @param music 조회수를 증가시킬 Musics 객체
     */
    private void increaseHit(Musics music) {
        music.setHit(music.getHit() + 1);
    }

    /**
     * 주어진 파일 경로에서 File 객체를 가져옵니다.
     *
     * @param filePath 파일의 경로
     * @return 해당 경로의 File 객체
     */
    private File getFile(String filePath) {
        Path path = Paths.get(FilePathConfig.MUSIC_PATH + filePath);
        File file = path.toFile();
        if (!file.exists()) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다. 경로 = " + file.getAbsolutePath());
        }
        return file;
    }

    /**
     * 음악 파일 전송에 필요한 HttpHeaders 객체를 생성합니다.
     *
     * @param fileSize 전송할 파일의 크기
     * @return 생성된 HttpHeaders 객체
     */
    private HttpHeaders createHeaders(long fileSize) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "audio/mpeg");
        headers.add("Accept-Ranges", "bytes");
        return headers;
    }

    /**
     * 전체 데이터를 전송합니다.
     *
     * @param fileSize 전송할 파일의 크기
     * @param file     전송할 File 객체
     * @return InputStreamResource와 함께 ResponseEntity 객체를 반환합니다.
     */
    private ResponseEntity<InputStreamResource> sendFullData(long fileSize, File file) {
        HttpHeaders headers = createHeaders(fileSize);

        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("파일 전송 실패", e);
        }
    }

    /**
     * /* 부분 데이터(파일 범위 내)를 전송합니다.
     * /*
     * /* @param rangeHeader Range 헤더 값
     * /* @param fileSize 전체 파일 크기
     * /* @param file 부분 데이터가 포함된 File 객체
     * /* return InputStreamResource와 함께 ResponseEntity 객체를 반환합니다.
     */
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

    /**
     * 특정 ID의 음악 정보를 가져옵니다.
     *
     * @param id 조회할 음악의 ID
     * @return 해당 음악의 정보가 담긴 MusicInfoResDto 객체를 반환합니다.
     */
    @Transactional(readOnly = true)
    public MusicInfoResDto getMusicInfo(String id) {
        Musics music = musicsJpaRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new MusicNotFoundException(Long.parseLong(id)));
        return new MusicInfoResDto(music);
    }

    /**
     * 지정된 개수만큼 랜덤한 음악 리스트를 반환합니다.
     *
     * @param count 가져올 랜덤한 음악의 개수
     * @return 랜덤하게 선택된 Musics 객체 리스트를 반환합니다.
     */
    @Transactional(readOnly = true)
    public List<Musics> getRandomEntities(int count) {
        return musicsJpaRepository.findRandomMusics(count);
    }

}
