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
 * FavArtists 엔터티는 사용자가 선호하는 가수 정보를 표현합니다.
 * <p>
 * 이 엔터티는 선호하는 가수의 ID, 가수의 고유 ID를 대체할 파일경로명, 그리고 회원체크리스트에 대한 정보를 속성으로 가지고 있습니다.
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
public class FavArtists {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="fav_artist_id")
	private Long id;
	
	/**
	 * artistId는 선호하는 아티스트의 음악 정보를 참조합니다.
	 * <p>
	 * 이 필드는 Musics 엔터티를 참조하며, 'artist_file_path'라는 컬럼으로 조인됩니다. 
	 * 데이터베이스 상에서는 'music_id' 값으로 저장되며, 해당 ID를 통해 아티스트의 음악 정보와 연결됩니다.
	 * </p>
	 */
	@ManyToOne
	@JoinColumn(name="artist_file_path")
	private Musics artistId;
	
	@OneToOne
	@JoinColumn(name="fav_checklist_id")
	private FavCheckLists favCheckList;
	
	@Builder
	public FavArtists(Long id, Musics artistId, FavCheckLists favCheckList) {
		this.id = id;
		this.artistId = artistId;
		this.favCheckList = favCheckList;
	}

}
