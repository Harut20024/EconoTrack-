package com.banking.BankPredict.model;

import jakarta.annotation.Nonnull;
import lombok.Builder;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record User(
        @Id UUID id,
        @Nonnull String email,
        @Nonnull String login,
        @Nonnull String password,
        @Nonnull LocalDateTime created
) {
}
