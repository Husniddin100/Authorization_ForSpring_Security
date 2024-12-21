package com.example.library.service.impl;

import com.example.library.dto.*;
import com.example.library.entity.Profile;
import com.example.library.entity.TelegramUser;
import com.example.library.enums.ProfileRole;
import com.example.library.enums.ProfileStatus;
import com.example.library.exp.AppBadException;
import com.example.library.repository.ProfileRepository;
import com.example.library.repository.TelegramUserRepository;
import com.example.library.service.AuthService;
import com.example.library.service.MailSenderService;
import com.example.library.service.TelegramService;
import com.example.library.util.JWTUtil;
import com.example.library.util.MDUtil;
import com.example.library.util.SpringSecurityUtil;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final MailSenderService mailSenderService;

    private final ProfileRepository profileRepository;

    private final TelegramUserRepository tgRepository;

    private final TelegramService tgService;

    private final Map<String, OtpDetailsDTO> otpStorage = new HashMap<>();


    @Override
    public Boolean registration(RegistrationDTO dto) {
        Optional<Profile> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optional.get());
            } else {
                throw new AppBadException("email exists");
            }
        }
        Profile entity = toEntity(dto);

        String jwt = JWTUtil.encode(entity.getEmail(), entity.getRole());
        String text = "Hello. \n To complete registration please link to the following link\n"
                + "http://localhost:8081/auth/verification/email/" + jwt;
        mailSenderService.sendEmail(dto.getEmail(), "Complete registration", text);
        return true;
    }

    public String emailVerification(String jwt) {
        try {
            JwtDTO jwtDTO = JWTUtil.decodeForSpringSecurity(jwt);
            Optional<Profile> optional = profileRepository.findByEmail(jwtDTO.getEmail());
            if (optional.isEmpty()) {
                throw new AppBadException("account not found");
            }
            Profile entity = optional.get();
            if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
                throw new AppBadException("profile in wrong status");
            }
            profileRepository.updateStatus(entity.getId(), ProfileStatus.ACTIVE);
        } catch (JwtException e) {
            throw new AppBadException("please try again");
        }
        return null;
    }


    @Override
    public ProfileDTO login(AuthDTO profile) {
        Optional<Profile> optional;

        if (profile.getUsername() != null && !profile.getUsername().isEmpty()) {
            optional = profileRepository.findByUsernameAndPassword(profile.getUsername(), MDUtil.encode(profile.getPassword()));
        } else {
            throw new AppBadException("Username or email is required");
        }

        Profile entity = optional.orElseThrow(() -> new AppBadException("email or username or password wrong"));

        Optional<TelegramUser> tgoptional = tgRepository.findByUsername(profile.getUsername());

        if (tgoptional.isEmpty()) {
            throw new AppBadException("user not connect telegram bot");
        }
        if (entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("account registration not finish");
        }

        String chatId = tgService.getChatId(tgoptional.get().getUsername());
        if (chatId != null) {
            tgService.sendMessage(chatId, "Your OTP is: " + otp(profile.getUsername()));
        }
        ProfileDTO dto = new ProfileDTO();
        dto.setUsername(entity.getUsername());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setJwt(JWTUtil.encode(entity.getEmail(), entity.getRole()));
        return dto;
    }

    @Override
    public String verifyOtp(String otp) {
        String username = SpringSecurityUtil.getCurrentUser().getUsername();
        String email = SpringSecurityUtil.getCurrentUser().getEmail();
        ProfileRole role = SpringSecurityUtil.getCurrentUser().getRole();

        OtpDetailsDTO detailsOtp = otpStorage.get(otp);

        if (detailsOtp == null) {
            throw new AppBadException("Invalid OTP or OTP not generated.");
        }
        if (detailsOtp.getExpireTime().isBefore(LocalDateTime.now())) {
            String chatId = tgService.getChatId(username);
            otpStorage.remove(otp);
            tgService.sendMessage(chatId, "otp expired");
            throw new AppBadException("otp expired");
        }

        if (username.equals(detailsOtp.getUsername())) {
            String chatId = tgService.getChatId(username);
            String jwt = JWTUtil.encode(email, role);
            tgService.sendMessage(chatId, " login successfully");
            otpStorage.remove(otp);
            return " login successfully " + jwt;
        } else {
            throw new AppBadException("invalid user");
        }
    }

    public String otp(String username) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(2);
        otpStorage.put(otp, new OtpDetailsDTO(username, expirationTime));
        return otp;
    }

    public Profile toEntity(RegistrationDTO dto) {
        Profile entity = new Profile();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MDUtil.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);
        return entity;
    }
}
