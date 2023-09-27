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

import com.blueme.backend.dto.genredto.FavGenreReqDto;
import com.blueme.backend.dto.genredto.GenreInfoDto;
import com.blueme.backend.model.entity.FavCheckLists;
import com.blueme.backend.model.entity.FavGenres;
import com.blueme.backend.model.entity.Genres;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.FavCheckListsJpaRepository;
import com.blueme.backend.model.repository.FavGenresJpaRepository;
import com.blueme.backend.model.repository.GenresJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.service.exception.UserNotFoundException;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * GenresService는 선호장르 관련 서비스 클래스입니다.
 * <p>
 * 이 클래스는 장르 추천, 선호장르 저장, 장르 검색, 선호장르 수정 기능을 처리합니다.
 * </p>
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-27
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class GenresService {

	private final GenresJpaRepository genresJpaRepository;
	private final UsersJpaRepository usersJpaRepository;
	private final FavGenresJpaRepository favGenresJpaRepository;
	private final FavCheckListsJpaRepository favCheckListsJpaRepository;

	/**
	 * 모든 장르를 조회합니다.
	 * 
	 * @return 장르 정보 목록 (List<GenreInfoDto>)
	 */
	@Transactional
	public List<GenreInfoDto> getAllGenre() {	
		return genresJpaRepository.findAll().stream().flatMap(genre -> {
			String base64Image = getBase64ImageForGenre(genre);
			if (base64Image != null) {
				return Stream.of(new GenreInfoDto(genre, base64Image));
			} else {
				return Stream.empty();
			}
		}).collect(Collectors.toList());
	}

	/**
	 * 사용자가 선택한 장르를 저장합니다.
	 * 
	 * @param requestDto 사용자가 선택한 장르 요청 DTO (FavGenreReqDto)
	 * @return 사용자 ID(Long)
	 */
	@Transactional
	public Long saveFavGenre(FavGenreReqDto requestDto) {
		log.info("Starting to save favorite genre : {}", requestDto);
		Long userId = Long.parseLong(requestDto.getFavChecklistId());
		Users user = usersJpaRepository.findById(userId)
		            .orElseThrow(() -> new UserNotFoundException(userId));
		
		FavCheckLists favCheckList = new FavCheckLists();
		favCheckList.setUser(user);
		favCheckList = favCheckListsJpaRepository.save(favCheckList);

		for (String favGenersStr : requestDto.getGenreIds()) {
			Genres genres = genresJpaRepository.findById(Long.parseLong(favGenersStr))
					.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장르"));

			FavGenres favGenres = new FavGenres();
			favGenres.setFavCheckList(favCheckList);
			favGenres.setGenre(genres);
			favGenresJpaRepository.save(favGenres);
			
			log.info("Saved favorite genres : {}", genres.getName());
		}
		return userId;
	}


	/**
	 * 사용자의 선호하는 장르를 수정합니다.
	 * 
	 * @param userId		사용자 ID (Long)
	 * @param newGenreIds	새로운 장르 ID 목록 (List<Long>)
	 * @return 사용자 ID (Long)
	 */
	@Transactional
	public Long updateFavGenre(Long userId, List<Long> newGenreIds) {
		List<FavCheckLists> favCheckList = favCheckListsJpaRepository.findByUserId(userId);
		
		List<FavGenres> favGenres = favGenresJpaRepository.findByFavCheckList(favCheckList.get(0));
		Genres genres = genresJpaRepository.findById(newGenreIds.get(0)).orElseThrow(null);
		Genres genres2 = genresJpaRepository.findById(newGenreIds.get(1)).orElseThrow(null);
	
		favGenres.get(0).setGenre(genres);
		favGenres.get(1).setGenre(genres2);
		
	    return userId;
	}
	
	/**
	 * 장르 이미지를 Base64 문자열로 변환합니다.
	 * 
	 * @param genre 장르 정보 (Genres)
	 * @return Base64 이미지 문자열 (String)
	 */
	public String getBase64ImageForGenre(Genres genre) {
		if (genre.getGenre_file_path() != null) {
			try {
				Path filePath = Paths.get("C:\\usr\\blueme\\genre\\" + genre.getGenre_file_path() + ".jpg");
				File file = filePath.toFile();
				ImageConverter<File, String> converter = new ImageToBase64();
				String base64 = null;
				base64 = converter.convert(file);
				return base64;
			} catch (IOException e) {
				log.info("error");
				log.info(e.getMessage());
			}
		}
		return null;
	}

}
