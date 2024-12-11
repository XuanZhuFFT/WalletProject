package org.example.walletweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    private String account;
    private String SSN ;
    private String walletUsername;
    private BigDecimal balance;
}
