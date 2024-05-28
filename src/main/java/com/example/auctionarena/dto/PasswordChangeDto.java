package com.example.auctionarena.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PasswordChangeDto {
    private String email;

    // private String currentPassword;
    private String newPassword;
    private String checkPassword;
}
