package com.banking.BankPredict.controller;

import com.banking.BankPredict.client.ExchangeRestClientProxy;
import com.banking.BankPredict.client.WorldBankClientProxy;
import com.banking.BankPredict.model.User;
import com.banking.BankPredict.model.WorldBankResponse;
import com.banking.BankPredict.service.UserService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class FinanceController {

    private final ExchangeRestClientProxy exchangeRestClientProxy;
    private final WorldBankClientProxy worldBankClientProxy;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestParam String email, @RequestParam String login, @RequestParam String password) {
        User savedUser = userService.saveUser(email, login, password);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestParam String email, @RequestParam String password) {
        User savedUser = userService.loginUser(email, password);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/gdp")
    public ResponseEntity<List<WorldBankResponse>> getGDP(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        List<WorldBankResponse> worldBankClientProxyGDP = worldBankClientProxy.getGDP(countryName, date, exchangeCode);
        return ResponseEntity.ok(worldBankClientProxyGDP);
    }

    @PostMapping("/gdpPerCapita")
    public ResponseEntity<List<WorldBankResponse>> getGNI(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        List<WorldBankResponse> worldBankClientProxyGDP = worldBankClientProxy.getGDPPerCapita(countryName, date, exchangeCode);
        return ResponseEntity.ok(worldBankClientProxyGDP);
    }

    @PostMapping("/urbanGrowth")
    public ResponseEntity<List<WorldBankResponse>> getUrbanGrowth(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        return ResponseEntity.ok(worldBankClientProxy.getUrbanGrowth(countryName, date, exchangeCode));
    }

    @PostMapping("/urbanPopulation")
    public ResponseEntity<List<WorldBankResponse>> getUrbanPopulation(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        return ResponseEntity.ok(worldBankClientProxy.getUrbanPopulation(countryName, date, exchangeCode));
    }

    @PostMapping("/urbanPovertyGap")
    public ResponseEntity<List<WorldBankResponse>> getUrbanPovertyGap(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        return ResponseEntity.ok(worldBankClientProxy.getUrbanPovertyGap(countryName, date, exchangeCode));
    }

    @PostMapping("/populationDensity")
    public ResponseEntity<List<WorldBankResponse>> getPopulationDensity(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        return ResponseEntity.ok(worldBankClientProxy.getPopulationDensity(countryName, date, exchangeCode));
    }

    @PostMapping("/ruralPovertyRate")
    public ResponseEntity<List<WorldBankResponse>> getRuralPovertyRate(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        return ResponseEntity.ok(worldBankClientProxy.getRuralPovertyRate(countryName, date, exchangeCode));
    }

    @PostMapping("/ruralPopulation")
    public ResponseEntity<List<WorldBankResponse>> getRuralPopulationGrowth(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        return ResponseEntity.ok(worldBankClientProxy.getRuralPopulation(countryName, date, exchangeCode));
    }

    @PostMapping("/ruralPopulationPercentage")
    public ResponseEntity<List<WorldBankResponse>> getRuralPopulationPercentage(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        return ResponseEntity.ok(worldBankClientProxy.getRuralPopulationPercentage(countryName, date, exchangeCode));
    }

    @PostMapping("/tradeInServices")
    public ResponseEntity<List<WorldBankResponse>> getTradeInServices(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        List<WorldBankResponse> worldBankClientProxyGDP = worldBankClientProxy.getTradeInServices(countryName, date, exchangeCode);
        return ResponseEntity.ok(worldBankClientProxyGDP);
    }

    @PostMapping("/importsOfTrade")
    public ResponseEntity<List<WorldBankResponse>> getImportsOfTrade(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        List<WorldBankResponse> worldBankClientProxyGDP = worldBankClientProxy.getImportsOfTrade(countryName, date, exchangeCode);
        return ResponseEntity.ok(worldBankClientProxyGDP);
    }

    @PostMapping("/exportsOfTrade")
    public ResponseEntity<List<WorldBankResponse>> getExportsOfTrade(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        List<WorldBankResponse> worldBankClientProxyGDP = worldBankClientProxy.getExportsOfTrade(countryName, date, exchangeCode);
        return ResponseEntity.ok(worldBankClientProxyGDP);
    }

    @PostMapping("/populationWithEducation")
    public ResponseEntity<List<WorldBankResponse>> getPopulationWithoutEducation(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        List<WorldBankResponse> worldBankClientProxyGDP = worldBankClientProxy.getPopulationWithEducation(countryName, date, exchangeCode);
        return ResponseEntity.ok(worldBankClientProxyGDP);
    }

    @PostMapping("/infection")
    public ResponseEntity<List<WorldBankResponse>> getInfection(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        List<WorldBankResponse> worldBankClientProxyGDP = worldBankClientProxy.getInfection(countryName, date, exchangeCode);
        return ResponseEntity.ok(worldBankClientProxyGDP);
    }

    @PostMapping("/popularity")
    public ResponseEntity<List<WorldBankResponse>> getPopularity(@Nonnull String countryName, @Nonnull String date, @Nonnull String exchangeCode) {
        List<WorldBankResponse> worldBankClientProxyGDP = worldBankClientProxy.getPopularity(countryName, date, exchangeCode);
        return ResponseEntity.ok(worldBankClientProxyGDP);
    }

    @PostMapping("/updateExchangeRate")
    public ResponseEntity<String> updateExchangeRate() {
        exchangeRestClientProxy.updateExchange();
        return ResponseEntity.ok("Exchange Updated");
    }

}
