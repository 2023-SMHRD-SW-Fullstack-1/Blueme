package com.blueme.backend.dto.musiclistsdto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class RecMusicListSaveDto {

	private String title;
	private String reason;
	private String userId;
	private List<String> musicIds;

}
