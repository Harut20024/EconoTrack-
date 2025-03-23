package com.banking.BankPredict.service;

import com.banking.BankPredict.model.EvaluateCountry;
import com.banking.BankPredict.model.WorldBankResponse;
import com.banking.BankPredict.repository.EvaluateCountryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluateCountryService {
    private final EvaluateCountryRepository evaluateCountryRepository;

    public EvaluateCountryService(EvaluateCountryRepository evaluateCountryRepository) {
        this.evaluateCountryRepository = evaluateCountryRepository;
    }

    private static final String NOW_DAYS_DATE = "2023";


    public EvaluateCountry getEvaluateCountry(String countryName) {
        return evaluateCountryRepository.findByCountry(countryName).orElse(null);

    }

    public void saveOrUpdateEvaluateCountry(String countryName,
                                            List<WorldBankResponse> gdp,
                                            List<WorldBankResponse> gdpPerCapita,
                                            List<WorldBankResponse> popularity,
                                            List<WorldBankResponse> urbanPopulation,
                                            List<WorldBankResponse> ruralPopulation,
                                            List<WorldBankResponse> populationWithoutEducation,
                                            List<WorldBankResponse> importsOfTrade,
                                            List<WorldBankResponse> exportsOfTrade,
                                            List<WorldBankResponse> infection) {
        Optional<EvaluateCountry> optionalCountry = evaluateCountryRepository.findByCountry(countryName);
        EvaluateCountry evaluateCountry;
        if (optionalCountry.isPresent()) {
            evaluateCountry = optionalCountry.get().toBuilder()
                    .gdp(getGDP(gdp))
                    .monthSalary(getGDPPerCapita(gdpPerCapita))
                    .population(getPopularity(popularity))
                    .diffOfPopulation(getPopularityDiff(urbanPopulation, ruralPopulation))
                    .populationWithEducation(getPopulationWithEducation(populationWithoutEducation))
                    .diffOfInflation(getInfection(infection))
                    .diffOfTrade(getDiffOfTrade(importsOfTrade, exportsOfTrade))
                    .created(LocalDateTime.now())
                    .build();
        } else {
            evaluateCountry = EvaluateCountry.builder()
                    .id(null)
                    .country(countryName)
                    .gdp(getGDP(gdp))
                    .monthSalary(getGDPPerCapita(gdpPerCapita))
                    .population(getPopularity(popularity))
                    .diffOfPopulation(getPopularityDiff(urbanPopulation, ruralPopulation))
                    .populationWithEducation(getPopulationWithEducation(populationWithoutEducation))
                    .diffOfInflation(getInfection(infection))
                    .diffOfTrade(getDiffOfTrade(importsOfTrade, exportsOfTrade))
                    .created(LocalDateTime.now())
                    .build();
        }
        evaluateCountryRepository.save(evaluateCountry);
    }


    private BigDecimal getDiffOfTrade(List<WorldBankResponse> importsOfTrade, List<WorldBankResponse> exportsOfTrade) {
        BigDecimal importAtNowDays = importsOfTrade.stream()
                .filter(el -> el.date().equals(NOW_DAYS_DATE))
                .findFirst()
                .map(el -> new BigDecimal(el.value()))
                .orElse(BigDecimal.ZERO);

        BigDecimal exportAtNowDays = exportsOfTrade.stream()
                .filter(el -> el.date().equals(NOW_DAYS_DATE))
                .findFirst()
                .map(el -> new BigDecimal(el.value()))
                .orElse(BigDecimal.ZERO);

        return importAtNowDays.subtract(exportAtNowDays);
    }


    private BigDecimal getInfection(List<WorldBankResponse> infection) {
        return infection.stream()
                .filter(el -> el.date().equals(NOW_DAYS_DATE))
                .findFirst()
                .map(el -> new BigDecimal(el.value()).divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP))
                .orElse(BigDecimal.ZERO);
    }

    private int getPopulationWithEducation(List<WorldBankResponse> populationWithEducation) {
        return populationWithEducation.stream()
                .filter(el -> NOW_DAYS_DATE.equals(el.date()) && new BigDecimal(el.value()).compareTo(BigDecimal.ZERO) != 0)
                .findFirst()
                .or(() -> populationWithEducation.stream()
                        .filter(el -> el.value() != null && new BigDecimal(el.value()).compareTo(BigDecimal.ZERO) != 0)
                        .max(Comparator.comparing(el -> Integer.parseInt(el.date()))))
                .map(el -> new BigDecimal(el.value()).intValue())
                .orElse(0);
    }


    private int getPopularityDiff(List<WorldBankResponse> urbanPopulation, List<WorldBankResponse> ruralPopulation) {
        int urbanPopulationAtNowDays = urbanPopulation.stream()
                .filter(el -> el.date().equals(NOW_DAYS_DATE))
                .findFirst()
                .map(el -> new BigDecimal(el.value()).intValue())
                .orElse(0);

        int ruralPopulationAtNowDays = ruralPopulation.stream()
                .filter(el -> el.date().equals(NOW_DAYS_DATE))
                .findFirst()
                .map(el -> new BigDecimal(el.value()).intValue())
                .orElse(0);

        return urbanPopulationAtNowDays - ruralPopulationAtNowDays;
    }


    private Long getPopularity(List<WorldBankResponse> popularity) {
        return popularity.stream()
                .filter(el -> el.date().equals(NOW_DAYS_DATE))
                .findFirst()
                .map(el -> new BigDecimal(el.value()).longValue())
                .orElse(0L);
    }


    private int getGDPPerCapita(List<WorldBankResponse> gdpPerCapita) {
        return gdpPerCapita.stream()
                .filter(el -> el.date().equals(NOW_DAYS_DATE))
                .findFirst()
                .map(el -> new BigDecimal(el.value()).divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP).intValue())
                .orElse(0);
    }


    private Long getGDP(List<WorldBankResponse> gdp) {
        long averageGDP = Math.round(
                gdp.stream()
                        .map(WorldBankResponse::value)
                        .map(BigDecimal::new)
                        .mapToDouble(BigDecimal::doubleValue)
                        .average()
                        .orElse(0.0)
        );

        long latestGDP = gdp.stream()
                .filter(el -> el.date().equals(NOW_DAYS_DATE))
                .findFirst()
                .map(el -> new BigDecimal(el.value()).longValue())
                .orElse(0L);

        return latestGDP - averageGDP;
    }


}
