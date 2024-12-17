package com.example.library.repository;

import com.example.library.entity.TelegramUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TelegramUserRepository extends CrudRepository<TelegramUser, String> {
    Optional<TelegramUser> findByUsername(String username);
}
