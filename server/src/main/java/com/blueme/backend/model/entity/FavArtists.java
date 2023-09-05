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
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FavArtists {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="fav_artist_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="artist_id", nullable = false)
	private Artists artist;
	
	@OneToOne
	@JoinColumn(name="fav_checklist_id")
	private FavCheckLists favCheckList;

	@Builder
	public FavArtists(Long id, Artists artist, FavCheckLists favCheckList) {
		this.id=id;
		this.artist = artist;
		this.favCheckList = favCheckList;
	}
}
