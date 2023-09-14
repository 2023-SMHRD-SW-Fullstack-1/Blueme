package com.blueme.backend.dto.genredto;

import java.util.List;

import com.blueme.backend.model.entity.FavGenres;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavGenreReqDto {
	
	private String favChecklistId;
	
	private List<String> genreIds;
	
	public FavGenres toEntityFavGenres() {
		return FavGenres.builder().id(Long.parseLong(favChecklistId)).build();
	}

}
