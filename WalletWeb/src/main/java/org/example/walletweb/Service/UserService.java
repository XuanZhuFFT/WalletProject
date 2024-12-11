package org.example.walletweb.Service;

import org.example.walletweb.pojo.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface UserService {

    void register(String username, String password);

    User getByUsername(String username);

    Wallet getWalletByUsername(String username);

    boolean login(String username, String password);

    List<Email> emailList(String username);

    List<BankAccount> bankAccountList(String username);

    void addEmail(String email, String username);

    void addBankAccount(String bankAccount, String username);

    void deleteEmail(String email);

    void deleteBankAccount(String account);

    void recharge(String username, String account, BigDecimal amount);
}
