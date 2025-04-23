package com.example.demo.controller;

import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.RegisterUserDto;
import com.example.demo.dto.UpdateUserDto;
import com.example.demo.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/api/v1/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterUserDto registerUserDto) {
        try {
            service.signup(registerUserDto);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Duplicate username/email.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Registered.", HttpStatus.OK);
    }

    @PostMapping("/api/v1/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody LoginRequestDto registerUserDTO) {
        return new ResponseEntity<>(service.loginUser(registerUserDTO.getUsername(), registerUserDTO.getPassword()), HttpStatus.OK);
    }

    @PutMapping("/api/v1/user")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDto updateUserDto) {
        service.updateUser(updateUserDto);
        return new ResponseEntity<>("Updated.", HttpStatus.OK);
    }

}
