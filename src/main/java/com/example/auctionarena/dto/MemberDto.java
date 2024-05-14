package com.example.auctionarena.dto;

import java.time.LocalDateTime;

import com.example.auctionarena.constant.MemberRole;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDto {

    private Long mid;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String phoneNumber;

    private String addr;

    private String zonecode;

    private MemberRole memberRole;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
