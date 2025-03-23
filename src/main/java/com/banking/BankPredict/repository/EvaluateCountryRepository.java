package com.banking.BankPredict.repository;

import com.banking.BankPredict.model.EvaluateCountry;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EvaluateCountryRepository extends CrudRepository<EvaluateCountry, UUID> {

    EvaluateCountry findByCountry(String country);

}
