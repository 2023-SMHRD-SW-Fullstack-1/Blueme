package com.blueme.backend.dto.musiclistdto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecMusicListSaveDto {
	
	private String title;
	private String userId;
	private List<String> recommendedMusicIds;

}
