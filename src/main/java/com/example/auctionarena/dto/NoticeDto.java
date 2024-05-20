package com.example.auctionarena.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private String title;

    private String content;

    private Long mid;

    private String writerNickname;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    @Builder.Default
    private List<NoticeImageDto> noticeImageDtos = new ArrayList<>();
}
