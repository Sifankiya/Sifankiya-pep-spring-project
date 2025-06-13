package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> register(Account account){
        if (account.getUsername() == null || account.getUsername().isBlank() ||
            account.getPassword() == null || account.getPassword().length() < 4 ||
            accountRepository.findByUsername(account.getUsername()).isPresent()) {
                return Optional.empty();
            
        }
        return Optional.of(accountRepository.save(account));
        
    }

    public Optional<Account> login(String username, String password){
        return accountRepository.findByUsernameAndPassword(username, password);
    }
}
