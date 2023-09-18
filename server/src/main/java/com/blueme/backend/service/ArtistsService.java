package com.blueme.backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
날짜(수정포함): 2023-09-16
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
	 * 모든 가수(아티스트) 조회
	 */
	@Transactional
	public List<ArtistInfoDto> getAllArtist() {
		List<Musics> uniqueArtists = musicsJpaRepository.findByArtist();
		return uniqueArtists.stream().flatMap(artist -> {
			String base64Image = getBase64ImageForArtist(artist);
			if (base64Image != null) {
	            return Stream.of(new ArtistInfoDto(artist, base64Image));
	        } else {
	            return Stream.empty();
	        }
	    }).collect(Collectors.toList());
	}

	/**
	 * 사용자가 선택한 가수(아티스트) 저장
	 */
	@Transactional
	public Long saveFavArtist(FavArtistReqDto requestDto) {
		Users user = usersJpaRepository.findById(Long.parseLong(requestDto.getFavChecklistId()))
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));

		FavCheckLists favCheckList = new FavCheckLists();
		favCheckList.setUser(user);
		favCheckList = favCheckListsJpaRepository.save(favCheckList);

		for (String favArtistStr : requestDto.getArtistIds()) {
			Musics musics = musicsJpaRepository.findByArtistFilePath(favArtistStr);
			if (musics == null)
				return -1L;
			FavArtists favArtists = new FavArtists();
			favArtists.setFavCheckList(favCheckList);
			favArtists.setArtistId(musics);
			favArtistsJpaRepository.save(favArtists);
		}
		return Long.parseLong(requestDto.getFavChecklistId());
	}
	

	/**
	 * get 가수(아티스트) 검색
	 */
	@Transactional(readOnly = true)
	public List<ArtistInfoDto> searchArtist(String keyword) {
		log.info("Artist searchArtist Service start...");
		List<Musics> artistSearch = musicsJpaRepository.findByDistinctArtist(keyword);
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
		
		List<FavArtists> favArtists = favArtistsJpaRepository.findByFavCheckList(favCheckList.get(1));
		
		Musics musics = musicsJpaRepository.findByArtistFilePath(favArtists.get(0).getArtistId());
		Musics musics2 = musicsJpaRepository.findByArtistFilePath(favArtists.get(1).getArtistId());
		
		favArtists.get(0).setArtistId(musics);
		favArtists.get(1).setArtistId(musics2);
		
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
