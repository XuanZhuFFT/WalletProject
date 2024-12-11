package org.example.walletweb.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber {
    private String phoneNumber;
    private String SSN;
    private char checked;
}
