package com.blueme.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class FavCheckLists extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fav_checklist_id")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private Users user;

	@Builder
	public FavCheckLists(Long id, Users users) {
		this.id = id;
		this.users = users;
	}
	
	
}
