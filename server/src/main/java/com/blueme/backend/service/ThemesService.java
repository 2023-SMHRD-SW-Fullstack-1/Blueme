package com.blueme.backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.blueme.backend.dto.musiclistsdto.RecMusicListSaveDto;
import com.blueme.backend.dto.themesdto.ThemeDetailsResDto;
import com.blueme.backend.dto.themesdto.ThemeSaveReqDto;
import com.blueme.backend.dto.themesdto.ThemelistDetailResDto;
import com.blueme.backend.dto.themesdto.ThemelistResDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.RecMusiclistDetails;
import com.blueme.backend.model.entity.RecMusiclists;
import com.blueme.backend.model.entity.ThemeMusiclists;
import com.blueme.backend.model.entity.Themes;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;
import com.blueme.backend.model.repository.ThemesJpaRepository;
import com.blueme.backend.utils.FileStorageUtil;
import com.blueme.backend.utils.ImgStorageUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-04
설명: 테마 단일 관련 서비스
*/

@Slf4j
@RequiredArgsConstructor
@Service
public class ThemesService {

  private final ThemesJpaRepository themesJpaRepository;
  private final MusicsJpaRepository musicsJpaRepository;
  
  /**
	 *  post 테마 등록 (음악과 함께)
	 */
	@Transactional
	public Long saveThemes(MultipartFile imageFile, 
      ThemeSaveReqDto requestDto) {
    log.info("ThemeService post registerTheme start...");
    // 테마 등록
		// musicIds 를 music Entity담긴 리스트로 변환
		List<Musics> musics = requestDto.getMusicIds().stream()
				.map((id) -> musicsJpaRepository.findById(Long.parseLong(id))
				.orElseThrow(() -> new IllegalArgumentException("해당 음악ID를 찾을 수 없습니다"))).collect(Collectors.toList());
    
    List<ThemeMusiclists> themeMusicList = new ArrayList<>();
    for(int i=0; i<musics.size(); i++){
      themeMusicList.add(ThemeMusiclists.builder().music(musics.get(i)).build());
    }
    // 테마 이미지 저장
    ImgStorageUtil imgStorage = new ImgStorageUtil();
  
    String filePath = imgStorage.storeFile(imageFile);

    return themesJpaRepository.save(Themes.builder().title(requestDto.getTitle()).content(requestDto.getContent()).themeImgPath(filePath).themeMusicList(themeMusicList).build()).getId();
	}


  /**
	 *  get 모든 테마 조회
	 */
  @Transactional
  public List<ThemelistResDto> getAllThemes(){
    try{
      List<Themes> themes = themesJpaRepository.findAll();
      List<ThemelistResDto> themelistResDtos = new ArrayList<ThemelistResDto>();
      //이미지 추가(base64로 인코딩)
      for(Themes t : themes){
        Path filePath = Paths.get("C:\\" + t.getThemeImgPath());
        File file = filePath.toFile();
        if(!file.exists()){
          log.debug("테마사진이 존재하지 않습니다 경로 = {}", file.getAbsolutePath());
        }
        // 이미지 base64로 인코딩
        ImageConverter<File, String> converter = new ImageToBase64();
        String base64 = null;
        base64 = converter.convert(file);
        ThemelistResDto res = new ThemelistResDto(t, base64);
        themelistResDtos.add(res);
      }
      return themelistResDtos;
    }catch(Exception e){
      throw new RuntimeException("테마이미지파일 전송 실패", e); 
    }
  }

  /**
	 *  get 단일 테마 상세 조회 (음악 id 포함)
	 */
  @Transactional
  public List<ThemeDetailsResDto> getThemeById(Long id){
    Themes themes = themesJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 테마 아이디가 없습니다."));
    List<ThemeDetailsResDto> res = new ArrayList<ThemeDetailsResDto>();
    for(ThemeMusiclists t : themes.getThemeMusicList()){
      res.add(new ThemeDetailsResDto(themes, t));
    }
    return res;
  }
}


	