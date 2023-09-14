package com.blueme.backend.dto.artistdto;

import com.blueme.backend.model.entity.Musics;

import lombok.Getter;

@Getter
public class ArtistInfoDto {
	
	String artistFilePath;
	
	String artistName;
	
	String img;

	public ArtistInfoDto(Musics musics, String imgBase64) {
		this.artistFilePath=musics.getArtistFilePath();
		this.artistName=musics.getArtist();
		this.img = imgBase64;
	}
	
	

}
