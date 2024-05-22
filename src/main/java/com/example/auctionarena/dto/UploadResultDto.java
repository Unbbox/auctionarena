package com.example.auctionarena.dto;

import java.io.Serializable;
import java.net.URLEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResultDto implements Serializable {
    private String folderPath;
    private String uuid;
    private String fileName;

    // 저장된 파일 위치
    public String getImageURL() {
        String fullPath = "";

        try {
            fullPath = URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fullPath;
    }
}
