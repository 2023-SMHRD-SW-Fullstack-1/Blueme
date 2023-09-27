package com.blueme.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * FavCheckLists 엔터티는 사용자의 선호 장르와 선호 가수를 관리하기 위한 체크리스트 정보를 표현합니다.
 * <p>
 * 이 엔터티는 즐겨찾기 체크리스트의 ID와, 해당 체크리스트를 소유한 사용자에 대한 정보를 속성으로 가지고 있습니다.
 * </p>
 *
 * @author 손지연
 * @version 1.0
 * @since 2023-09-06
 */

@Setter
@Getter
@NoArgsConstructor
@Entity
public class FavCheckLists extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fav_checklist_id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private Users user;

	@Builder
	public FavCheckLists(Long id, Users user) {
		this.id = id;
		this.user = user;
	}

}
