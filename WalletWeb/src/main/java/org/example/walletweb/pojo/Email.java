package org.example.walletweb.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String email;
    private String SSN;
    private String password;
    private String walletUsername;
    private char checked;
}
