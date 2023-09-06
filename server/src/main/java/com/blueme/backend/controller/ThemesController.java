package com.blueme.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.themesdto.ThemeSaveReqDto;
import com.blueme.backend.dto.themesdto.ThemelistResDto;
import com.blueme.backend.model.entity.Themes;
import com.blueme.backend.service.ThemesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/theme")
public class ThemesController {

  private final ThemesService themesService;
  
  /**
  *  post theme 등록 
  */
  @PostMapping("/register")
  public Long register(@RequestBody ThemeSaveReqDto requestDto) {
    log.info("Starting theme registration");
    Long themeId = themesService.saveThemes(requestDto);
    log.info("theme registration completed");
    return themeId;
  }

  /**
  *  get 모든 테마 조회
  */
  @GetMapping("/themelists")
  public List<ThemelistResDto> getAllThemes() {
      log.info("Starting With No logs Ending all themelists selection");
      return themesService.getAllThemes();
  }

  /**
  * get 특정 테마 상세조회 (음악 목록 포함)
  */
  @GetMapping("/themelists/{id}")
  public ThemelistResDto getThemeById(@PathVariable("id") Long id) {
      log.info("Starting theme selection by id: {}" , id);
      ThemelistResDto result = themesService.getThemeById(id);
      log.info("Theme selection by id completed");
      return result;
  }


}
