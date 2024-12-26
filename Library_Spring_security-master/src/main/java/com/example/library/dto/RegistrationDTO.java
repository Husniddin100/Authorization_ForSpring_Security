package com.example.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationDTO {
    private String name;
    private String surname;
    @NotBlank
    private String username;
    @Email(message = "email should be valid")
    private String email;
    @NotBlank
    private String password;
}
