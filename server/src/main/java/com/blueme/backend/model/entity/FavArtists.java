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
	@JoinColumn(name="artist_file_path")
	private Musics artistId;	// 가수이미지파일경로(ex:16055)을 id 대체?
	
	@OneToOne
	@JoinColumn(name="fav_checklist_id")
	private FavCheckLists favCheckList;
	
	@Builder
	public FavArtists(Long id, Musics artistId, FavCheckLists favCheckList) {
		this.id = id;
		this.artistId = artistId;
		this.favCheckList = favCheckList;
	}

	public void setArtistId(Musics artistFilePath) {
		this.artistId=artistFilePath;
	}

	public void setArtistId(String string) {
		// TODO Auto-generated method stub
		
	}


}
