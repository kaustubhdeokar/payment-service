package com.example.demo.service;

import com.example.demo.dto.BankDto;
import com.example.demo.dto.TransferDto;
import com.example.demo.exception.CustomException;
import com.example.demo.model.User;
import com.example.demo.repo.BankRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankService {
    private final BankRepo bankRepo;
    private final UserService userService;

    public BankService(BankRepo bankRepo, UserService userService) {
        this.bankRepo = bankRepo;
        this.userService = userService;
    }

    public void createBankAccount(Long id) {

    }

    @Transactional
    public void transfer(TransferDto dto) {
        Long amount = Long.parseLong(dto.getAmount());
        debitBalanceUser(new BankDto(dto.getSender(), amount));
        creditBalanceUser(new BankDto(dto.getReceiver(), amount));
    }

    public void creditBalanceUser(BankDto bankDto) {
        User user = userService.getUserIfPresent(bankDto.getUsername());
        user.getBank().setBalance(user.getBank().getBalance() +
                bankDto.getAddBalance());
        userService.saveUser(user);
    }

    public void debitBalanceUser(BankDto bankDto) {
        User user = userService.getUserIfPresent(bankDto.getUsername());
        if (user.getBank().getBalance() < bankDto.getAddBalance()) {
            throw new CustomException("Insufficient balance");
        }
        user.getBank().setBalance(user.getBank().getBalance() -
                bankDto.getAddBalance());
        userService.saveUser(user);
    }

}
