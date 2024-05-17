package com.example.auctionarena.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;

import com.example.auctionarena.entity.Notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NoticeImageDto {

    private Long ninum;

    private String nuuid;

    private String nimgName;

    private String npath;

    private Notice notice;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // 저장된 파일 위치
    public String getImageURL() {
        String fullPath = "";

        try {
            fullPath = URLEncoder.encode(npath + "/" + nuuid + "_notice_" + nimgName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return fullPath;
    }

}
