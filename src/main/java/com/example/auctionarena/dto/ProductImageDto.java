package com.example.auctionarena.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDto {

  private Long inum;
  private String uuid;
  private String imgName;
  private String path;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;

  // 저장된 파일의 위치
  public String getImageURL() {
    String fullPath = "";

    try {
      fullPath = URLEncoder.encode(path + "/" + uuid + "_" + imgName, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return fullPath;
  }
  // 썸네일 이미지는 굳이 필요없을듯

  // public String getThumbImageURL() {
  // String thumbFullPath = "";

  // try {
  // thumbFullPath = URLEncoder.encode(path + "/s_" + uuid + "_" + imgName,
  // "UTF-8");
  // } catch (UnsupportedEncodingException e) {
  // e.printStackTrace();
  // }
  // return thumbFullPath;
  // }
}
