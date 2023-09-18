package com.blueme.backend.model.entity;

import java.util.List;

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
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FavGenres {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fav_genre_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="genre_id")
	private Genres genre;
	
	@OneToOne
	@JoinColumn(name="fav_checklist_id")
	private FavCheckLists favCheckList;

	@Builder
	public FavGenres(Long id, Genres genre, FavCheckLists favCheckList) {
		this.id=id;
		this.genre = genre;
		this.favCheckList = favCheckList;
	}

	public void update(Genres genreIds) {
		this.genre=genreIds;
	}
	
	public Long getGenreId() {
		return this.genre.getId();
	}
	
	
	

}
