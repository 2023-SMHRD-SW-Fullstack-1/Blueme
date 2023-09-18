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
import com.blueme.backend.service.exception.MusicNotFoundException;
import com.blueme.backend.service.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * MusicListsService는 음악목록 서비스 클래스입니다.
 * 추천음악 목록 등록 기능을 제공합니다.
 * RecMusicList서비스 로 이전으로 사용하지 않습니다.
 *
 * @author 김혁
 * @version 1.0
 * @since 2023-09-13
 */

@RequiredArgsConstructor
@Service
public class MusicListsService {

	private final UsersJpaRepository usersJpaRepository;
	private final RecMusicListsJpaRepository recMusicListsJpaRepository;
	private final MusicsJpaRepository musicsJpaRepository;

	/**
	 * 제목, 이유, 사용자ID, musicIds 배열을 기반으로 추천리스트를 등록합니다.
	 * 
	 * @param requestDto 추천음악 저장 요청 Dto(RecMusicListSaveDto)
	 * @return 저장된 추천음악 ID (Long)
	 */
	@Transactional
	public Long save(RecMusicListSaveDto requestDto) {

		Users user = usersJpaRepository.findById(Long.parseLong(requestDto.getUserId()))
				.orElseThrow(() -> new UserNotFoundException(Long.parseLong(requestDto.getUserId())));

		List<Musics> musics = requestDto.getMusicIds().stream()
				.map((id) -> musicsJpaRepository.findById(Long.parseLong(id))
						.orElseThrow(() -> new MusicNotFoundException(Long.parseLong(id))))
				.collect(Collectors.toList());

		List<RecMusiclistDetails> recMusicListDetail = musics.stream()
				.map((music) -> RecMusiclistDetails.builder().music(music).build()).collect(Collectors.toList());

		return recMusicListsJpaRepository.save(
				RecMusiclists.builder().user(user).title(requestDto.getTitle()).reason(requestDto.getReason())
						.recMusicListDetail(recMusicListDetail).build())
				.getId();
	}

}
