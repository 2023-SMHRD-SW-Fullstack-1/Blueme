package com.blueme.backend.dto.genredto;

import java.util.List;

import com.blueme.backend.model.entity.FavGenres;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GenresUpdateDto {
	
	private String userId;
	private List<FavGenreReqDto> genreIds;
	
	public FavGenres toEntity() {
		return FavGenres.builder().id(Long.parseLong(userId)).build();
	}

}
