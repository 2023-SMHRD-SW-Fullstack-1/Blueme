package com.blueme.backend.model.entity;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javax.persistence.Transient;

import com.blueme.backend.config.FilePathConfig;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자가 저장한 음악 목록 엔터티.
 * <p>
 * 각 사용자는 여러 개의 음악 목록을 가질 수 있으며,
 * 각 음악 목록은 여러 개의 세부 항목(SavedMusiclistDetails)을 가질 수 있습니다.
 * </p>
 *
 * @author 김혁
 * @version 1.0
 * @since 2023-09-06
 */
@Getter
@NoArgsConstructor
@Entity
public class SavedMusiclists {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "saved_musiclist_id")
  private Long id;

  @Column(length = 100)
  private String title;

  @Column(length = 200)
  private String imgPath;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "saved_musiclist_id")
  private List<SavedMusiclistDetails> savedMusiclistDetails;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private Users user;

  @Builder
  public SavedMusiclists(String title, Users user, List<SavedMusiclistDetails> savedMusiclistDetails, String imgPath) {
    this.title = title;
    this.user = user;
    this.savedMusiclistDetails = savedMusiclistDetails;
    this.imgPath = imgPath;
  }

  @Transient
  public String getJacketFile() {
    try {
      Path filePath = Paths.get(imgPath);
      File file = filePath.toFile();
      ImageConverter<File, String> converter = new ImageToBase64();
      String base64 = null;
      base64 = converter.convert(file);
      return base64;
    } catch (Exception e) {
      throw new RuntimeException("재킷파일 전송 실패", e);
    }
  }
}
