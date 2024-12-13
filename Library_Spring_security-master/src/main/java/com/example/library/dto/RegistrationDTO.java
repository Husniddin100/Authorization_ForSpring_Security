package com.example.library.dto;

import lombok.Data;

@Data
public class RegistrationDTO {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
}
