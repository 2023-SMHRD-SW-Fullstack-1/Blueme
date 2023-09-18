package com.blueme.backend.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

/**
 * 모든 엔터티들의 기본적인 필드를 정의하는 베이스 엔터티 클래스입니다.
 * <p>
 * 이 클래스는 JPA Auditing 기능을 활용하여 생성 시간과 수정 시간을 자동으로 관리합니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-02
 */
@Getter
@MappedSuperclass // BaseEntity를 상속한 엔터티들은 아래 필드들을 컬럼으로 추가 인식
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	/**
	 * 엔터티가 생성된 시간입니다.
	 * 이 필드는 엔터티가 처음 저장될 때 자동으로 현재 시간이 설정되며, 이후에는 변경되지 않습니다.
	 */
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	// @CreatedBy
	// @Column(updatable = false)
	// private String createdBy;

	/**
	 * 엔터티가 마지막으로 수정된 시간입니다.
	 * 이 필드는 엔터티가 저장될 때마다 자동으로 현재 시간이 설정됩니다.
	 */
	@LastModifiedDate
	private LocalDateTime modifiedAt;

	// @LastModifiedBy
	// private String modifiedBy;
	//

}
