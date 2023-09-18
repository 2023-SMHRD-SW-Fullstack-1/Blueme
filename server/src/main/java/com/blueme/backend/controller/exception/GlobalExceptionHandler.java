package com.blueme.backend.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blueme.backend.service.exception.MusicNotFoundException;
import com.blueme.backend.service.exception.SaveMusiclistNotFoundException;
import com.blueme.backend.service.exception.UserNotFoundException;

/**
 * GlobalExceptionHandler는 전역적인 예외 처리를 담당하는 클래스입니다.
 * <p>
 * 이 클래스에서는 사용자, 음악, 저장된 음악리스트를 찾지 못했을 때의 예외 처리 메소드를 정의하고 있습니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-11
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MusicNotFoundException.class)
  public ResponseEntity<String> handleMusicNotFound(MusicNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(SaveMusiclistNotFoundException.class)
  public ResponseEntity<String> handleSaveMusiclistNotFound(SaveMusiclistNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

}
