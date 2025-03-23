package com.banking.BankPredict.client;

import com.banking.BankPredict.model.WorldBankResponse;
import com.banking.BankPredict.repository.CurrencyExchangeRateRepository;
import com.banking.BankPredict.service.EvaluateCountryService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Profile("!test")
@Component
public class WorldBankClientProxy {

    private final RestClient restClient;
    private final EvaluateCountryService evaluateCountryService;
    private final CurrencyExchangeRateRepository currencyExchangeRateRepository;

    private static final Set<String> METRICS_TO_CONVERT = Set.of(
            "NY.GDP.MKTP.CD",
            "BM.GSR.GNFS.CD",
            "BX.GSR.GNFS.CD",
            "6.0.GNIpc",
            "NY.GDP.PCAP.CD"
    );

    public WorldBankClientProxy(@Qualifier("worldBankClient") RestClient restClient, ExchangeRestClientProxy exchangeRestClientProxy, EvaluateCountryService evaluateCountry, CurrencyExchangeRateRepository currencyExchangeRateRepository) {
        this.restClient = restClient;
        this.evaluateCountryService = evaluateCountry;
        this.currencyExchangeRateRepository = currencyExchangeRateRepository;
    }

    @Nonnull
    public List<WorldBankResponse> getGDP(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/NY.GDP.MKTP.CD?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable GDP data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    @Nonnull
    public List<WorldBankResponse> getTradeInServices(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/NE.TRD.GNFS.ZS?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable GDP data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    @Nonnull
    public List<WorldBankResponse> getGDPPerCapita(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/NY.GDP.PCAP.CD?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable GDP data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    @Nonnull
    public List<WorldBankResponse> getImportsOfTrade(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/BM.GSR.GNFS.CD?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable GDP data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    @Nonnull
    public List<WorldBankResponse> getExportsOfTrade(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/BX.GSR.GNFS.CD?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable GDP data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    @Nonnull
    public List<WorldBankResponse> getPopulationWithEducation(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/SE.TER.ENRR?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable GDP data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    @Nonnull
    public List<WorldBankResponse> getInfection(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/FP.CPI.TOTL.ZG?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable GDP data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    @Nonnull
    public List<WorldBankResponse> getPopularity(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/SP.POP.TOTL?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable popularity data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    @Nonnull
    public List<WorldBankResponse> getUrbanGrowth(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/SP.URB.GROW?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable urban growth data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    @Nonnull
    public List<WorldBankResponse> getUrbanPopulation(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/SP.URB.TOTL?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable urban population data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    @Nonnull
    public List<WorldBankResponse> getUrbanPovertyGap(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/SI.POV.URGP?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable urban poverty gap data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    @Nonnull
    public List<WorldBankResponse> getPopulationDensity(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/EN.POP.DNST?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable population density data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    @Nonnull
    public List<WorldBankResponse> getRuralPovertyRate(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/SI.POV.RUHC?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable rural poverty rate data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    @Nonnull
    public List<WorldBankResponse> getRuralPopulation(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/SP.RUR.TOTL?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable rural population growth data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    @Nonnull
    public List<WorldBankResponse> getRuralPopulationPercentage(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        String jsonResponse = Optional.ofNullable(
                this.restClient.get()
                        .uri("/country/" + countryName + "/indicator/SP.RUR.TOTL.ZS?date=" + date + "&format=json")
                        .retrieve()
                        .body(String.class)
        ).orElseThrow(() -> new IllegalArgumentException("No suitable rural population percentage data available"));

        return parseGDPData(jsonResponse, exchangeCode);
    }

    private List<WorldBankResponse> parseGDPData(String jsonResponse, String exchangeCode) {
        List<WorldBankResponse> gdpList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        double exchange = switch (exchangeCode) {
            case "USD" -> 1;
            case "AMD" -> currencyExchangeRateRepository.getCurrencyExchangeRateByCurrency("AMD").rate();
            case "RUB" -> currencyExchangeRateRepository.getCurrencyExchangeRateByCurrency("RUB").rate();
            default -> throw new RuntimeException("Exchange code is wrong");
        };

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode dataArray = rootNode.get(1);

            if (dataArray != null && dataArray.isArray()) {
                for (JsonNode node : dataArray) {
                    String country = node.get("country").get("value").asText();
                    String date = node.get("date").asText();
                    String indicatorId = node.get("indicator").get("id").asText();
                    double rawValue = node.get("value").asDouble();

                    if (METRICS_TO_CONVERT.contains(indicatorId)) {
                        rawValue *= exchange;
                    }

                    String value = String.format("%.1f", rawValue);
                    gdpList.add(new WorldBankResponse(country, date, value));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("error with converting info: " + e);
        }
        return gdpList;
    }


}
