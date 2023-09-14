package com.blueme.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueme.backend.model.entity.SavedMusiclists;

public interface SavedMusiclistsJpaRepository extends JpaRepository<SavedMusiclists, Long> {

}
