package com.blueme.backend.dto.gptdto;

import com.blueme.backend.model.entity.Musics;

import lombok.Getter;

@Getter
public class ChatGptMusicsDto {

  private String id;
  private String title;
  private String artist;
  private String genre;

  public ChatGptMusicsDto(Musics music) {
    this.id = music.getId().toString();
    this.title = music.getTitle();
    this.artist = music.getArtist();
    this.genre = music.getGenre1();
  }

  public String toString() {
    return "(" + id + "," + title + "," + artist + "," + genre + ")";
  }
}
