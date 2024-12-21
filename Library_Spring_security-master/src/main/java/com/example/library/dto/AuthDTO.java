package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class AuthDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
