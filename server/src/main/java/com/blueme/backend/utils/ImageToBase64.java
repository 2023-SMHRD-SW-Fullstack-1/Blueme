package com.blueme.backend.utils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
/*
작성자: 김혁
날짜(수정포함): 2023-09-04
설명: Image Base64 로 인코딩
*/

public class ImageToBase64 extends ImageConverter<File, String> {

	@Override
	public String convert(File f) throws IOException {
		
		byte[] fileContent = FileUtils.readFileToByteArray(f);
		
		String result = Base64.getEncoder().encodeToString(fileContent);
		
		return result; 
	}

}