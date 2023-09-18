package com.blueme.backend.dto.usersdto;

import java.util.List;

import com.blueme.backend.dto.artistdto.ArtistInfoDto;
import com.blueme.backend.dto.genredto.GenreInfoDto;
import com.blueme.backend.model.entity.Users;

import lombok.Getter;

@Getter
public class UserProfileDto {

	Long userId;	
	String imgUrl;	
	String platFromType;	
	List<GenreInfoDto> genres;	
	List<ArtistInfoDto> artists;

	public UserProfileDto(Users user, List<GenreInfoDto> genres, List<ArtistInfoDto> artists) {
		this.userId = user.getId();
		this.imgUrl = user.getImg_url();
		this.platFromType = user.getPlatformType();
		this.genres = genres;
		this.artists = artists;
	}	
	
	
}
