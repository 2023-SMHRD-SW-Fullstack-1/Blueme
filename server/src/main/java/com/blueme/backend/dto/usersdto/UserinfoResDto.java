package com.blueme.backend.dto.usersdto;

import com.blueme.backend.model.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserinfoResDto {
  private String userId;
  private String nickname;
  private String email;
  private String platformType;
  private String role;
  private String createdAt;

  public UserinfoResDto(Users user) {
    this.userId = user.getId().toString();
    this.nickname = user.getNickname();
    this.email = user.getEmail();
    this.platformType = user.getPlatformType();
    this.role = user.getRole().toString();
    this.createdAt = user.getCreatedAt().toString();
  }

}
