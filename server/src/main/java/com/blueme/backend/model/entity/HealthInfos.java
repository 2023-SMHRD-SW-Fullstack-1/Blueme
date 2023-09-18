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

/**
 * 사용자의 건강 정보를 표현하는 엔터티 클래스입니다.
 * <p>
 * 이 클래스는 사용자의 심박수, 칼로리 소모량, 속도, 걸음 수, 위치(위도와 경도) 등의 정보를 저장합니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-06
 */
@Getter
@NoArgsConstructor
@Entity
public class HealthInfos extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "health_info_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	private String heartrate;

	private String calorie;

	private String speed;

	private String step;

	// 위도
	private String lat;

	// 경도
	private String lon;

	@Builder
	public HealthInfos(Users user, String heartrate, String calorie, String speed, String step, String lat, String lon) {
		this.user = user;
		this.heartrate = heartrate;
		this.calorie = calorie;
		this.speed = speed;
		this.step = step;
		this.lat = lat;
		this.lon = lon;
	}

}
