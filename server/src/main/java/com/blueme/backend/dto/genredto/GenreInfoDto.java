package com.blueme.backend.dto.genredto;

import com.blueme.backend.model.entity.Genres;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GenreInfoDto {
	
	Long genreId;
	
	String name;
	
	String img;
	
	public GenreInfoDto(Genres genres, String imgBase64) {
		this.genreId=genres.getId();
		this.name=genres.getName();
		this.img=imgBase64;
	}

	
	
	

}
