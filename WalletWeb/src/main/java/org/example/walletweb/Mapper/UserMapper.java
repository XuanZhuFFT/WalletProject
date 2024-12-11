package org.example.walletweb.Mapper;


import org.apache.ibatis.annotations.*;
import org.example.walletweb.pojo.BankAccount;
import org.example.walletweb.pojo.Email;
import org.example.walletweb.pojo.User;
import org.example.walletweb.pojo.Wallet;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("Insert into wallet(username,password) values(#{username},#{password})")
    void register(String username, String password);

    @Select("select * from wallet where username = #{username}")
    User getByUsername(String username);

    @Select("select * from wallet where username = #{username}")
    Wallet getWalletByUsername(String username);

    @Select("select * from email where walletUsername = #{username}")
    List<Email> emailList(String username);

    @Select("select * from bankaccount where walletUsername = #{username}")
    List<BankAccount> bankAccountList(String username);

    @Insert("insert into email(email,walletUsername) values(#{email},#{username})")
    void addEmail(String email,String username);

    @Insert("insert into bankaccount(account,walletUsername,balance) values(#{bankAccount},#{username},0)")
    void addBankAccount(String bankAccount,String username);

    @Delete("delete from email where email = #{email}")
    void deleteEmail(String email);

    @Delete("delete from bankaccount where account = #{account}")
    void deleteBankAccount(String account);

    @Update("update wallet set balance = balance + #{amount} where username = #{username}")
    void rechargeWallet(String username, BigDecimal amount);

    @Update("update bankaccount set balance = balance - #{amount} where account = #{account}")
    void reduceAccountBalance(String account, BigDecimal amount);
}

