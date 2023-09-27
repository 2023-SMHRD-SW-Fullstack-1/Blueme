package com.blueme.backend.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import com.blueme.backend.model.entity.UserRole;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.FavArtistsJpaRepository;
import com.blueme.backend.model.repository.FavCheckListsJpaRepository;
import com.blueme.backend.model.repository.FavGenresJpaRepository;
import com.blueme.backend.model.repository.GenresJpaRepository;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.service.exception.EmailAlreadyExistsException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁, 손지연
날짜(수정포함): 2023-09-20
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
	public Long signUp(UsersRegisterDto usersRegisterDto){
		log.info("userService method save start...");
		
		Optional<Users> users = usersJpaRepository.findByEmail(usersRegisterDto.getEmail());
		if (users.isPresent()) {
			throw new EmailAlreadyExistsException(usersRegisterDto.getEmail());
		} else {
//		if (usersJpaRepository.findByNickname(usersRegisterDto.getNickname()).isPresent()) {
//			throw new Exception("이미 존재하는 닉네임입니다.");
//		}
			Users user = Users.builder().email(usersRegisterDto.getEmail())
					.password(bCryptPasswordEncoder.encode(usersRegisterDto.getPassword()))
					.nickname(usersRegisterDto.getNickname()).refreshToken(usersRegisterDto.getRefreshToken())
					.platformType("blueme").role(UserRole.USER).build();

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
	 * @throws IOException 
	 */

	@Transactional
	public Long update(Optional<Users> user, UsersUpdateDto requestDto) throws IOException {
		log.info("userService method update start!");
		
		Users users = usersJpaRepository.findByEmail(user.get().getEmail())
				.orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email=" + user.get().getEmail()));

		String filePath = null;
	    if (requestDto.getImg_url() != null) {
	        filePath = saveImage(requestDto.getImg_url(), requestDto.getNickname());
	    }
	    
		if (requestDto.getPassword() != null) {
			users.update(requestDto.getNickname(), bCryptPasswordEncoder.encode(requestDto.getPassword()),filePath);
		}else {
			users.update(requestDto.getNickname(), filePath);
		}
		return -1L;
	}

	/**
	 * 	마이페이지
	 */
	public List<UserProfileDto> myprofile(Long userId) {
		 log.info("Getting profile for userId : {}", userId);
		 Users user = usersJpaRepository.findById(userId)
				 .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
		 
		 List<FavCheckLists> favCheckLists = favCheckListsJpaRepository.findByUserId(user.getId());
		 List<FavGenres> favGenres = favGenresJpaRepository.findByFavCheckList(favCheckLists.get(0));	
		 List<FavArtists> favArtists = favArtistsJpaRepository.findByFavCheckList(favCheckLists.get(1));	
		 
		 List<UserProfileDto> userProfileDtos = new ArrayList<>();
		 List<GenreInfoDto> genreDtos= new ArrayList<>();
		 List<ArtistInfoDto> artistDtos= new ArrayList<>();
		 
		 for(FavGenres fav : favGenres) {    
			Genres genre = genresJpaRepository.findById(fav.getGenre().getId()).orElse(null);
		 	if(genre != null){
		 	    GenreInfoDto genreInfoDTO=new GenreInfoDto(genre,genresService.getBase64ImageForGenre(genre));
		 	    genreDtos.add(genreInfoDTO);
		 	}
		 }
		 
		 for(FavArtists fav : favArtists){
			 Musics music = musicsJpaRepository.findTop1ByArtistFilePath(fav.getArtistId().getArtistFilePath());
		 	if(music!=null){
		 	    ArtistInfoDto artistInfoDTO=new ArtistInfoDto(music, artistsService.getBase64ImageForArtist(music));
		 	    artistDtos.add(artistInfoDTO);
		    }
		 }

		 UserProfileDto userProfileDTODto=new UserProfileDto(user,genreDtos,artistDtos);
		 userProfileDtos.add(userProfileDTODto);

		 return userProfileDtos;	
	}
	
	/**
	 * Base64 형태의 이미지 데이터를 받아서 파일로 저장하고, 해당 파일의 경로를 반환합니다.
	 *
	 * @param base64Image Base64 인코딩된 이미지 데이터
	 * @param nickname 사용자 닉네임
	 * @return 저장된 이미지 파일의 경로
	 * @throws IOException 파일 입출력 중 발생할 수 있는 예외
	 */
	private String saveImage(String base64Image, String nickname) throws IOException {
	    // base64 to multipart
	    // 저장할 파일 경로를 지정합니다.
		String fileNameWithUUID = UUID.randomUUID().toString() + "_" + nickname;
		String fileName =  fileNameWithUUID + ".jpg";
		String filePath = "/usr/blueme/profileImg/" + fileName;

		File file = new File(filePath);
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] decodedBytes = decoder.decode(base64Image.getBytes());

		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(decodedBytes);
		fileOutputStream.close();

		return filePath;
	}
}


	

