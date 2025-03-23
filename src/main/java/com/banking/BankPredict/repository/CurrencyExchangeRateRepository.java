package com.banking.BankPredict.repository;

import com.banking.BankPredict.model.CurrencyExchangeRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrencyExchangeRateRepository extends CrudRepository<CurrencyExchangeRate, UUID>
{

    CurrencyExchangeRate getCurrencyExchangeRateByCurrency(String currency);

    Optional<CurrencyExchangeRate> findByCurrency(String currency);
}
