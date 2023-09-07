package com.blueme.backend.utils;

import java.io.IOException;

//F : 실제파일
//S : 저장 형식
public abstract class ImageConverter<F, S> {
	// 오버라이딩할 추상메소드
	public abstract S convert(F f) throws IOException;
	
}