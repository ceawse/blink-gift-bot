package com.blinkgift.service.handler;

import com.blinkgift.service.bot.MessageService;
import com.blinkgift.service.bot.PriceService;
import com.blinkgift.telegram.Bot;
import com.blinkgift.util.KeyBoardUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
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

        try {
            bot.execute(new AnswerCallbackQuery(callbackQuery.getId()));
        } catch (Exception ignored) {}

        if (data.equals("MAIN_CRYPTO")) {
            try { bot.execute(messageService.deleteMessage(chatId, messageId)); } catch (Exception ignored) {}
            return messageService.createMessage(chatId, "💱⤵️ *Выберите криптовалюту которую отдаете:*",
                    KeyBoardUtils.createCryptoMenu("GIVE_SELECTED", null));
        }

        if (data.equals("MAIN_BANKS")) {
            try { bot.execute(messageService.deleteMessage(chatId, messageId)); } catch (Exception ignored) {}
            return messageService.createMessage(chatId, "🏦⤵️ *Выберите банк который отдаете:*",
                    KeyBoardUtils.createBanksMenu("GIVE_SELECTED", null));
        }

        if (data.equals("MAIN_PAY_SYSTEMS")) {
            try { bot.execute(messageService.deleteMessage(chatId, messageId)); } catch (Exception ignored) {}
            return messageService.createMessage(chatId, "💳⤵️ *Выберите платежную систему которую отдаете:*",
                    KeyBoardUtils.createPaySystemsMenu("GIVE_SELECTED", null));
        }

        if (data.equals("MAIN_CASH")) {
            try { bot.execute(messageService.deleteMessage(chatId, messageId)); } catch (Exception ignored) {}
            return messageService.createMessage(chatId, "💵⤵️ *Выберите город для обмена наличных:*",
                    KeyBoardUtils.createCashMenu("GIVE_SELECTED", null));
        }

        if (data.startsWith("GIVE_SELECTED:")) {
            try { bot.execute(messageService.deleteMessage(chatId, messageId)); } catch (Exception ignored) {}
            String selectedGive = data.split(":")[1];
            return messageService.createMessage(chatId, "💱⤴️ *Выберите валюту которую хотите получить:*",
                    KeyBoardUtils.createCryptoMenu("RECEIVE_SELECTED", selectedGive));
        }

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
                            "Максимальная сумма обмена %s = *132000000*\n\n" +
                            "Для продолжения обмена перейдите на сайт http://localhost:3000/",
                    giveCoin, receiveCoin,
                    giveCoin.split(" ")[0], rate, receiveCoin.split(" ")[0],
                    receiveCoin.split(" ")[0], giveCoin, giveCoin
            );

            try { bot.execute(messageService.deleteMessage(chatId, messageId)); } catch (Exception ignored) {}
            return messageService.createMessage(chatId, text, KeyBoardUtils.createExchangeFinalMenu(giveCoin, receiveCoin));
        }

        if (data.equals("BACK_TO_MAIN")) {
            try { bot.execute(messageService.deleteMessage(chatId, messageId)); } catch (Exception ignored) {}
            return messageService.createMessage(chatId, "💰 *Выберите раздел:*", KeyBoardUtils.createMainMenu());
        }

        return null;
    }
}