package com.testlab2.application;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CryptoService {
    public String getCoinPrice(String cryptoName) {
        log.trace("Running getCoinPrice()");

        String coincapBitcoinAPIURL = "https://api.coincap.io/v2/assets/" + cryptoName;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<DataFromAPI> response = restTemplate.getForEntity(coincapBitcoinAPIURL, DataFromAPI.class);
        log.info("Response data received from api = " + response.getBody());

        CoinData coinData = response.getBody().getData();

        log.trace("Returning coin price data = " + coinData.getPriceUsd());
        return coinData.getPriceUsd();
    }
}

@Getter
@Setter
class CoinData {
    private String id;
    private String symbol;
    private String name;
    private String priceUsd;
}
@Getter
@Setter
class DataFromAPI {
    private CoinData data;
}
