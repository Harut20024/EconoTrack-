package com.banking.BankPredict.model;

import java.util.Map;

public record CurrencyCodeResponse(
        String base_code,
        Map<String, Double> conversion_rates
) {
}
