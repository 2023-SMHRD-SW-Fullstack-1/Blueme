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

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class RecMusiclist extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rec_musiclist_id")
	private Long id;
	
	private String title;
	
	@OneToMany(mappedBy = "rec_musiclist", cascade = CascadeType.ALL)
	private List<RecMusiclistDetail> recMusicListDetail;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
}
