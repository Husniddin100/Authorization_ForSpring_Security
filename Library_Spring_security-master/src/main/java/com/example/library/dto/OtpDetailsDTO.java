package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OtpDetailsDTO {
    @NotBlank
    private String username;
    private LocalDateTime expireTime;
}
