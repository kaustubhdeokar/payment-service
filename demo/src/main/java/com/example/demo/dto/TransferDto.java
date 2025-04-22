package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferDto {
    public String sender;
    public String receiver;
    public String amount;
}
