package com.blinkgift.service.bot;

import com.blinkgift.service.handler.CallbackQueryHandler;
import com.blinkgift.service.handler.CommandHandler;
import com.blinkgift.telegram.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class UpdateDispatcher {
    private final CallbackQueryHandler queryHandler;
    private final CommandHandler commandHandler;

    public BotApiMethod<?> distribute(Update update, Bot bot) {
        if (update.hasCallbackQuery()) {
            return queryHandler.answer(update.getCallbackQuery(), bot);
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            return commandHandler.answer(update);
        }
        return null;
    }
}