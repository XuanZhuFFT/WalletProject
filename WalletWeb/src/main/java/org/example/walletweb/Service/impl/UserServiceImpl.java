package org.example.walletweb.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.example.walletweb.Mapper.UserMapper;
import org.example.walletweb.Service.UserService;
import org.example.walletweb.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByUsername(String username){
        return userMapper.getByUsername(username);
    }

    @Override
    public void register(String username, String password) {
        userMapper.register(username, password);
    }

    @Override
    public boolean login(String username, String password) {
        try {
            userMapper.getByUsername(username);
            return true;
        } catch (Exception e) {
            // 处理异常，例如用户名重复等
            log.error("登录失败", e);
            return false;
        }
    }

    @Override
    public Wallet getWalletByUsername(String username) {
        return userMapper.getWalletByUsername(username);
    }

    @Override
    public List<Email> emailList(String username){
        return userMapper.emailList(username);
    }

    @Override
    public List<BankAccount> bankAccountList(String username){
        return userMapper.bankAccountList(username);
    }

    @Override
    public void addEmail(String email, String username) {
        userMapper.addEmail(email,username);
    }

    @Override
    public void addBankAccount(String bankAccount, String username) {
        userMapper.addBankAccount(bankAccount,username);
    }

    @Override
    public void deleteEmail(String email) {
        userMapper.deleteEmail(email);
    }

    @Override
    public void deleteBankAccount(String account) {
        userMapper.deleteBankAccount(account);
    }

    @Override
    public void recharge(String username, String account, BigDecimal amount){
        userMapper.rechargeWallet(username,amount);
        userMapper.reduceAccountBalance(account,amount);
    }
}
