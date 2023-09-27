package com.blueme.backend.service.exception;

/*
 * 작성자: 손지연
 * 날짜(수정포함): 2023-09-27
 * 설명: 이미 존재하는 이메일로 가입 시도할 때 발생하는 예외처리
 */

public class EmailAlreadyExistsException extends RuntimeException {
	private String email;
	
	public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
        this.email = email;
    }
	
	 public String getEmail() {
	        return this.email;
	    }
}
