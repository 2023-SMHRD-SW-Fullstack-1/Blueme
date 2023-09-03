package com.blueme.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class RecMusiclistDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rec_musiclist_detail_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="rec_musiclist_id", insertable=false, updatable=false)
	private RecMusiclist recMusiclist;
	
	@ManyToOne
	@JoinColumn(name="music_id")
	private Music music;
	
	@Builder
	public RecMusiclistDetail(RecMusiclist recMusiclist,Music music){
		this.recMusiclist = recMusiclist;
		this.music = music;
	}
	
}
