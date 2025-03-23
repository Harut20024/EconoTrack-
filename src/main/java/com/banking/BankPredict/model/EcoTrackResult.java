package com.banking.BankPredict.model;

import java.util.List;

public record EcoTrackResult(String code, List<WorldBankResponse> resultList) {
}
