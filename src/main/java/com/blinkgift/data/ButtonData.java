package com.blinkgift.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ButtonData {
    // Главное меню
    MAIN_CRYPTO("MAIN_CRYPTO"),
    MAIN_BANKS("MAIN_BANKS"),
    MAIN_PAY_SYSTEMS("MAIN_PAY_SYSTEMS"),
    MAIN_CASH("MAIN_CASH"),

    // Вспомогательные
    BACK_TO_MAIN("BACK_TO_MAIN");

    private final String callbackData;
}