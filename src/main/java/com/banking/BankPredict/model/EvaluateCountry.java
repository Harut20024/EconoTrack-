package com.banking.BankPredict.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record EvaluateCountry(
        @Id
        UUID id,
        String country,
        Long gdp,
        Integer monthSalary,
        Long population,
        Integer diffOfPopulation,
        Integer populationWithEducation,
        BigDecimal diffOfInflation,
        BigDecimal diffOfTrade,
        LocalDateTime created
) {
}
