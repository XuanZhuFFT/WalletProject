package org.example.walletweb.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    private String username;
    @JsonIgnore//返回的json字符串中忽略password属性
    private String password;
    private String phoneNumber;
    private String mainAccount;
    private BigDecimal balance;
}
