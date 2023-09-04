package com.blueme.backend.dto.musicdto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MusicSaveDto {
	
	private MultipartFile[] files;
	
}
