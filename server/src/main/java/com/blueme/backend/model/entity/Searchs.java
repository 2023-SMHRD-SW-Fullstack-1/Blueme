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

/*
 * 작성자 : 김혁
 * 작성일 : 2023-09-15
 * 설명   : 검색목록 저장 엔터티
 */

@Getter
@NoArgsConstructor
@Entity
public class Searchs extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "search_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "music_id")
  private Musics music;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private Users user;

  @Builder
  public Searchs(Musics music, Users user) {
    this.music = music;
    this.user = user;
  }

}
