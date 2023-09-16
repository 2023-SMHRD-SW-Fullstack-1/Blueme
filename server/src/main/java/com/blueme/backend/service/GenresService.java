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
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 손지연
날짜(수정포함): 2023-09-16
설명: 회원가입 시 선호장르 관련 서비스
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
	 * 모든 장르 조회 (회원가입 시)
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
	 * 사용자가 선택한 장르 저장
	 */
	@Transactional
	public Long saveFavGenre(FavGenreReqDto requestDto) {
		Users user = usersJpaRepository.findById(Long.parseLong(requestDto.getFavChecklistId()))
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));
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
		}
		return Long.parseLong(requestDto.getFavChecklistId());
	}

	/**
	 * 장르 이미지 변환
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
