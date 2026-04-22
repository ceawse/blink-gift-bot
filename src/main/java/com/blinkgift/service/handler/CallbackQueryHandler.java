package com.blinkgift.service.handler;

import com.blinkgift.service.bot.MessageService;
import com.blinkgift.service.bot.PriceService;
import com.blinkgift.telegram.Bot;
import com.blinkgift.util.KeyBoardUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
@RequiredArgsConstructor
public class CallbackQueryHandler {
    private final MessageService messageService;
    private final PriceService priceService;

    public BotApiMethod<?> answer(CallbackQuery callbackQuery, Bot bot) {
        String data = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();

        try { bot.execute(messageService.deleteMessage(chatId, messageId)); } catch (Exception ignored) {}

        // 1. Шаг: Выбор валюты которую ОТДАЕМ
        if (data.equals("STEP_GIVE")) {
            return messageService.createMessage(chatId, "💱⤵️ *Выберите валюту которую хотите отдать:*",
                    KeyBoardUtils.createCryptoMenu("GIVE_SELECTED", null));
        }

        // 2. Шаг: Выбор валюты которую ПОЛУЧАЕМ
        if (data.startsWith("GIVE_SELECTED:")) {
            String selectedGive = data.split(":")[1];
            return messageService.createMessage(chatId, "💱⤴️ *Выберите валюту которую хотите получить:*",
                    KeyBoardUtils.createCryptoMenu("RECEIVE_SELECTED", selectedGive));
        }

        // 3. Шаг: Финальное окно
        if (data.startsWith("RECEIVE_SELECTED:")) {
            String[] parts = data.split(":");
            String receiveCoin = parts[1];
            String giveCoin = parts[2];

            double rate = priceService.getExchangeRate(giveCoin, receiveCoin);

            String text = String.format(
                    "Ваша скидка: *0.05%%*\n" +
                            "Вы хотите обменять:\n*%s* на *%s*\n\n" +
                            "Курс обмена:\n*1 %s = %.4f %s*\n\n" +
                            "Резерв составляет:\n*19995381 %s*\n\n" +
                            "Минимальная сумма обмена %s = *30*\n" +
                            "Максимальная сумма обмена %s = *132000000*",
                    giveCoin, receiveCoin,
                    giveCoin.split(" ")[0], rate, receiveCoin.split(" ")[0],
                    receiveCoin.split(" ")[0], giveCoin, giveCoin
            );

            return messageService.createMessage(chatId, text, KeyBoardUtils.createExchangeFinalMenu(giveCoin, receiveCoin));
        }

        // Возврат в меню
        if (data.equals("BACK_TO_MAIN")) {
            return messageService.createMessage(chatId, "💰 *Выберите раздел:*", KeyBoardUtils.createMainMenu());
        }

        return null;
    }
}