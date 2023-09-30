package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.FavCheckLists;

/**
 * FavCheckLists 테이블과 연결해주는 JPA Repositories
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-20
 */

@Repository
public interface FavCheckListsJpaRepository extends JpaRepository<FavCheckLists, Long> {

	/**
     * 사용자 ID에 따른 첫번째 즐겨찾기 체크리스트를 반환
     *
     * @param userId 사용자 ID 
     */
	FavCheckLists findFirstByUserId(Long userId);

	/**
     *사용자 ID에 따른 모든 즐겨찾기 체크리스트 목록을 반환
     *
     *@param userId 사용자 ID 
     */
	List<FavCheckLists> findByUserId(Long userId);

}