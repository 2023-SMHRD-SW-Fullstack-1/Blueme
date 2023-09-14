package com.blueme.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.FavGenres;

@Repository
public interface FavGenresJpaRepository extends JpaRepository<FavGenres, Long> {
}
