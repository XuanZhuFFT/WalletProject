package org.example.walletweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String password;
    private String mainAccount;
    private String phoneNumber;
    private Double balance;
}
