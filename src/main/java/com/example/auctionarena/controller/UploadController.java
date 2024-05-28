package com.example.auctionarena.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.auctionarena.dto.UploadResultDto;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/upload")
@Log4j2
@Controller
public class UploadController {

    // apllication.properties 설정한 변수 가져오기
    @Value("${com.example.upload.path}")
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDto>> postUpload(MultipartFile[] uploadFiles, String folderType) {
        List<UploadResultDto> uploadResultDtos = new ArrayList<>();

        for (MultipartFile multipartFile : uploadFiles) {
            if (!multipartFile.getContentType().startsWith("image")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String oriName = multipartFile.getOriginalFilename();
            String fileName = oriName.substring(oriName.lastIndexOf("//") + 1);
            // log.info("파일정보 - 전체경로: {}", oriName);
            log.info("파일정보 - 전체경로: {}", fileName);

            // 폴더 생성
            if (folderType.contains("notice")) {
                folderType = "notice";
            } else {
                folderType = "product";
            }
            String saveFolderPath = makeFolder(folderType);
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + saveFolderPath + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);

            try {
                // 원본 파일 저장
                multipartFile.transferTo(savePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 저장된 파일 정보 객체 생성 후 리스트에 추가
            uploadResultDtos.add(new UploadResultDto(saveFolderPath, uuid, fileName));
            log.info("파일 등록 완료{}", fileName);
        }
        return new ResponseEntity<>(uploadResultDtos, HttpStatus.OK);
    }

    private String makeFolder(String folderType) {
        String dateStr = folderType + "/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderStr = dateStr.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderStr);
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return folderStr;
    }

    // 이미지 전송 후 보여주기
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName) {
        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "utf-8");

            File file = new File(uploadPath + File.separator + srcFileName);
            log.info("file >> {}", file);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("result >> {}", result);
        return result;
    }

    @PostMapping("/remove")
    public ResponseEntity<Boolean> postRemove(String filePath) {
        log.info("파일 삭제 요청 {}", filePath);

        String srcFileName = null;

        try {
            srcFileName = URLDecoder.decode(filePath, "utf-8");

            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete(); // 원본 파일 제거

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
