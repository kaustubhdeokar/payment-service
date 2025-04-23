package com.example.demo.service;

import com.example.demo.repo.BankRepo;
import org.springframework.stereotype.Service;

@Service
public class BankService {
    private final BankRepo bankRepo;

    public BankService(BankRepo bankRepo) {
        this.bankRepo = bankRepo;
    }

    public void createBankAccount(Long id) {
    }
}
