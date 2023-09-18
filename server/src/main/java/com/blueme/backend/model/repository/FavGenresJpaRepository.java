package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.FavCheckLists;
import com.blueme.backend.model.entity.FavGenres;
import com.blueme.backend.model.entity.Genres;

@Repository
public interface FavGenresJpaRepository extends JpaRepository<FavGenres, Long> {
	
	List<FavGenres> findByFavCheckList(FavCheckLists favCheckLists);
	List<FavGenres> findByFavCheckList(Long favCheckLists);
	
	void deleteByFavCheckListId(Long favCheckListId);
}
