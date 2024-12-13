package com.example.library.service;

import com.example.library.dto.AuthDTO;
import com.example.library.dto.RegistrationDTO;
import com.example.library.dto.ProfileDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ProfileDTO> login(AuthDTO profile);

    ResponseEntity<String> logout(String token);

    ResponseEntity<Boolean> registration(RegistrationDTO registrationDTO);

    ResponseEntity<String> emailVerification(String jwt);
}
