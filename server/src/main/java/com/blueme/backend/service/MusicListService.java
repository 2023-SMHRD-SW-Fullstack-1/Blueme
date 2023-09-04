package com.blueme.backend.service;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blueme.backend.dto.musiclistdto.RecMusicListSaveDto;
import com.blueme.backend.model.entity.Music;
import com.blueme.backend.model.entity.RecMusiclist;
import com.blueme.backend.model.entity.RecMusiclistDetail;
import com.blueme.backend.model.entity.User;
import com.blueme.backend.model.repository.MusicJpaRepository;
import com.blueme.backend.model.repository.RecMusicListJpaRepository;
import com.blueme.backend.model.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MusicListService {
	
	private final UserJpaRepository userJpaRepository;
	private final RecMusicListJpaRepository recMusicListJpaRepository;
	private final MusicJpaRepository musicJpaRepository;
	
	/**
	 *  post 추천음악리스트 등록
	 */
	@Transactional
	public Long save(RecMusicListSaveDto requestDto) {
		
		User user = userJpaRepository.findById(Long.parseLong(requestDto.getUserId()))
				.orElseThrow(() -> new IllegalArgumentException("User with id " + requestDto.getUserId() + " does not exist."));
		
		// musicIds 를 music Entity담긴 리스트로 변환
		List<Music> musics = requestDto.getRecommendedMusicIds().stream()
				.map((id) -> musicJpaRepository.findById(Long.parseLong(id))
				.orElseThrow(() -> new IllegalArgumentException("해당 음악ID를 찾을 수 없습니다"))).collect(Collectors.toList());
		
		// 음악을 RecMusiclistDetail 리스트로 변환
		List<RecMusiclistDetail> recMusicListDetail = musics.stream()
				.map((music) -> RecMusiclistDetail.builder().music(music).build()).collect(Collectors.toList());

		return recMusicListJpaRepository.save(RecMusiclist.builder().user(user).title(requestDto.getTitle()).recMusicListDetail(recMusicListDetail).build()).getId();
	}
	
}
