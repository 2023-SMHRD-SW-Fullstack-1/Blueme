package com.blueme.backend.service.exception;

public class MusicNotFoundException extends RuntimeException {
  private Long musicId;

  public MusicNotFoundException(Long musicId) {
    super("Could not find music with id: " + musicId);
    this.musicId = musicId;
  }

  public Long getMusicId() {
    return this.musicId;
  }
}