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
작성자: 김혁
날짜(수정포함): 2023-09-14
설명: 사용자가 저장한음악 상세 엔터티
*/

@Getter
@NoArgsConstructor
@Entity
public class SavedMusiclistDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "saved_musiclist_detail_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "saved_musiclist_id", insertable = false, updatable = false)
  private SavedMusiclists savedMusiclists;

  @ManyToOne
  @JoinColumn(name = "music_id")
  private Musics music;

  public SavedMusiclistDetails(Musics music) {
    this.music = music;
  }

}
