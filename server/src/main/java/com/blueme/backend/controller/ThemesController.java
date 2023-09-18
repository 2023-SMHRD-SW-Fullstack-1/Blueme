package com.blueme.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blueme.backend.dto.themesdto.ThemeDetailsResDto;
import com.blueme.backend.dto.themesdto.ThemeSaveReqDto;
import com.blueme.backend.dto.themesdto.ThemelistResDto;
import com.blueme.backend.service.ThemesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ThemesController는 테마 관련 컨트롤러입니다.
 * <p>
 * 이 클래스에서는 테마 등록, 모든테마 조회, 특정 테마 조회 기능을 처리합니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-11
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/theme")
@CrossOrigin("*")
public class ThemesController {

    private final ThemesService themesService;

    /**
     * 테마 등록을 위한 POST 메서드입니다.
     * 
     * @param imageFile        테마 이미지 (MultipartFile)
     * @param requestDtoString 제목, 설명, musicIds가 담긴 문자열 (String)
     * @return 저장된 테마ID (Long)
     * @throws JsonMappingException    JSON 매핑 과정에서의 오류발생
     * @throws JsonProcessingException JSON 처리 과정에서의 오류발생
     */
    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestPart("theme_img_file") MultipartFile imageFile,
            @RequestPart("requestDto") String requestDtoString) throws JsonMappingException, JsonProcessingException {
        ThemeSaveReqDto requestDto = null;
        log.info("Starting theme registration");
        ObjectMapper objectMapper = new ObjectMapper();
        requestDto = objectMapper.readValue(requestDtoString, ThemeSaveReqDto.class);
        log.info("End of theme registration");
        Long result = themesService.saveThemes(imageFile, requestDto);
        return ResponseEntity.ok(result);
    }

    /**
     * 모든 테마목록을 조회하기 위한 GET 메서드입니다.
     * 
     * @return 테마정보가 담긴 목록 (List<ThemelistResDto>), 없을경우 NO_CONTENT 상태코드 반환
     */
    @GetMapping("/themelists")
    public ResponseEntity<List<ThemelistResDto>> getAllThemes() {
        log.info("Starting GetAllThemelists");
        List<ThemelistResDto> themelistResDtos = themesService.getAllThemes();
        log.info("Ending GetAllThemelists");
        if (themelistResDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(themelistResDtos);
        }
    }

    /**
     * 특정테마 리스트 상세조회를 위한 GET 메서드입니다.
     * 
     * @param id 테마ID (Long)
     * @return 특정테마 정보 상세가 담긴 정보 목록 (List<ThemeDetailsResDto>), 없을경우 NO_CONTENT 상태코드
     *         반환
     */
    @GetMapping("/themelists/{id}")
    public ResponseEntity<List<ThemeDetailsResDto>> getThemeById(@PathVariable("id") Long id) {
        log.info("Starting theme selection by id: {}", id);
        List<ThemeDetailsResDto> themeDetailsResDtos = themesService.getThemeDetailsById(id);
        log.info("Ending theme selection by id: {}", id);
        if (themeDetailsResDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(themeDetailsResDtos);
        }
    }

}
