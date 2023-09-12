package com.blueme.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blueme.backend.dto.themesdto.ThemeDetailsResDto;
import com.blueme.backend.dto.themesdto.ThemeDetailsResDto;
import com.blueme.backend.dto.themesdto.ThemeSaveReqDto;
import com.blueme.backend.dto.themesdto.ThemelistDetailResDto;
import com.blueme.backend.dto.themesdto.ThemelistResDto;
import com.blueme.backend.model.entity.Themes;
import com.blueme.backend.service.ThemesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-11
설명: 음악테마 관련 컨트롤러
*/

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/theme")
@CrossOrigin("*")
public class ThemesController {

    private final ThemesService themesService;

    /**
     * post theme 등록
     */
    @PostMapping("/register")
    public Long register(@RequestPart("theme_img_file") MultipartFile imageFile,
            @RequestPart("requestDto") String requestDtoString) {
        ThemeSaveReqDto requestDto = null;
        log.info("Starting theme registration");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            requestDto = objectMapper.readValue(requestDtoString, ThemeSaveReqDto.class);
        } catch (JsonProcessingException e) {
            log.error("objectMapper 변환 실패!");
        }
        Long themeId = themesService.saveThemes(imageFile, requestDto);
        log.info("theme registration completed");
        return themeId;
    }

    /**
     * get 모든 테마 조회
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
    public List<ThemeDetailsResDto> getThemeById(@PathVariable("id") Long id) {
        log.info("Starting theme selection by id: {}", id);
        List<ThemeDetailsResDto> result = themesService.getThemeById(id);
        log.info("Theme selection by id completed");
        return result;
    }

}
