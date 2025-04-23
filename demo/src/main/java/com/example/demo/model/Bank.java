package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Setter
@Getter
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue
    private Long bankAccountId;

    private Long balance = 0L;
    @OneToOne(mappedBy = "bank")
    private User user;

}
