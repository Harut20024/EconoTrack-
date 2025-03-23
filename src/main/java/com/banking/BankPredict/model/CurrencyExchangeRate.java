package com.banking.BankPredict.model;

import jakarta.annotation.Nonnull;
import lombok.Builder;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record CurrencyExchangeRate(
        @Id UUID id,
        @Nonnull String currency,
        @Nonnull Double rate,
        @Nonnull LocalDateTime created
) {
}
