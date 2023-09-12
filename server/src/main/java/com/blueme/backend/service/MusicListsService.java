package com.blueme.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blueme.backend.dto.musiclistsdto.RecMusicListSaveDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.RecMusiclists;
import com.blueme.backend.model.entity.RecMusiclistDetails;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.repository.RecMusicListsJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-04
설명: 음악리스트 관련 서비스
*/

@Slf4j
@RequiredArgsConstructor
@Service
public class MusicListsService {

	private final UsersJpaRepository usersJpaRepository;
	private final RecMusicListsJpaRepository recMusicListsJpaRepository;
	private final MusicsJpaRepository musicsJpaRepository;

	/**
	 * post 추천음악리스트 등록
	 */
	@Transactional
	public Long save(RecMusicListSaveDto requestDto) {

		Users user = usersJpaRepository.findById(Long.parseLong(requestDto.getUserId()))
				.orElseThrow(() -> new IllegalArgumentException("User with id " + requestDto.getUserId() + " does not exist."));

		// id를 music Entity담은 리스트로 변환
		List<Musics> musics = requestDto.getMusicIds().stream()
				.map((id) -> musicsJpaRepository.findById(Long.parseLong(id))
						.orElseThrow(() -> new IllegalArgumentException("해당 음악ID를 찾을 수 없습니다")))
				.collect(Collectors.toList());

		// 음악을 RecMusiclistDetail 리스트로 변환
		List<RecMusiclistDetails> recMusicListDetail = musics.stream()
				.map((music) -> RecMusiclistDetails.builder().music(music).build()).collect(Collectors.toList());

		return recMusicListsJpaRepository.save(
				RecMusiclists.builder().user(user).title(requestDto.getTitle()).reason(requestDto.getReason())
						.recMusicListDetail(recMusicListDetail).build())
				.getId();
	}

}
