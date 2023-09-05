package com.blueme.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.Musics;

@Repository
public interface MusicsJpaRepository extends JpaRepository<Musics, Long>{

}
