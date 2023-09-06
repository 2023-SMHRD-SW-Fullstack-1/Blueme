package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.themesdto.ThemeSaveReqDto;
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
}
