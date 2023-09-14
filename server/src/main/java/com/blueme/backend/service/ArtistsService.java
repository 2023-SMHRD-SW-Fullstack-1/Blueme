package com.blueme.backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blueme.backend.dto.artistdto.ArtistInfoDto;
import com.blueme.backend.dto.artistdto.FavArtistReqDto;
import com.blueme.backend.model.entity.FavArtists;
import com.blueme.backend.model.entity.FavCheckLists;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.FavArtistsJpaRepository;
import com.blueme.backend.model.repository.FavCheckListsJpaRepository;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 손지연
날짜(수정포함): 2023-09-13
설명: 회원가입 시 선호아티스트 관련 서비스
*/

@Slf4j
@RequiredArgsConstructor
@Service
public class ArtistsService {
	
	private final UsersJpaRepository usersJpaRepository;
	private final MusicsJpaRepository musicsJpaRepository;
	private final FavCheckListsJpaRepository favCheckListsJpaRepository;
	private final FavArtistsJpaRepository favArtistsJpaRepository;
	
	/**
	 * 	모든 가수(아티스트) 조회
	 */
	@Transactional
	public List<ArtistInfoDto> getAllArtist(){
		Map<String, String> pathToBase64 = new HashMap();
	    ImageConverter<File, String> converter = new ImageToBase64();
	    List<Musics> uniqueArtists = musicsJpaRepository.findByArtist();

	    return uniqueArtists.stream().flatMap(artist -> {
	        String imgPath = artist.getArtistFilePath();
	        if (imgPath != null) {
	            Path filePath = Paths.get("C:\\usr\\blueme\\artists\\"+imgPath+".jpg");
	            File file = filePath.toFile();
	            try {
	                // If the image path is already processed, use the existing base64 string.
	                // Otherwise, convert the image and put it in the map.
	                if (!pathToBase64.containsKey(imgPath)) {
	                    String base64 = converter.convert(file);
	                    pathToBase64.put(imgPath, base64);
	                }
	                return Stream.of(new ArtistInfoDto(artist, pathToBase64.get(imgPath)));
	            } catch (IOException e) {
	                log.info(e.getMessage());
	            }
	        }
	        return Stream.empty();
	    }).collect(Collectors.toList());
	}
	
	
	/**
	 * 사용자가 선택한 가수(아티스트) 저장
	 */
	@Transactional
	public Long saveFavArtist(FavArtistReqDto requestDto) {
		log.info("userId : ", requestDto.getFavChecklistId());
		log.info("artist_file_path : ", requestDto.getArtistIds());
		
		Users user = usersJpaRepository.findById(Long.parseLong(requestDto.getFavChecklistId()))
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));
		
		FavCheckLists favCheckList = new FavCheckLists();
		favCheckList.setUser(user);
		favCheckList = favCheckListsJpaRepository.save(favCheckList);
		
		for(String favArtistStr : requestDto.getArtistIds()) {
			Musics musics = musicsJpaRepository.findByArtistFilePath(favArtistStr);
//			if(musics == null) throw new IllegalArgumentException("존재하지 않는 가수 : "+favArtistStr); 
			if(musics == null) return -1L; 
			
			FavArtists favArtists = new FavArtists();
			favArtists.setFavCheckList(favCheckList);
			favArtists.setArtistId(musics);
			
			favArtistsJpaRepository.save(favArtists);
		}
		return Long.parseLong(requestDto.getFavChecklistId());
	}

}
