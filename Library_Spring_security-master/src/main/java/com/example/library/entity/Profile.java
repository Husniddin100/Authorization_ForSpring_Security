package com.example.library.entity;

import com.example.library.enums.ProfileRole;
import com.example.library.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "username",unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "profile_status")
    private ProfileStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_role")
    private ProfileRole role;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
