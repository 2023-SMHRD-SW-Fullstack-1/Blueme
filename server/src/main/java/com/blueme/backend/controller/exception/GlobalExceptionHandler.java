package com.blueme.backend.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blueme.backend.service.exception.MusicNotFoundException;
import com.blueme.backend.service.exception.SaveMusiclistNotFoundException;
import com.blueme.backend.service.exception.UserNotFoundException;

/*
작성자: 김혁
날짜(수정포함): 2023-09-14
설명: 전역적 에러처리 핸들러
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
