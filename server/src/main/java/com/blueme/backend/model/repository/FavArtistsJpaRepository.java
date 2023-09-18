package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.FavArtists;
import com.blueme.backend.model.entity.FavCheckLists;
import com.blueme.backend.model.entity.FavGenres;
import com.blueme.backend.model.entity.Musics;

@Repository
public interface FavArtistsJpaRepository extends JpaRepository<FavArtists, Long> {
	
	List<FavArtists> findByFavCheckList(FavCheckLists favCheckList);

//	List<Musics> findByFavCheckList1(FavCheckLists id);

	List<Musics> findById(FavCheckLists favCheckLists);
	
}
