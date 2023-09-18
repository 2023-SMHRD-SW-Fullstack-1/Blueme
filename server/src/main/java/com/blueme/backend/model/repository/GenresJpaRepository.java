package com.blueme.backend.model.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.FavGenres;
import com.blueme.backend.model.entity.Genres;

@Repository
public interface GenresJpaRepository extends JpaRepository<Genres, Long> {

//	List<Genres> findById(List<FavGenres> favGenres);
	
//	Genres findByGenreId(Long genreId);

}
