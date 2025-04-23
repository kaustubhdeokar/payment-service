package com.example.demo.service;

import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.dto.BankDto;
import com.example.demo.dto.RegisterUserDto;
import com.example.demo.dto.UpdateUserDto;
import com.example.demo.exception.CustomException;
import com.example.demo.model.Bank;
import com.example.demo.model.User;
import com.example.demo.repo.BankRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final BankRepo bankRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepo userRepo, BankRepo bankRepo, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.bankRepo = bankRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public void signup(@RequestBody RegisterUserDto registerUserDto) {
        checkIfUserAlreadyExists(registerUserDto.getUsername());
        User user = new User(registerUserDto.getUsername(),
                passwordEncoder.encode(registerUserDto.getPassword()), Long.parseLong(registerUserDto.getBankAccountId()));
        Bank bank = new Bank();
        bank.setUser(user);
        user.setBank(bank);
        userRepo.save(user);
    }

    public void updateUser(UpdateUserDto updateUserDto) {
        User user = getUserIfPresent(updateUserDto.getUsername());
        user.setDetails(updateUserDto.getDetails());
        user.getBank().setBankAccountId(Long.parseLong(updateUserDto.getBankDetails()));
        user.getBank().setBalance(Long.parseLong(updateUserDto.getAccountBalance()));
        userRepo.save(user);
    }

    public void creditBalanceUser(BankDto bankDto) {
        User user = getUserIfPresent(bankDto.getUsername());
        user.getBank().setBalance(user.getBank().getBalance() +
                Long.parseLong(bankDto.getAddBalance()));
        userRepo.save(user);
    }

    public void debitBalanceUser(BankDto bankDto) {
        User user = getUserIfPresent(bankDto.getUsername());
        if(user.getBank().getBalance() < Long.parseLong(bankDto.getAddBalance())){
            throw new CustomException("Insufficient balance");
        }
        user.getBank().setBalance(user.getBank().getBalance() -
                Long.parseLong(bankDto.getAddBalance()));
        userRepo.save(user);
    }


    public AuthenticationResponse loginUser(String username, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println(auth.getPrincipal());
            System.out.println(auth.getCredentials());
            return new AuthenticationResponse(username);
        } catch (AuthenticationServiceException | DisabledException e) {
            throw new CustomException(e.getMessage());
        }
    }

    private void checkIfUserAlreadyExists(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    String.format("Username %s exists in the database", username));
        }
    }


    private User getUserIfPresent(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new CustomException(HttpStatus.BAD_REQUEST,
                String.format("Username %s does not exist in the database", username));
    }

}
