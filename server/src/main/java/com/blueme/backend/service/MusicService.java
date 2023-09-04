package com.blueme.backend.service;

import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.blueme.backend.model.entity.Music;
import com.blueme.backend.model.repository.MusicJpaRepository;
import com.blueme.backend.utils.FileStorageUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MusicService {
	
	private final MusicJpaRepository musicJpaRepository;
	
	/**
	 *  post 음악 다중등록
	 */
	@Transactional
	public Long save (MultipartFile[] files){
		FileStorageUtil fileStorage = new FileStorageUtil();
		
		// 오류나면 저장안됄시 -1 반환 저장시 마지막 음악의ID값 반환
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
                String bpm = tag.getFirst(FieldKey.BPM);
                String mood = tag.getFirst(FieldKey.MOOD);
                String year = tag.getFirst(FieldKey.YEAR);
                Music music = Music.builder().title(title).album(album).artist(artist).genre(genre)
                		.bpm(bpm).mood(mood).year(year).filePath(filePath).build();
                
                lastId = musicJpaRepository.save(music).getId();
                
            } catch (Exception e) {
               throw new RuntimeException("Failed to extract metadata from the file", e); 
            }
        }
		return lastId;
	}
	
}
