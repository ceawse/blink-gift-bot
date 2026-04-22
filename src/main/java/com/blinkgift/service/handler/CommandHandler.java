package com.blinkgift.service.handler;

import com.blinkgift.service.bot.MessageService;
import com.blinkgift.util.KeyBoardUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class CommandHandler {
    private final MessageService messageService;

    public BotApiMethod<?> answer(Update update) {
        String text = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        if (text != null && text.startsWith("/start")) {
            return messageService.createMessage(
                    chatId,
                    "👋 *Добро пожаловать в обменный сервис!*\n\nВыберите раздел валюты, которую хотите отдать:",
                    KeyBoardUtils.createMainMenu()
            );
        }
        return null;
    }
}