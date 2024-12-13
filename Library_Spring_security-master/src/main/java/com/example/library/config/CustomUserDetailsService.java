package com.example.library.config;


import com.example.library.entity.Profile;
import com.example.library.exp.AppBadException;
import com.example.library.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Profile> optional = profileRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Bad Credentials ");
        }

        Profile profile = optional.get();
        return new CustomUserDetails(profile.getId(), profile.getEmail(),
                profile.getPassword(), profile.getStatus(), profile.getRole());
    }
}
