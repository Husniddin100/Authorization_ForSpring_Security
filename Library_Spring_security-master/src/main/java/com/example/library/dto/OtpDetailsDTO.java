package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OtpDetailsDTO {
    private String username;
    private LocalDateTime expireTime;
}
