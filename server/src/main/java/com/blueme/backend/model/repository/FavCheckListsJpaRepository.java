package com.blueme.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueme.backend.model.entity.FavCheckLists;
import com.blueme.backend.model.entity.Users;

@Repository
public interface FavCheckListsJpaRepository extends JpaRepository<FavCheckLists, Long> {
	
	
	FavCheckLists findAllByUser(Users user);

	List<FavCheckLists> findByUser(Users user);
	
	List<FavCheckLists> findByUserId(Long long1);

}