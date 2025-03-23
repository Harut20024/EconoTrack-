package com.banking.BankPredict.service;

import com.banking.BankPredict.model.CurrencyExchangeRate;
import com.banking.BankPredict.repository.CurrencyExchangeRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ExchangeRateService {
    private final CurrencyExchangeRateRepository currencyExchangeRateRepository;
    private static final Logger log = LoggerFactory.getLogger(ExchangeRateService.class);

    public ExchangeRateService(CurrencyExchangeRateRepository currencyExchangeRateRepository) {
        this.currencyExchangeRateRepository = currencyExchangeRateRepository;
    }

    public void saveExchange(String currency, double rate) {
        Optional<CurrencyExchangeRate> existingRate = currencyExchangeRateRepository.findByCurrency(currency);

        if (existingRate.isPresent()) {
            CurrencyExchangeRate updatedRate = existingRate.get().toBuilder()
                    .rate(rate)
                    .created(LocalDateTime.now())
                    .build();
            currencyExchangeRateRepository.save(updatedRate);
            log.info("Updated exchange rate for {}: {}", currency, rate);
        } else {
            CurrencyExchangeRate newRate = CurrencyExchangeRate.builder()
                    .currency(currency)
                    .rate(rate)
                    .created(LocalDateTime.now())
                    .build();
            currencyExchangeRateRepository.save(newRate);
            log.info("✅ Created new exchange rate for {}: {}", currency, rate);
        }
        }
}
