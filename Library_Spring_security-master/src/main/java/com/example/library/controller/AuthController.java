package com.example.library.controller;

import com.example.library.dto.AuthDTO;
import com.example.library.dto.ProfileDTO;
import com.example.library.dto.RegistrationDTO;
import com.example.library.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO authDTO) {
        return authService.login(authDTO);
    }

    @PostMapping("/registration")
    public ResponseEntity<Boolean> registration(@RequestBody RegistrationDTO dto) {
        return authService.registration(dto);
    }

    @GetMapping("/verification/email/{jwt}")
    public ResponseEntity<String> emailVerification(@PathVariable("jwt") String jwt) {
        return authService.emailVerification(jwt);
    }

    @PostMapping("/verify-otp/{otp}")
    public ResponseEntity<String> otp(@PathVariable String otp) {
        return authService.verifyOtp(otp);
    }

}
