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

import com.blueme.backend.dto.themesdto.TagDetailsResDto;
import com.blueme.backend.dto.themesdto.ThemeDetailsResDto;
import com.blueme.backend.dto.themesdto.ThemeSaveReqDto;
import com.blueme.backend.dto.themesdto.ThemelistResDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.ThemeMusiclists;
import com.blueme.backend.model.entity.ThemeTags;
import com.blueme.backend.model.entity.Themes;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.repository.ThemeMusiclistsJpaRepository;
import com.blueme.backend.model.repository.ThemeTagsJpaRepository;
import com.blueme.backend.model.repository.ThemesJpaRepository;
import com.blueme.backend.utils.ImgStorageUtil;

import lombok.RequiredArgsConstructor;

/**
 * ThemesService는 테마 서비스 입니다.
 * <p>
 * 이 클래스에서는 테마 등록, 모든테마 조회, 특정 테마조회 기능을 수행합니다.
 * <p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-13
 */
@RequiredArgsConstructor
@Service
public class ThemesService {

  private final ThemesJpaRepository themesJpaRepository;
  private final MusicsJpaRepository musicsJpaRepository;

  private final ThemeMusiclistsJpaRepository themeMusiclistsJpaRepository;
  private final ThemeTagsJpaRepository themeTagsJpaRepository;

  /**
   * 테마 등록을 수행합니다.
   * 
   * @param imageFile  테마 이미지(MultipartFile)
   * @param requestDto 제목, 설명, musicIds가 담긴 문자열 (String)
   * @return 저장된 테마의ID (Long)
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
    ThemeTags tag = themeTagsJpaRepository.findById(Long.parseLong(requestDto.getSelectedTagId()))
        .orElseThrow(null);
    ImgStorageUtil imgStorage = new ImgStorageUtil();
    String filePath = imgStorage.storeFile(imageFile);
    return themesJpaRepository.save(Themes.builder()
        .title(requestDto.getTitle())
        .content(requestDto.getContent())
        .themeImgPath(filePath)
        .themeMusicList(themeMusicList)
        .themeTag(tag)
        .build())
        .getId();
  }

  /**
   * 모든 테마 조회를 수행합니다.
   * 
   * @return 테마 정보가 담긴 목록 (List<ThemelistResDto>)
   */
  @Transactional(readOnly = true)
  public List<ThemelistResDto> getAllThemes() {
    List<Themes> themes = themesJpaRepository.findAll();
    return themes.stream().map(ThemelistResDto::new).collect(Collectors.toList());
  }

  /**
   * 특정 테마에 해당하는 테마 상세조회를 수행합니다.
   * 
   * @param id 테마ID (Long)
   * @return 특정테마정보 상세가 담긴 정보 목록 (List<ThemeDetailsResDto>)
   */
  @Transactional(readOnly = true)
  public List<ThemeDetailsResDto> getThemeDetailsById(Long id) {
    List<ThemeMusiclists> themeDetails = themeMusiclistsJpaRepository.findByThemeId(id);
    return themeDetails.stream().map(ThemeDetailsResDto::new).collect(Collectors.toList());
  }

  /**
   * 모든 테마의 태그 조회를 수행합니다.
   * 
   * @return 테마 태그 목록(List<ThemeDetailsResDto>)
   */
  @Transactional(readOnly = true)
  public List<TagDetailsResDto> getAllThemeTags() {
    return themeTagsJpaRepository.findAll().stream().map(TagDetailsResDto::new).collect(Collectors.toList());
  }
}
