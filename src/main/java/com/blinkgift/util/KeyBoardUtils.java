package com.blinkgift.util;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class KeyBoardUtils {

    // Главное меню
    public static InlineKeyboardMarkup createMainMenu() {
        return createMarkup(List.of(
                List.of(createButton("🪙 Криптовалюта", "STEP_GIVE")),
                List.of(createButton("🏦 Банки", "MAIN_BANKS")),
                List.of(createButton("💳 Платёжные системы", "MAIN_PAY_SYSTEMS")),
                List.of(createButton("💵 Наличные", "MAIN_CASH"))
        ));
    }

    // Меню выбора валют (используется для ОТДАЮ и ПОЛУЧАЮ)
    // prefix: шаг (GIVE или RECEIVE)
    // extraData: если это шаг RECEIVE, то тут лежит имя первой выбранной монеты
    public static InlineKeyboardMarkup createCryptoMenu(String prefix, String extraData) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        String[] coins = {"BNB BEP20", "Bitcoin", "Cardano (ADA)", "Chainlink (LINK)", "Cosmos (ATOM)", "Dash",
                "Dogecoin", "Ethereum", "Litecoin", "Solana", "Toncoin", "Tron", "USDT TRC20", "USD Coin ERC20"};

        for (int i = 0; i < coins.length; i += 2) {
            String callback1 = prefix + ":" + coins[i] + (extraData != null ? ":" + extraData : "");
            String callback2 = prefix + ":" + coins[i+1] + (extraData != null ? ":" + extraData : "");
            rows.add(List.of(createButton(coins[i], callback1), createButton(coins[i+1], callback2)));
        }

        rows.add(List.of(createButton("⬅️ Назад", "BACK_TO_MAIN")));
        return createMarkup(rows);
    }

    // Финальное меню с переходом на сайт
    public static InlineKeyboardMarkup createExchangeFinalMenu(String giveCoin, String receiveCoin) {
        String siteUrl = "https://your-exchange-site.com/?from=" + giveCoin + "&to=" + receiveCoin;

        InlineKeyboardButton giveBtn = new InlineKeyboardButton();
        giveBtn.setText("Отдать X кол-во " + giveCoin.split(" ")[0]);
        giveBtn.setUrl(siteUrl);

        InlineKeyboardButton receiveBtn = new InlineKeyboardButton();
        receiveBtn.setText("Получить X кол-во " + receiveCoin.split(" ")[0]);
        receiveBtn.setUrl(siteUrl);

        return createMarkup(List.of(
                List.of(giveBtn, receiveBtn),
                List.of(createButton("⬅️ Назад", "STEP_GIVE")),
                List.of(createButton("📁 Меню", "BACK_TO_MAIN"))
        ));
    }

    private static InlineKeyboardButton createButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }

    private static InlineKeyboardMarkup createMarkup(List<List<InlineKeyboardButton>> keyboard) {
        return InlineKeyboardMarkup.builder().keyboard(keyboard).build();
    }
}