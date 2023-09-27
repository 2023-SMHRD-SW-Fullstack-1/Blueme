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

/**
 * ArtistsService는 선호가수(아티스트) 관련 서비스 클래스입니다.
 * <p>
 * 이 클래스는 가수 추천, 선호가수 저장, 가수 검색, 선호가수 수정 기능을 처리합니다.
 * </p>
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-27
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
	 * 모든 가수를 조회합니다.
	 * 
	 * @return 가수 정보 목록(List)
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
	 * 사용자가 선택한 가수를 저장합니다.
	 * 
	 * @param requestDto 사용자가 선택한 가수 요청 DTO (FavArtistReqDto)
	 * @return 사용자 ID (Long)
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
	 * 가수를 키워드로 검색합니다.
	 * 
	 * @param keyword 검색 키워드 (String)
	 * @return 검색된 가수 정보 목록 (List)
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
	 * 사용자의 선호하는 가수를 수정합니다.
	 * 
	 * @param userId		사용자 ID (Long)
	 * @param newArtistIds	새로운 선호하는 가수 ID 목록 (List<Long>)
	 * @return 사용자 ID (Long)
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
	 * 가수의 이미지를 Base64 형식으로 변환합니다.
	 * @param music 가수 정보 (Musics)
	 * @return Base64 형식의 이미지 (String)
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
