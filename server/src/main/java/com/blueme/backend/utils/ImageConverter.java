package com.blueme.backend.utils;

import java.io.IOException;

/*
작성자: 김혁
날짜(수정포함): 2023-09-04
설명: 이미지 컨버터 추상클래스 오버라이딩 강제
*/

//F : 실제파일
//S : 저장 형식
public abstract class ImageConverter<F, S> {
	// 오버라이딩할 추상메소드
	public abstract S convert(F f) throws IOException;
	
}