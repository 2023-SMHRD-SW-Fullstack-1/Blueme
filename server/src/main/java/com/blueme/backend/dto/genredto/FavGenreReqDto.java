package com.blueme.backend.dto.genredto;

import java.util.List;
import java.util.stream.Collectors;

import com.blueme.backend.model.entity.FavGenres;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter@NoArgsConstructor
public class FavGenreReqDto {
	
	private String favChecklistId;
	
	private List<String> genreIds;
	
	 public List<FavGenres> toEntityFavGenres() { // 반환 타입을 List<FavGenres>로 변경
	        return genreIds.stream()
	            .map(genreId -> FavGenres.builder().id(Long.parseLong(favChecklistId)).build()) // .genreId(Long.parseLong(genreId)) 부분 삭제
	            .collect(Collectors.toList());
	    }
	

}
