package com.blinkgift.service.bot;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PriceService {
    private final RestTemplate restTemplate = new RestTemplate();

    // Кэш для цен: Ключ - ID монеты, Значение - цена в USD
    private final Map<String, Double> priceCache = new ConcurrentHashMap<>();
    private long lastUpdateTime = 0;
    private static final long CACHE_DURATION = 300_000; // 5 минут в миллисекундах

    public double getExchangeRate(String from, String to) {
        try {
            updatePricesIfNeeded();

            String fromId = mapToId(from);
            String toId = mapToId(to);

            double fromPrice = priceCache.getOrDefault(fromId, 1.0);
            double toPrice = priceCache.getOrDefault(toId, 1.0);

            return fromPrice / toPrice;
        } catch (Exception e) {
            return 1.048; // Резервное значение при ошибке
        }
    }

    private synchronized void updatePricesIfNeeded() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime < CACHE_DURATION && !priceCache.isEmpty()) {
            return;
        }

        try {
            // Список всех нужных ID для одного запроса
            String ids = "bitcoin,ethereum,tether,solana,the-open-network,tron,litecoin,binancecoin,cardano,chainlink,cosmos,dash,dogecoin";
            String url = String.format("https://api.coingecko.com/api/v3/simple/price?ids=%s&vs_currencies=usd", ids);

            JsonNode response = restTemplate.getForObject(url, JsonNode.class);
            if (response != null) {
                response.fields().forEachRemaining(entry -> {
                    priceCache.put(entry.getKey(), entry.getValue().get("usd").asDouble());
                });
                lastUpdateTime = currentTime;
            }
        } catch (Exception e) {
            // Если API упало, используем старые данные из кэша
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
        if (n.contains("bnb")) return "binancecoin";
        if (n.contains("ada")) return "cardano";
        if (n.contains("link")) return "chainlink";
        if (n.contains("atom")) return "cosmos";
        if (n.contains("dash")) return "dash";
        if (n.contains("doge")) return "dogecoin";
        return "tether";
    }
}