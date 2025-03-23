package com.banking.BankPredict.repository;

import com.banking.BankPredict.model.EvaluateCountry;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface EvaluateCountryRepository extends CrudRepository<EvaluateCountry, UUID> {

    Optional<EvaluateCountry> findByCountry(String country);

}
