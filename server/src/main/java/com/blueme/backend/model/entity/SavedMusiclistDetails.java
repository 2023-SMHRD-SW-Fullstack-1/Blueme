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
 * 저장된음악상세 엔터티입니다.
 * <p>
 * 이 클래스는 사용자가 저장한 음악 상세를 가집니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-06
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
