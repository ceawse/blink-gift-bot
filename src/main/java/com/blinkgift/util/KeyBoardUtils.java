package com.blinkgift.util;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class KeyBoardUtils {

    public static InlineKeyboardMarkup createMainMenu() {
        return createMarkup(List.of(
                List.of(createButton("🪙 Криптовалюта", "MAIN_CRYPTO")),
                List.of(createButton("🏦 Банки", "MAIN_BANKS")),
                List.of(createButton("💳 Платёжные системы", "MAIN_PAY_SYSTEMS")),
                List.of(createButton("💵 Наличные", "MAIN_CASH"))
        ));
    }

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

    public static InlineKeyboardMarkup createBanksMenu(String prefix, String extraData) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        String[] banks = {"Сбербанк", "Тинькофф", "Альфа-Банк", "ВТБ", "Райффайзен", "Газпромбанк"};
        for (int i = 0; i < banks.length; i += 2) {
            String callback1 = prefix + ":" + banks[i] + (extraData != null ? ":" + extraData : "");
            String callback2 = prefix + ":" + banks[i+1] + (extraData != null ? ":" + extraData : "");
            rows.add(List.of(createButton(banks[i], callback1), createButton(banks[i+1], callback2)));
        }
        rows.add(List.of(createButton("⬅️ Назад", "BACK_TO_MAIN")));
        return createMarkup(rows);
    }

    public static InlineKeyboardMarkup createPaySystemsMenu(String prefix, String extraData) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        String[] systems = {"Payeer RUB", "Payeer USD", "AdvCash RUB", "AdvCash USD", "Perfect Money", "Qiwi"};
        for (int i = 0; i < systems.length; i += 2) {
            String callback1 = prefix + ":" + systems[i] + (extraData != null ? ":" + extraData : "");
            String callback2 = prefix + ":" + systems[i+1] + (extraData != null ? ":" + extraData : "");
            rows.add(List.of(createButton(systems[i], callback1), createButton(systems[i+1], callback2)));
        }
        rows.add(List.of(createButton("⬅️ Назад", "BACK_TO_MAIN")));
        return createMarkup(rows);
    }

    public static InlineKeyboardMarkup createCashMenu(String prefix, String extraData) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        String[] cities = {"Москва", "Санкт-Петербург", "Дубай", "Стамбул", "Ереван", "Тбилиси"};
        for (int i = 0; i < cities.length; i += 2) {
            String callback1 = prefix + ":" + cities[i] + (extraData != null ? ":" + extraData : "");
            String callback2 = prefix + ":" + cities[i+1] + (extraData != null ? ":" + extraData : "");
            rows.add(List.of(createButton(cities[i], callback1), createButton(cities[i+1], callback2)));
        }
        rows.add(List.of(createButton("⬅️ Назад", "BACK_TO_MAIN")));
        return createMarkup(rows);
    }

    public static InlineKeyboardMarkup createExchangeFinalMenu(String giveCoin, String receiveCoin) {
        String siteUrl = "http://localhost:3000/";
        InlineKeyboardButton actionBtn = new InlineKeyboardButton();
        actionBtn.setText("Перейти к обмену на сайт");
        actionBtn.setUrl(siteUrl);

        return createMarkup(List.of(
                List.of(actionBtn),
                List.of(createButton("⬅️ Назад", "BACK_TO_MAIN")),
                List.of(createButton("📁 В главное меню", "BACK_TO_MAIN"))
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