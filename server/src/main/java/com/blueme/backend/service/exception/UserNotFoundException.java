package com.blueme.backend.service.exception;

/*
작성자: 김혁
날짜(수정포함): 2023-09-14
설명: Id에 해당하는 유저가 없을떄의 에러처리
*/

public class UserNotFoundException extends RuntimeException {
  private Long userId;

  public UserNotFoundException(Long userId) {
    super("Could not find user with id: " + userId);
    this.userId = userId;
  }

  public Long getUserId() {
    return this.userId;
  }
}
