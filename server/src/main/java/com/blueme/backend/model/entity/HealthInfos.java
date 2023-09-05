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
public class HealthInfos extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="health_info_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user;
	
	private int avgHearRate;

	@Builder
	public HealthInfos(Users user, int avgHearRate) {
		this.user = user;
		this.avgHearRate = avgHearRate;
	}
	
	

}
