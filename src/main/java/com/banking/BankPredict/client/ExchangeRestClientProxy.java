package com.banking.BankPredict.client;

import com.banking.BankPredict.model.CurrencyCodeResponse;
import com.banking.BankPredict.model.CurrencyExchangeRate;
import com.banking.BankPredict.repository.CurrencyExchangeRateRepository;
import com.banking.BankPredict.service.ExchangeRateService;
import io.vavr.Tuple3;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Slf4j
@Profile("!test")
@Component
public class ExchangeRestClientProxy {

    private final RestClient restClient;

    private final ExchangeRateService exchangeRateService;

    public ExchangeRestClientProxy(@Qualifier("exchangeClient") RestClient restClient, CurrencyExchangeRateRepository currencyExchangeRateRepository, ExchangeRateService exchangeRateService) {
        this.restClient = restClient;
        this.exchangeRateService = exchangeRateService;
    }

    public void updateExchange() {
        try {
            CurrencyCodeResponse response = Optional.ofNullable(
                    this.restClient.get()
                            .uri("/USD")
                            .retrieve()
                            .body(CurrencyCodeResponse.class)
            ).orElseThrow(() -> new IllegalArgumentException("No suitable currency data available"));

            Double amdRate = response.conversion_rates().get("AMD");
            Double rubRate = response.conversion_rates().get("RUB");

            if (amdRate == null || rubRate == null) {
                log.error("Error: Missing exchange rates for AMD or RUB!");
                throw new IllegalStateException("Exchange rate data is incomplete");
            }

            log.info("Updating exchange rates: AMD = {}, RUB = {}", amdRate, rubRate);

            this.exchangeRateService.saveExchange("AMD", amdRate);
            this.exchangeRateService.saveExchange("RUB", rubRate);

            log.info("Exchange rates updated successfully");

        } catch (Exception e) {
            log.error("Failed to update exchange rates: {}", e.getMessage());
        }
    }



}
