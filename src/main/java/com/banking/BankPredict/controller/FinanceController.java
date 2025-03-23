package com.banking.BankPredict.controller;

import com.banking.BankPredict.client.ExchangeRestClientProxy;
import com.banking.BankPredict.client.WorldBankClientProxy;
import com.banking.BankPredict.model.EcoTrackResult;
import com.banking.BankPredict.model.EvaluateCountry;
import com.banking.BankPredict.model.User;
import com.banking.BankPredict.service.EvaluateCountryService;
import com.banking.BankPredict.service.UserService;
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
    private final EvaluateCountryService evaluateCountryService;
    private final WorldBankClientProxy worldBankClientProxy;
    private final UserService userService;

    @PostMapping("/evaluateCountry")
    public ResponseEntity<EvaluateCountry> getEvaluateCountry(@RequestParam String countryName) {
        return ResponseEntity.ok(evaluateCountryService.getEvaluateCountry(countryName));
    }

    @PostMapping("/ecoTrack")
    public ResponseEntity<List<EcoTrackResult>> ecoTrack(@RequestParam String countryName, @RequestParam String date, @RequestParam String exchangeCode) {
        return ResponseEntity.ok(worldBankClientProxy.getEcoTrack(countryName, date, exchangeCode));
    }

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

    @PostMapping("/updateExchangeRate")
    public ResponseEntity<String> updateExchangeRate() {
        exchangeRestClientProxy.updateExchange();
        return ResponseEntity.ok("Exchange Updated");
    }

}
