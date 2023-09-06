package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.themesdto.ThemeSaveReqDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/theme")
public class ThemesController {
  
  /**
  *  post 유저 로그인 ( 불일치시(+비활성화계정) -1 반환, 일치시 유저의고유ID반환 )
  */
  @PostMapping("/save")
  public Long register(@RequestBody ThemeSaveReqDto requestDto) {
    log.info("Starting theme registration");
    //Long userId = usersService.login(requestDto);
    log.info("theme registration completed");
    return 1L;
  }
}
