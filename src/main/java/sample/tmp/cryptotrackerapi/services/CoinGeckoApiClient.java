package sample.tmp.cryptotrackerapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sample.tmp.cryptotrackerapi.dto.CryptoCurrencyInfo;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CoinGeckoApiClient {

    private final RestTemplate restTemplate;
    private final String apiUrl = "https://api.coingecko.com/api/v3"; // Базовый URL CoinGecko API

    @Autowired
    public CoinGeckoApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CryptoCurrencyInfo getCryptoCurrencyInfo(String cryptoSymbol) {
        // Создайте URL для запроса к CoinGecko API с использованием cryptoSymbol
        String apiUrl = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=" + cryptoSymbol;

        // Выполните GET-запрос к CoinGecko API и получите ответ в формате JSON
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);

        // Инициализируйте ObjectMapper для преобразования JSON в объекты Java
        ObjectMapper objectMapper = new ObjectMapper();

        // Преобразуйте JSON-ответ в объект JsonNode
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Извлеките необходимую информацию (например, текущую цену) из объекта JsonNode
        BigDecimal currentPrice = jsonNode.get(0).get("current_price").decimalValue();

        // Создайте и верните объект CryptoCurrencyInfo
        CryptoCurrencyInfo cryptoCurrencyInfo = new CryptoCurrencyInfo();
        cryptoCurrencyInfo.setCurrentPrice(currentPrice);

        return cryptoCurrencyInfo;
    }
}
