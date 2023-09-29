package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.FavCheckLists;
import com.blueme.backend.model.entity.FavGenres;

/**
 * FavGenres 테이블과 연결해주는 JPA Repositories
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-20
 */

@Repository
public interface FavGenresJpaRepository extends JpaRepository<FavGenres, Long> {
	
	/**
     * 즐겨찾기 체크리스트 ID에 따른 선호 장르 목록을 반환
     *
     * @param favCheckLists 즐겨찾기 체크리스트 ID 
     */
	List<FavGenres> findByFavCheckList(FavCheckLists favCheckLists);
	
}
