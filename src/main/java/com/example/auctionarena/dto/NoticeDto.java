package com.example.auctionarena.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NoticeDto {

    private Long nno;

    @NotBlank(message = "제목 입력")
    private String title;

    @NotBlank(message = "내용 입력")
    private String content;

    private Long mid;

    private String writerNickname;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    @Builder.Default
    private List<NoticeImageDto> noticeImageDtos = new ArrayList<>();
}
