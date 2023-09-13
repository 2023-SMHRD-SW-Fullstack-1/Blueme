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

import com.blueme.backend.dto.themesdto.ThemeDetailsResDto;
import com.blueme.backend.dto.themesdto.ThemeSaveReqDto;
import com.blueme.backend.dto.themesdto.ThemelistResDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.ThemeMusiclists;
import com.blueme.backend.model.entity.Themes;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.repository.ThemeMusiclistsJpaRepository;
import com.blueme.backend.model.repository.ThemesJpaRepository;
import com.blueme.backend.utils.ImgStorageUtil;

import lombok.RequiredArgsConstructor;

/*
작성자: 김혁
날짜(수정포함): 2023-09-13
설명: 테마 단일 관련 서비스
*/

@RequiredArgsConstructor
@Service
public class ThemesService {

  private final ThemesJpaRepository themesJpaRepository;
  private final MusicsJpaRepository musicsJpaRepository;

  private final ThemeMusiclistsJpaRepository themeMusiclistsJpaRepository;

  /**
   * post 테마 등록
   */
  @Transactional
  public Long saveThemes(MultipartFile imageFile, ThemeSaveReqDto requestDto) {
    List<Musics> musics = requestDto.getMusicIds().stream()
        .map((id) -> musicsJpaRepository.findById(Long.parseLong(id))
            .orElseThrow(() -> new IllegalArgumentException("음악ID " + id + "를 찾을 수 없습니다")))
        .collect(Collectors.toList());

    List<ThemeMusiclists> themeMusicList = musics.stream()
        .map(music -> ThemeMusiclists.builder().music(music).build())
        .collect(Collectors.toList());

    ImgStorageUtil imgStorage = new ImgStorageUtil();
    String filePath = imgStorage.storeFile(imageFile);
    return themesJpaRepository.save(Themes.builder()
        .title(requestDto.getTitle())
        .content(requestDto.getContent())
        .themeImgPath(filePath)
        .themeMusicList(themeMusicList)
        .build())
        .getId();
  }

  /**
   * get 모든 테마 조회
   */
  @Transactional(readOnly = true)
  public List<ThemelistResDto> getAllThemes() {
    List<Themes> themes = themesJpaRepository.findAll();
    return themes.stream().map(ThemelistResDto::new).collect(Collectors.toList());
  }

  /**
   * get 단일 테마 상세 조회 (음악 id 포함)
   */
  @Transactional(readOnly = true)
  public List<ThemeDetailsResDto> getThemeDetailsById(Long id) {
    List<ThemeMusiclists> themeDetails = themeMusiclistsJpaRepository.findByThemeId(id);
    return themeDetails.stream().map(ThemeDetailsResDto::new).collect(Collectors.toList());

  }
}
