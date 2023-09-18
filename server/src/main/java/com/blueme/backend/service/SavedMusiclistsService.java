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

/**
 * 저장된 음악 관련 서비스 클래스입니다.
 * <p>
 * 이 클래스에서는 저장된 음악 목록의 조회, 상세 조회, 등록 기능을 제공합니다.
 * </p>
 *
 * @author 김혁
 * @version 1.0
 * @since 2023-09-14
 */
@RequiredArgsConstructor
@Service
public class SavedMusiclistsService {

        private final UsersJpaRepository usersJpaRepository;
        private final SavedMusiclistsJpaRepository savedMusiclistsJpaRepository;
        private final MusicsJpaRepository musicsJpaRepository;

        /**
         * 특정 사용자의 모든 저장된 음악 목록을 조회합니다.
         *
         * @param userId 사용자 ID (String)
         * @return 해당 사용자의 모든 저장된 음악 목록 (SavedMusiclistsResDto 리스트)
         */
        @Transactional(readOnly = true)
        public List<SavedMusiclistsResDto> getSavedMusiclists(String userId) {
                return savedMusiclistsJpaRepository
                                .findByUserId(Long.parseLong(userId)).stream().map(SavedMusiclistsResDto::new)
                                .collect(Collectors.toList());
        }

        /**
         * 특정 저장된 음악 목록의 상세 정보를 조회합니다.
         *
         * @param savedMusiclistId 조회하려는 저장된 음악 목록의 ID (String)
         * @return 해당 저장된 음악 목록의 상세 정보 (SavedMusiclistsGetResDto)
         */
        @Transactional(readOnly = true)
        public SavedMusiclistsGetResDto getSavedMusiclistDetail(String savedMusiclistId) {
                return new SavedMusiclistsGetResDto(
                                savedMusiclistsJpaRepository.findById(Long.parseLong(savedMusiclistId))
                                                .orElseThrow(() -> new SaveMusiclistNotFoundException(
                                                                Long.parseLong(savedMusiclistId))));
        }

        /**
         * 새로운 저장된 음악 목록을 등록합니다.
         *
         * @param request 등록하려는 저장된 음악 목록 정보가 담긴 요청 객체 (SavedMusiclistsSaveReqDto)
         * @return 등록된 새로운 저장된 음악 목록의 ID (Long). 만약 요청 처리에 실패한다면 null 반환
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
                        return null;
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
