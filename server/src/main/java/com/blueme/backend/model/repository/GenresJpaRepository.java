package com.blueme.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.Genres;

/**
 * Genres 테이블과 연결해주는 JPA Repositories
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-20
 */

@Repository
public interface GenresJpaRepository extends JpaRepository<Genres, Long> {

}
