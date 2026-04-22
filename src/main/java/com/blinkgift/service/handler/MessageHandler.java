package com.blinkgift.service.handler;

import com.blinkgift.service.bot.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class MessageHandler {
    private final MessageService messageService;

    public BotApiMethod<?> answer(Message message) {
        // Можно отвечать, что бот работает только через меню
        return messageService.createMessage(message.getChatId(), "Пожалуйста, используйте кнопки меню для навигации.", null);
    }
}