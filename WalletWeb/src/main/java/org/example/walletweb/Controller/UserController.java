package org.example.walletweb.Controller;

import lombok.extern.slf4j.Slf4j;
import org.example.walletweb.pojo.*;
import org.example.walletweb.Service.impl.UserServiceImpl;
import org.example.walletweb.pojo.DTO.Recharge;
import org.example.walletweb.utils.JwtUtil;
import org.example.walletweb.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping
@CrossOrigin
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/user/register")
    public Result register(@RequestParam String username, @RequestParam String password) {
        //查询用户判断是否已经存在相同用户名
        User u = userService.getByUsername(username);

        if (u == null) {
            //用户名未被占用，将信息写入数据库
            userService.register(username,password);
            return Result.success();
        }else{
            return Result.error("用户名已存在");
        }

    }

    @PostMapping("/user/login")
    public Result login(@RequestParam String username, @RequestParam String password){
        log.info("登录");
        User u = userService.getByUsername(username);

        if (u == null){
            return Result.error("用户名不存在");
        }

        if(u.getPassword().equals(password)){
            //成功登录
            Map<String,Object> claims = new HashMap<>();
            claims.put("username",username);
            claims.put("balance",u.getBalance());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        else
            return Result.error("用户名或密码错误");
    }

    @GetMapping("/user/info")
    public Result getInfo(){
        //根据用户名查询信息
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        Wallet wallet = userService.getWalletByUsername(username);

        return Result.success(wallet);
    }

    @GetMapping("/user/email")
    public Result getEmail(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");

        List<Email> emailList = userService.emailList(username);
        return Result.success(emailList);
    }

    @GetMapping("/user/bankAccount")
    public Result getBankAccount(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");

        List<BankAccount> bankAccountList = userService.bankAccountList(username);
        return Result.success(bankAccountList);
    }

    @PostMapping("/user/email")
    public Result addEmail(@RequestParam String email){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");

        userService.addEmail(email,username);
        return Result.success();
    }

    @PostMapping("/user/bankAccount")
    public Result addBankAccount(@RequestParam String bankAccount){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");

        userService.addBankAccount(bankAccount,username);
        return Result.success();
    }

    @DeleteMapping("/user/email")
    public Result deleteEmail(@RequestBody Map<String, String> body){
        String email = body.get("email");
        if (email == null || email.isEmpty()) {
            return Result.error("邮箱地址不能为空");
        }
        userService.deleteEmail(email);
        return Result.success();
    }

    @DeleteMapping("/user/bankAccount")
    public Result deleteBankAccount(@RequestBody Map<String, String> body){
        String account = body.get("bankAccount");
        System.out.println(account);
        if (account == null || account.isEmpty()) {
            return Result.error("银行账号不能为空");
        }
        userService.deleteBankAccount(account);
        return Result.success();
    }

    @PostMapping("/user/bankAccount/recharge")
    public Result recharge(@RequestBody Recharge recharge){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        String account = recharge.getAccount();
        BigDecimal amount = recharge.getAmount();

        userService.recharge(username,account,amount);
        return Result.success();
    }
}
