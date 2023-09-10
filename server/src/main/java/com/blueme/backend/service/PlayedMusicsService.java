package com.blueme.backend.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blueme.backend.dto.musicdto.MusicInfoResDto;
import com.blueme.backend.dto.playedmusicdto.PlayedMusicsSaveReqDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.PlayedMusics;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.repository.PlayedMusicsJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;

import lombok.RequiredArgsConstructor;

/*
작성자: 김혁
날짜(수정포함): 2023-09-10
설명: 사용자가 재생한 음악 서비스
*/

@Service
@RequiredArgsConstructor
public class PlayedMusicsService {

  private final PlayedMusicsJpaRepository playedMusicsJpaRepository;
  private final UsersJpaRepository usersJpaRepository;
  private final MusicsJpaRepository musicsJpaRepository;
  
  /*
  * 최근재생된 음악 조회
  */
  @Transactional
  public List<MusicInfoResDto> getPlayedMusic(Long userId){
    try {
      List<Musics> musicsList = playedMusicsJpaRepository.findDistinctMusicByUserId(userId);
      List<MusicInfoResDto> musicInfoResDtos = new ArrayList<>();
      // 음악이미지 추가
      for(Musics music: musicsList){
        Path filePath = Paths.get("\\usr\\blueme\\jackets\\"+music.getJacketFilePath()+".jpg");
        File file = filePath.toFile();

        ImageConverter<File, String> converter = new ImageToBase64();
        String base64 = null;
        base64 = converter.convert(file);
        MusicInfoResDto res = new MusicInfoResDto(music, base64);
        musicInfoResDtos.add(res);
      }
      return musicInfoResDtos;
    } catch (Exception e) {
      throw new RuntimeException("저장리스트 - 재킷파일 전송 실패", e); 
    }
  }

  /*
   * 재생된 음악 등록
   */
  @Transactional
  public Long savePlayedMusic(PlayedMusicsSaveReqDto request){

    Users user = usersJpaRepository.findById(Long.parseLong(request.getUserId()))
      .orElseThrow(() -> new IllegalArgumentException("해당 ID에 해당하는 회원이 존재하지 않습니다."));
    
    Musics music = musicsJpaRepository.findById(Long.parseLong(request.getMusicId()))
      .orElseThrow(() -> new IllegalArgumentException("해당 ID에 해당하는 음악이 존재하지 않습니다."));

    return playedMusicsJpaRepository.save(PlayedMusics.builder().user(user).music(music).build()).getId();
  }
}
