package com.example.auctionarena.dto;

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
public class CommentDto {

    private Long commentNo;
    private String text;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // 멤버
    private Long mid;
    private String nickname;

    // 제품
    private Long pno;
}
