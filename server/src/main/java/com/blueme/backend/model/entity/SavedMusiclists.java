package com.blueme.backend.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
작성자: 김혁
날짜(수정포함): 2023-09-14
설명: 사용자가 저장한음악 목록 엔터티
*/

@Getter
@NoArgsConstructor
@Entity
public class SavedMusiclists {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "saved_musiclist_id")
  private Long id;

  private String title;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "saved_musiclist_id")
  private List<SavedMusiclistDetails> savedMusiclistDetails;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private Users user;

  @Builder
  public SavedMusiclists(String title, Users user, List<SavedMusiclistDetails> savedMusiclistDetails) {
    this.title = title;
    this.user = user;
    this.savedMusiclistDetails = savedMusiclistDetails;
  }

}
