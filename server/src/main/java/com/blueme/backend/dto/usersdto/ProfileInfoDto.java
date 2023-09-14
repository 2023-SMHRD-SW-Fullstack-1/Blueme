package com.blueme.backend.dto.usersdto;

import com.blueme.backend.model.entity.Genres;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.Users;

import lombok.Getter;

@Getter
public class ProfileInfoDto {

	String platformType;
	
	String imgUrl;
	
	String genreName;
	
	String genreImg;
	
	String artistName;
	
	String artistImg;

	public ProfileInfoDto(Users user, Musics music, Genres genre, String genreImg, String artistImg) {
		this.platformType = user.getPlatformType();
		this.imgUrl = user.getImg_url();
		this.genreName = genre.getName();
		this.genreImg = genreImg;
		this.artistName = music.getArtist();
		this.artistImg = artistImg;
	}
	
	
}
