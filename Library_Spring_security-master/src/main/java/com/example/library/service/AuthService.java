package com.example.library.service;

import com.example.library.dto.AuthDTO;
import com.example.library.dto.ProfileDTO;
import com.example.library.dto.RegistrationDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ProfileDTO login(AuthDTO profile);

    Boolean registration(RegistrationDTO registrationDTO);

    String emailVerification(String jwt);

    String verifyOtp(String otp);
}
