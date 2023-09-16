package com.blueme.backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blueme.backend.dto.artistdto.ArtistInfoDto;
import com.blueme.backend.dto.genredto.GenreInfoDto;
import com.blueme.backend.dto.usersdto.UserProfileDto;
import com.blueme.backend.dto.usersdto.UsersDeleteDto;
import com.blueme.backend.dto.usersdto.UsersRegisterDto;
import com.blueme.backend.dto.usersdto.UsersUpdateDto;
import com.blueme.backend.model.entity.FavArtists;
import com.blueme.backend.model.entity.FavCheckLists;
import com.blueme.backend.model.entity.FavGenres;
import com.blueme.backend.model.entity.Genres;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.entity.Users.UserRole;
import com.blueme.backend.model.repository.FavArtistsJpaRepository;
import com.blueme.backend.model.repository.FavCheckListsJpaRepository;
import com.blueme.backend.model.repository.FavGenresJpaRepository;
import com.blueme.backend.model.repository.GenresJpaRepository;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;
import com.nimbusds.jose.util.Base64;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁, 손지연
날짜(수정포함): 2023-09-13
설명: 회원 관련 서비스
*/

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersService {

	private final UsersJpaRepository usersJpaRepository;
	private final GenresJpaRepository genresJpaRepository;
	private final MusicsJpaRepository musicsJpaRepository;
	private final FavArtistsJpaRepository favArtistsJpaRepository;
	private final FavGenresJpaRepository favGenresJpaRepository;
	private final FavCheckListsJpaRepository favCheckListsJpaRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final GenresService genresService;
	private final ArtistsService artistsService;

	/**
	 * 유저 등록
	 */
	@Transactional
	public Long signUp(UsersRegisterDto usersRegisterDto) throws Exception {
		log.info("userService method save start...");
		Optional<Users> users = usersJpaRepository.findByEmail(usersRegisterDto.getEmail());

		if (users.isPresent()) {
			throw new Exception("이미 존재하는 이메일입니다.");
		} else {
//		
//		if (usersJpaRepository.findByNickname(usersRegisterDto.getNickname()).isPresent()) {
//			throw new Exception("이미 존재하는 닉네임입니다.");
//		}

			Users user = Users.builder().email(usersRegisterDto.getEmail())
					.password(bCryptPasswordEncoder.encode(usersRegisterDto.getPassword()))
					.nickname(usersRegisterDto.getNickname()).refreshToken(usersRegisterDto.getRefreshToken()).build();
			user.setPlatformType("blueme");
			user.setRole(UserRole.USER);

			usersJpaRepository.save(user);
			return user.getId();
		}
	}

//	public Long save(UsersRegisterDto requestDto) {
//		log.info("userService method save start...");
//		Users user = usersJpaRepository.findByEmail(requestDto.getEmail());
//		return (user == null) ? usersJpaRepository.save(requestDto.toEntity()).getId() : -1L;
//	}

	/*
	 * @Transactional public UUID save(UserRegisterDto requestDto) {
	 * log.info("userService method save start..."); User user =
	 * usersJpaRepository.findByEmail(requestDto.getEmail()); if (user == null) {
	 * User newUser = User.builder() .email(requestDto.getEmail())
	 * .password(requestDto.getPassword()) .nickname(requestDto.getNickname())
	 * .accessToken(requestDto.getAccessToken()) .build();
	 * newUser.setPlatformType("blueme"); newUser.setActiveStatus("Y");
	 * newUser.setRole(User.UserRole.ADMIN); UUID userId =
	 * usersJpaRepository.save(newUser).getId(); return userId; } else { return
	 * UUID.fromString("-1"); // 등록 실패 시 -1 반환 (UUID 형식으로 변환) } }
	 */

	/**
	 * 유저 로그인확인
	 */
	// @Transactional
	// public Long login(UsersLoginDto requestDto) {
	// log.info("userService method login start...");
	// Users user =
	// usersJpaRepository.findByEmailAndPasswordAndActiveStatus(requestDto.getEmail(),
	// requestDto.getPassword(), "Y");
	// return (user == null) ? -1L : user.getId();
	// }
	/*
	 * @Transactional public UUID login(UserLoginDto requestDto) {
	 * log.info("userService method login start..."); User user =
	 * usersJpaRepository.findByEmailAndPasswordAndActiveStatus(
	 * requestDto.getEmail(), requestDto.getPassword(), "Y"); if (user != null) {
	 * return user.getId(); } else { return UUID.fromString("-1"); // 로그인 실패 시 -1 반환
	 * (UUID 형식으로 변환) } }
	 */

	/**
	 * delete 유저 탈퇴 ( 실패시 -1 반환, 성공시 유저의고유ID반환 ), activeStatus 컬럼 "N"으로 변경
	 */

	@Transactional
	public Long deactivate(UsersDeleteDto requestDto) {
		log.info("userService method delete start...");
		Users user = usersJpaRepository.findByEmailAndActiveStatus(requestDto.getEmail(), "Y");
		if (user == null) {
			log.info("No matching user found...");
			return -1L; // or throw an exception
		}
		/* 비밀번호 검증 */
		boolean isMatch = bCryptPasswordEncoder.matches(requestDto.getPassword(), user.getPassword());

		if (!isMatch) {
			log.info("No Matching password found...");
			return -1L;
		} else {
			user.setActiveStatus("N");
			return user.getId();
		}

	}

	/**
	 * patch 유저 수정
	 */

	@Transactional
	public Long update(UsersUpdateDto requestDto) {
		log.info("userService method update start...");
		Users user = usersJpaRepository.findByEmail(requestDto.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email=" + requestDto.getEmail()));

		user.update(requestDto.getNickname(), bCryptPasswordEncoder.encode(requestDto.getPassword()),
				requestDto.getImgUrl());
		return user.getId();
//	    Users user = usersJpaRepository.findByEmail(requestDto.getEmail());
//			user.setPassword(requestDto.getPassword());
//			user.setNickname(requestDto.getNickname());
//			System.out.println(user.getNickname());
//			return user.getId();
//	    return null;
	}

	/**
	 * 	마이페이지
	 */
	public List<UserProfileDto> myprofile(String userId) {
		 log.info("Getting profile for userId : {}", userId);

		 // 사용자프로필이미지, 사용자플랫폼타입
		 // 선택한 장르의 이미지(경로), 이름, 아이디
		 // 선택한 장르의 이미지(경로), 이름, 아이디(경로로 대체)

		 Users user = usersJpaRepository.findById(Long.parseLong(userId))
				 .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
		 
		 List<FavCheckLists> favCheckLists = favCheckListsJpaRepository.findByUserId(user.getId());
		 
		 if (favCheckLists.size() < 2) {
		        throw new IllegalArgumentException("Insufficient FavCheckList data for user id: " + userId);
		    }
		 
		 List<FavGenres> favGenres = favGenresJpaRepository.findByFavCheckList(favCheckLists.get(0));	
		 List<FavArtists> favArtists = favArtistsJpaRepository.findByFavCheckList(favCheckLists.get(1));	
		 
		 List<UserProfileDto> userProfileDtos = new ArrayList<>();
		 List<GenreInfoDto> genreDtos= new ArrayList<>();
		 List<ArtistInfoDto> artistDtos= new ArrayList<>();
		 
		 for(FavGenres fav : favGenres) {    
			Genres genre = genresJpaRepository.findById(fav.getGenre().getId()).orElse(null);
		 	System.out.println();
		 	if(genre != null){
		 	    GenreInfoDto genreInfoDTO=new GenreInfoDto(genre,genresService.getBase64ImageForGenre(genre));
		 	    genreDtos.add(genreInfoDTO);
		 	}
		 }
		 
		 for(FavArtists fav : favArtists){
			 Musics music = musicsJpaRepository.findByArtistFilePath(fav.getArtistId().getArtistFilePath());
		 	if(music!=null){
		 	    ArtistInfoDto artistInfoDTO=new ArtistInfoDto(music, artistsService.getBase64ImageForArtist(music));
		 	    artistDtos.add(artistInfoDTO);
		    }
		 }

		 UserProfileDto userProfileDTODto=new UserProfileDto(user,genreDtos,artistDtos);
		 userProfileDtos.add(userProfileDTODto);

		 return userProfileDtos;	
	}


	

	}
