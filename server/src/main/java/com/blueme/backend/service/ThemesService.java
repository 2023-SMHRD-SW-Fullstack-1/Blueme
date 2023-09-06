package com.blueme.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blueme.backend.dto.musiclistsdto.RecMusicListSaveDto;
import com.blueme.backend.dto.themesdto.ThemeDetailsResDto;
import com.blueme.backend.dto.themesdto.ThemeSaveReqDto;
import com.blueme.backend.dto.themesdto.ThemelistDetailResDto;
import com.blueme.backend.dto.themesdto.ThemelistResDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.RecMusiclistDetails;
import com.blueme.backend.model.entity.RecMusiclists;
import com.blueme.backend.model.entity.ThemeMusiclists;
import com.blueme.backend.model.entity.Themes;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.repository.ThemesJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ThemesService {

  private final ThemesJpaRepository themesJpaRepository;
  private final MusicsJpaRepository musicsJpaRepository;
  
  /**
	 *  post 테마 등록 (음악과 함께)
	 */
	@Transactional
	public Long saveThemes(ThemeSaveReqDto requestDto) {
    log.info("ThemeService post registerTheme start...");
		// musicIds 를 music Entity담긴 리스트로 변환
		List<Musics> musics = requestDto.getMusicIds().stream()
				.map((id) -> musicsJpaRepository.findById(Long.parseLong(id))
				.orElseThrow(() -> new IllegalArgumentException("해당 음악ID를 찾을 수 없습니다"))).collect(Collectors.toList());
    
    List<ThemeMusiclists> themeMusicList = new ArrayList<>();
    for(int i=0; i<musics.size(); i++){
      themeMusicList.add(ThemeMusiclists.builder().music(musics.get(i)).build());
    }

    return themesJpaRepository.save(Themes.builder().title(requestDto.getTitle()).content(requestDto.getContent()).themeMusicList(themeMusicList).build()).getId();
	}

  /**
	 *  get 모든 테마 조회
	 */
  @Transactional
  public List<ThemelistResDto> getAllThemes(){
    List<Themes> themes = themesJpaRepository.findAll();
    return themes.stream().map(ThemelistResDto::new).collect(Collectors.toList());
  }

  /**
	 *  get 단일 테마 상세 조회 (음악 id 포함)
	 */
  @Transactional
  public List<ThemeDetailsResDto> getThemeById(Long id){
    Themes themes = themesJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 테마 아이디가 없습니다."));
    List<ThemeDetailsResDto> res = new ArrayList<ThemeDetailsResDto>();
    for(ThemeMusiclists t : themes.getThemeMusicList()){
      res.add(new ThemeDetailsResDto(themes, t));
    }
    return res;
  }
}


	