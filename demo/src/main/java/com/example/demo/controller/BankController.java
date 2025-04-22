package com.example.demo.controller;

import com.example.demo.dto.TransferDto;
import com.example.demo.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferDto transferDto){
        bankService.transfer(transferDto);
        return new ResponseEntity<>("Transferred.", HttpStatus.OK);
    }

}
