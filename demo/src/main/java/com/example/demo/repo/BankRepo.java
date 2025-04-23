package com.example.demo.repo;

import com.example.demo.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepo extends JpaRepository<Bank, Long> {
    Optional<Bank> findByBankAccountId(Long bankAccountId);

}

