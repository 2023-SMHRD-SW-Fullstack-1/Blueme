package com.blueme.backend.utils;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/*
작성자: 김혁
날짜(수정포함): 2023-09-03
설명: APIResponse 클래스
*/

@Component
public class APIResponse {

    public void printErrorMessage(Exception e) {
        // 로그 또는 콘솔에 에러 메시지를 출력하는 코드
        System.err.println(e.getMessage());
    }

    public <T> ResponseEntity<T> getResponseEntity(Locale locale, String code, T body) {
        HttpStatus status = HttpStatus.OK; // 기본적으로 OK 상태를 반환

        // 에러 코드가 있다면 상태를 변경
        if (!code.equals(ResponseCode.CD_SUCCESS)) {
            status = HttpStatus.INTERNAL_SERVER_ERROR; // 실패 시 일반적으로 사용되는 HTTP 상태 코드
            // 필요에 따라 다른 HTTP 상태 코드로 설정할 수 있습니다.
        }

        return new ResponseEntity<>(body, status);
    }
}