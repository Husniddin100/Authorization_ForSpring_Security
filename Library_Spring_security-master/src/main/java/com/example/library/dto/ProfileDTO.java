package com.example.library.dto;

import com.example.library.enums.ProfileRole;
import com.example.library.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProfileDTO {
    private String id;
    private String name;
    private String surname;
    private String password;
    private String username;
    private String email;
    private ProfileStatus status;
    private LocalDateTime createdDate;
    private ProfileRole role;
    private String jwt;
}
