package com.blueme.backend.dto.artistdto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavArtistReqDto {
	
	private String favChecklistId;
	
	private List<String> artistIds;

}
