package com.blueme.backend.dto.recmusiclistsdto;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.blueme.backend.model.entity.RecMusiclistDetails;
import com.blueme.backend.model.entity.RecMusiclists;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;

import lombok.Getter;

@Getter
public class RecMusiclistsDetailResDto {

  private Long recMusiclistDetailId;
  private Long musicId;
  private String img;
  private String musicTitle;
  private String musicArtist;
  private String musicAlbum;
  private String musicGenre;

  public RecMusiclistsDetailResDto(RecMusiclistDetails detailResDto) {
    this.recMusiclistDetailId = detailResDto.getId();
    this.musicId = detailResDto.getMusic().getId();
    this.img = detailResDto.getMusic().getJacketFile();
    this.musicTitle = detailResDto.getMusic().getTitle();
    this.musicAlbum = detailResDto.getMusic().getArtist();
    this.musicAlbum = detailResDto.getMusic().getAlbum();
    this.musicGenre = detailResDto.getMusic().getGenre1();
  }

}
