package com.blueme.backend.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass // BaseEntity를 상속한 엔터티들은 아래 필드들을 컬럼으로 추가 인식
@EntityListeners(AuditingEntityListener.class) // 자동으로 값 매핑 기능 추가
public class BaseEntity {

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	// @CreatedBy
	// @Column(updatable = false)
	// private String createdBy;

	@LastModifiedDate
	private LocalDateTime modifiedAt;

	// @LastModifiedBy
	// private String modifiedBy;
	//

}
