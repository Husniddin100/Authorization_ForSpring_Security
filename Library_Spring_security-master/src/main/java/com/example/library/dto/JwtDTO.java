package com.example.library.dto;

import com.example.library.enums.ProfileRole;
import lombok.Data;

@Data
public class JwtDTO {
    private Integer id;
    private String email;
    private ProfileRole role;

    public JwtDTO(String email, ProfileRole role) {
        this.email = email;
        this.role = role;
    }
}
