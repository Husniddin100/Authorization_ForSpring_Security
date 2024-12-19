package com.example.library.service;

import com.example.library.dto.AuthDTO;
import com.example.library.dto.ProfileDTO;
import com.example.library.dto.RegistrationDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<ProfileDTO> login(AuthDTO profile);

    ResponseEntity<Boolean> registration(RegistrationDTO registrationDTO);

    ResponseEntity<String> emailVerification(String jwt);

    ResponseEntity<String> verifyOtp(String otp);
}
