package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Setter
@Getter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Long userid;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    private String details = "";

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankAccountId", referencedColumnName = "bankAccountId", nullable = false)
    private Bank bank;

    public User(String username, String password, Long bankAccountId) {
        this.username = username;
        this.password = password;
        this.bank = new Bank(bankAccountId, 0L, this);
    }
}
