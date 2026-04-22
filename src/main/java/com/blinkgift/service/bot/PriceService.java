package com.blinkgift.service.bot;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PriceService {
    private final RestTemplate restTemplate = new RestTemplate();

    public double getExchangeRate(String from, String to) {
        try {
            // Упрощенный маппинг имен в ID CoinGecko (для примера)
            String fromId = mapToId(from);
            String toId = mapToId(to);

            String url = String.format("https://api.coingecko.com/api/v3/simple/price?ids=%s,%s&vs_currencies=usd", fromId, toId);
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);

            double fromPrice = response.get(fromId).get("usd").asDouble();
            double toPrice = response.get(toId).get("usd").asDouble();

            return fromPrice / toPrice;
        } catch (Exception e) {
            return 1.048; // Дефолтный курс если API упало
        }
    }

    private String mapToId(String name) {
        String n = name.toLowerCase();
        if (n.contains("bitcoin")) return "bitcoin";
        if (n.contains("ethereum")) return "ethereum";
        if (n.contains("usdt") || n.contains("usd coin")) return "tether";
        if (n.contains("solana")) return "solana";
        if (n.contains("toncoin")) return "the-open-network";
        if (n.contains("tron")) return "tron";
        if (n.contains("litecoin")) return "litecoin";
        return "tether";
    }
}