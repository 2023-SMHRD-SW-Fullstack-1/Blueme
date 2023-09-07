package com.blueme.backend.utils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;


public class ImageToBase64 extends ImageConverter<File, String> {

	@Override
	public String convert(File f) throws IOException {
		
		byte[] fileContent = FileUtils.readFileToByteArray(f);
		
		String result = Base64.getEncoder().encodeToString(fileContent);
		
		return result; 
	}

}