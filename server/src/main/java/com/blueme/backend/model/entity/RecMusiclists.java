package com.blueme.backend.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class RecMusiclists extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rec_musiclist_id")
	private Long id;
	
	private String title;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="rec_musiclist_id") // 셀프조인
	private List<RecMusiclistDetails> recMusicListDetail;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user;
	
	@Builder
	public RecMusiclists(String title, Users user, List<RecMusiclistDetails> recMusicListDetail) {
		this.title = title;
		this.user = user;
		this.recMusicListDetail = recMusicListDetail;
	}
	
}
