package com.banking.BankPredict.service;

import com.banking.BankPredict.model.EvaluateCountry;
import com.banking.BankPredict.repository.EvaluateCountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class EvaluateCountryService {
    private final EvaluateCountryRepository evaluateCountryRepository;

    public EvaluateCountryService(EvaluateCountryRepository evaluateCountryRepository) {
        this.evaluateCountryRepository = evaluateCountryRepository;
    }

    @Transactional
    public synchronized void updateColumn(String columnName, String value, String countryName) {
        EvaluateCountry country = evaluateCountryRepository.findByCountry(countryName);

        if (country == null) {
            country = EvaluateCountry.builder()
                    .country(countryName)
                    .created(LocalDateTime.now())
                    .build();
        }

        EvaluateCountry updatedCountry = updateField(country, columnName, value);

        evaluateCountryRepository.save(updatedCountry);
    }


    private EvaluateCountry updateField(EvaluateCountry country, String columnName, String value) {
        if (value == null || value.trim().isEmpty()) {
            return country;
        }

        return switch (columnName) {
            case "gdp" -> country.toBuilder().gdp(parseLong(value)).build();
            case "monthSalary" -> country.toBuilder().monthSalary(parseInt(value)).build();
            case "population" -> country.toBuilder().population(parseLong(value)).build();
            case "diffOfPopulation" -> country.toBuilder().diffOfPopulation(parseInt(value)).build();
            case "populationWithEducation" -> country.toBuilder().populationWithEducation(parseInt(value)).build();
            case "diffOfInflation" -> country.toBuilder().diffOfInflation(parseBigDecimal(value)).build();
            case "diffOfTrade" -> country.toBuilder().diffOfTrade(parseBigDecimal(value)).build();
            default -> throw new IllegalArgumentException("Column not found: " + columnName);
        };
    }

    private Long parseLong(String value) {
        try {
            BigDecimal bigDecimal = new BigDecimal(value);
            return bigDecimal.setScale(0, RoundingMode.HALF_UP).longValue();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid Long value: " + value, e);
        }
    }

    private Integer parseInt(String value) {
        try {
            BigDecimal bigDecimal = new BigDecimal(value);
            return bigDecimal.setScale(0, RoundingMode.HALF_UP).intValue();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid Integer value: " + value, e);
        }
    }

    private BigDecimal parseBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid BigDecimal value: " + value, e);
        }
    }
}
