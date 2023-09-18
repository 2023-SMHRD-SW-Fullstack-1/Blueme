package com.blueme.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blueme.backend.dto.savedMusiclistsdto.SavedMusiclistsGetResDto;
import com.blueme.backend.dto.savedMusiclistsdto.SavedMusiclistsResDto;
import com.blueme.backend.dto.savedMusiclistsdto.SavedMusiclistsSaveReqDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.SavedMusiclistDetails;
import com.blueme.backend.model.entity.SavedMusiclists;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.repository.SavedMusiclistsJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.service.exception.MusicNotFoundException;
import com.blueme.backend.service.exception.SaveMusiclistNotFoundException;
import com.blueme.backend.service.exception.UserNotFoundException;
import com.blueme.backend.utils.Base64ToImage;

import lombok.RequiredArgsConstructor;

/*
작성자: 김혁
날짜(수정포함): 2023-09-14
설명: 저장한음악 관련 서비스
*/
@RequiredArgsConstructor
@Service
public class SavedMusiclistsService {

        private final UsersJpaRepository usersJpaRepository;
        private final SavedMusiclistsJpaRepository savedMusiclistsJpaRepository;
        private final MusicsJpaRepository musicsJpaRepository;

        /*
         * get 저장음악리스트 조회
         */
        @Transactional(readOnly = true)
        public List<SavedMusiclistsResDto> getSavedMusiclists(String userId) {
                return savedMusiclistsJpaRepository
                                .findByUserId(Long.parseLong(userId)).stream().map(SavedMusiclistsResDto::new)
                                .collect(Collectors.toList());
        }

        /*
         * get 저장음악리스트 상세조회
         */
        @Transactional(readOnly = true)
        public SavedMusiclistsGetResDto getSavedMusiclistDetail(String savedMusiclistId) {
                return new SavedMusiclistsGetResDto(
                                savedMusiclistsJpaRepository.findById(Long.parseLong(savedMusiclistId))
                                                .orElseThrow(() -> new SaveMusiclistNotFoundException(
                                                                Long.parseLong(savedMusiclistId))));
        }

        /*
         * post 저장음악리스트 등록
         */
        @Transactional
        public Long save(SavedMusiclistsSaveReqDto request) {
                Users user = usersJpaRepository.findById(request.parsedUserId())
                                .orElseThrow(() -> new UserNotFoundException(request.parsedUserId()));

                List<Musics> musics = request.getMusicIds().stream()
                                .map((id) -> musicsJpaRepository.findById(Long.parseLong(id))
                                                .orElseThrow(() -> new MusicNotFoundException(Long.parseLong(id))))
                                .collect(Collectors.toList());
                if (musics.isEmpty()) {
                        return -1L;
                }

                List<SavedMusiclistDetails> savedMusiclistDetails = musics.stream()
                                .map(SavedMusiclistDetails::new).collect(Collectors.toList());
                if (request.getImage() != null) {
                        Base64ToImage decoder = new Base64ToImage();
                        String imgPath = decoder.convertBase64ToImage(request.getImage());
                        return savedMusiclistsJpaRepository.save(
                                        SavedMusiclists.builder()
                                                        .title(request.getTitle())
                                                        .user(user)
                                                        .imgPath(imgPath)
                                                        .savedMusiclistDetails(savedMusiclistDetails)
                                                        .build())
                                        .getId();
                } else {
                        return savedMusiclistsJpaRepository.save(
                                        SavedMusiclists.builder()
                                                        .title(request.getTitle())
                                                        .user(user)
                                                        .savedMusiclistDetails(savedMusiclistDetails)
                                                        .build())
                                        .getId();
                }

        }

}
