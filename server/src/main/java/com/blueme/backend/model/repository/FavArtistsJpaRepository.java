package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.FavArtists;
import com.blueme.backend.model.entity.FavCheckLists;
import com.blueme.backend.model.entity.Musics;

/**
 * FavArtists 테이블과 연결해주는 JPA Repositories
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-20
 */

@Repository
public interface FavArtistsJpaRepository extends JpaRepository<FavArtists, Long> {
	
	/**
     * 즐겨찾기 체크리스트에 따른 선호 가수 목록을 반환
     *
     * @param favCheckList 즐겨찾기 체크리스트  
     */
	List<FavArtists> findByFavCheckList(FavCheckLists favCheckList);

	/**
     * 즐겨찾기 체크리스트 ID에 해당하는 음악 목록을 반환
     *
     * @param favCheckLists 즐겨찾기 체크리스트 
     */
	List<Musics> findById(FavCheckLists favCheckLists);
	
}
