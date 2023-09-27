package com.blueme.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 테마의 태그 엔터티입니다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-27
 */
@Entity
@Getter
@NoArgsConstructor
public class ThemeTags {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "theme_tag_id")
  private Long id;

  @Column(length = 50)
  private String name;

  @Builder
  public ThemeTags(String name) {
    this.name = name;
  }

}
