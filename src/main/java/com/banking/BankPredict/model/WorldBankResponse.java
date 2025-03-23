package com.banking.BankPredict.model;

public record WorldBankResponse(
        String country,
        String date,
        String value
) {
}
