package com.blueme.backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
import com.blueme.backend.service.exception.MusicNotFoundException;
import com.blueme.backend.service.exception.UserNotFoundException;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 손지연
날짜(수정포함): 2023-09-20
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
	public List<ArtistInfoDto> getAllArtist() {
		return musicsJpaRepository.findByArtist().stream()
				.map(artist -> {
					String base64Image = getBase64ImageForArtist(artist);
					return base64Image != null ? new ArtistInfoDto(artist, base64Image) : null;
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	/**
	 * 사용자가 선택한 가수(아티스트) 저장
	 */
	@Transactional
	public Long saveFavArtist(FavArtistReqDto requestDto) {
		log.info("Starting to save favorite artist : {}", requestDto);
		Long userId = Long.parseLong(requestDto.getFavChecklistId());
		Users user = usersJpaRepository.findById(userId)
		            .orElseThrow(() -> new UserNotFoundException(userId));
		
		FavCheckLists favCheckList = new FavCheckLists();
		favCheckList.setUser(user);
		favCheckList = favCheckListsJpaRepository.save(favCheckList);

		for (String favArtistStr : requestDto.getArtistIds()) {
			Musics musics = musicsJpaRepository.findTop1ByArtistFilePath(favArtistStr);
			if (musics == null) throw new MusicNotFoundException(Long.parseLong(favArtistStr));
			
			FavArtists favArtists = new FavArtists();
			favArtists.setFavCheckList(favCheckList);
			favArtists.setArtistId(musics);
			favArtistsJpaRepository.save(favArtists);
			
			log.info("Saved favorite artists : {}", musics.getArtist());
		}
		return userId;
	}
	

	/**
	 * get 가수(아티스트) 검색
	 */
	@Transactional(readOnly = true)
	public List<ArtistInfoDto> searchArtist(String keyword) {
		log.info("Starting artist search with keyword : {}", keyword);
		List<Musics> artistSearch = musicsJpaRepository.findByDistinctArtist(keyword);
		 if (artistSearch.isEmpty()) {
		        log.warn("No artists found with keyword: {}", keyword);
		        return Collections.emptyList();
		    }
		    return artistSearch.stream().flatMap(artist -> {
		        String base64Image = getBase64ImageForArtist(artist);
		        if (base64Image != null) {
		            return Stream.of(new ArtistInfoDto(artist, base64Image));
		        } else {
		            return Stream.empty();
		        }
		    }).collect(Collectors.toList());
	}
	
	
	/**
	 * 	patch 가수(아티스트) 수정
	 */
	@Transactional
	public Long updateFavArtist(Long userId, List<Long> newArtistIds) {	
		List<FavCheckLists> favCheckList = favCheckListsJpaRepository.findByUserId(userId);
		
		if(favCheckList.isEmpty()) throw new UserNotFoundException(userId);

		List<FavArtists> favArtists = favArtistsJpaRepository.findByFavCheckList(favCheckList.get(1));
		Musics music1 = musicsJpaRepository.findTop1ByArtistFilePath(newArtistIds.get(0).toString());
		Musics music2 = musicsJpaRepository.findTop1ByArtistFilePath(newArtistIds.get(1).toString());
		
	    favArtists.get(0).setArtistId(music1);
	    favArtists.get(1).setArtistId(music2);

		return userId;
	}

	
	/**
	 * 아티스트(가수) 이미지 변환
	 */
	public String getBase64ImageForArtist(Musics music) {
		if (music.getArtistFilePath() != null) {
			try {
				Path filePath = Paths.get("C:\\usr\\blueme\\artists\\" + music.getArtistFilePath() + ".jpg");
				File file = filePath.toFile();
				ImageConverter<File, String> converter = new ImageToBase64();
				String base64 = null;
				base64 = converter.convert(file);
				return base64;
			} catch (IOException e) {
				log.info(e.getMessage());
			}
		}
		return null;
	}

}
