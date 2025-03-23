package com.banking.BankPredict.repository;

import com.banking.BankPredict.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID>
{
    Optional<User> findByEmail(String email);
}
