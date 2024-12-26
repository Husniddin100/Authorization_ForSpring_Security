package com.example.library.dto;

import com.example.library.enums.ProfileRole;
import com.example.library.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProfileDTO {
    private Long id;
    private String name;
    private String surname;
    @NotBlank
    private String password;
    @NotBlank
    private String username;
    @Email(message = "email should be valid")
    private String email;

    private ProfileStatus status;
    private LocalDateTime createdDate;
    private ProfileRole role;
    private String jwt;
}
