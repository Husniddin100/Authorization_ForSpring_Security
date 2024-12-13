package com.example.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@RequiredArgsConstructor
@Service
public class TelegramService extends TelegramLongPollingBot {

    private final Map<String, String> userChatIds = new HashMap<>();



    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = String.valueOf(update.getMessage().getChatId());
            String username = update.getMessage().getFrom().getUserName();

            if (username != null) {
                userChatIds.put(username, chatId);
                sendMessage(chatId, "Thank you for connecting! You can now log in.");
            }
        }
    }

    public String getChatId(String username) {
        return userChatIds.get(username);
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
    @Override
    public String getBotUsername() {
        return "@authorization_test1_bot";
    }
    @Override
    public String getBotToken() {
        return "8092240171:AAHts6ur2e82hYDRz2E0DGh4YQBxoh8Hprs";
    }

}
