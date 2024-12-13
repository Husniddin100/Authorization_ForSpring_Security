package com.example.library.repository;

import com.example.library.entity.Profile;
import com.example.library.enums.ProfileRole;
import com.example.library.enums.ProfileStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile,String> {

    @Modifying
    @Transactional
    @Query("update Profile set status=?2 where id=?1")
    void updateStatus(String id, ProfileStatus profileStatus);

    Optional<Profile> findByEmail(String email);

    Optional<Profile> findByEmailAndPassword(String email, String encode);

    Optional<Profile> findByRole(ProfileRole role);

    Optional<Profile> findByUsernameAndPassword(String username, String encode);
}
