package com.banking.BankPredict.service;

import com.banking.BankPredict.model.User;
import com.banking.BankPredict.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.password().equals(password)) {
            throw new IllegalArgumentException("Password or email is incorrect");
        }

        return user;
    }

    public User saveUser(String email, String login, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("this Email is exist");
        }

        User user = User.builder()
                .id(null)
                .email(email)
                .login(login)
                .password(password)
                .created(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }
}
