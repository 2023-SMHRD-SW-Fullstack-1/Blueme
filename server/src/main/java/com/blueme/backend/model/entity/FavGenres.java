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

/**
 * FavGenres 엔터티는 사용자의 선호 장르 정보를 표현합니다.
 * <p>
 * 이 엔터티는 선호 장르의 ID, 해당 장르에 대한 정보, 그리고 즐겨찾기 체크리스트에 대한 정보를 속성으로 가지고 있습니다.
 * </p>
 *
 * @author 손지연
 * @version 1.0
 * @since 2023-09-06
 */

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

	/**
	* update 메서드는 FavGenres 객체의 genre 필드를 갱신합니다.
	*
	* @param genreIds 갱신할 Genres 객체 
	*/
	public void update(Genres genreIds) {
		this.genre=genreIds;
	}
	
	/**
	* getGenreId 메서드는 현재 FavGenres 객체가 참조하고 있는 장르의 ID를 반환합니다.
	*
	* @return 현재 참조하고 있는 Genres 객체의 ID 반환  
	*/
	public Long getGenreId() {
		return this.genre.getId();
	}
	
	
	

}
