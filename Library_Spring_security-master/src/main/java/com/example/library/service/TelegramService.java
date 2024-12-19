package com.example.library.service;

import com.example.library.entity.TelegramUser;
import com.example.library.exp.AppBadException;
import com.example.library.repository.TelegramUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class TelegramService extends TelegramLongPollingBot {

    private final TelegramUserRepository repo;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = String.valueOf(update.getMessage().getChatId());
            String username = update.getMessage().getFrom().getUserName();

            if (username == null || username.isEmpty()) {
                sendMessage(chatId, "Please set up a username on your Telegram account!");
            } else {
                Optional<TelegramUser> userOptional = repo.findByUsername(username);
                if (userOptional.isPresent()) {
                    TelegramUser user = userOptional.get();
                    if (!chatId.equals(user.getChatId())) {
                        user.setChatId(chatId);
                        repo.save(user);
                    }
                } else {
                    TelegramUser newUser = new TelegramUser();
                    newUser.setChatId(chatId);
                    newUser.setUsername(username);
                    repo.save(newUser);
                }
                sendMessage(chatId, "You are now connected to the authentication system.");
            }
        }
    }

    public void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getChatId(String username) {
        Optional<TelegramUser> optional = repo.findByUsername(username);
        if (optional.isEmpty()) {
            throw new AppBadException("user not found");
        }
        return optional.get().getChatId();
    }

    public

    @Override
    String getBotUsername() {
        return "@authorization_test1_bot";
    }

    @Override
    public String getBotToken() {
        return "";
    }

}
