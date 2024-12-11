package org.example.walletweb.Controller;

import lombok.extern.slf4j.Slf4j;
import org.example.walletweb.Service.impl.TransactionServiceImpl;
import org.example.walletweb.pojo.*;
import org.example.walletweb.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping
@CrossOrigin
public class TransactionController {
    @Autowired
    private TransactionServiceImpl transactionService;

    @GetMapping("/transaction/finished")
    public Result getFinishedBill(@RequestParam(value = "paymentAccount", required = false) String paymentAccount,
                                  @RequestParam(value = "receivingAccount", required = false) String receivingAccount,
                                  @RequestParam(value = "state", required = false) String state){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");

        List<FinishedBill> finishedBillList = transactionService.getFinishedBill(username,paymentAccount,receivingAccount,state);
        return Result.success(finishedBillList);
    }

    @GetMapping("/transaction/unfinished")
    public Result getUnfinishedBill(@RequestParam(value = "paymentAccount", required = false) String paymentAccount,
                                    @RequestParam(value = "receivingAccount", required = false) String receivingAccount,
                                    @RequestParam(value = "state", required = false) String state){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");

        List<UnfinishedBill> unfinishedBillList = transactionService.getUnfinishedBill(username,paymentAccount,receivingAccount,state);
        return Result.success(unfinishedBillList);
    }

    @DeleteMapping("/transaction/cancel")
    public Result cancelBill(@RequestBody Map<String, String> body){
        Integer billID = Integer.valueOf(body.get("ID"));
        /*if (billID == null) {
            return Result.error("请确认账单ID");
        }*/
        transactionService.cancelBill(billID);
        return Result.success();
    }

    @DeleteMapping("/transaction/finish")
    public Result finishBill(@RequestBody Map<String, String> body){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        double balance = (double) map.get("balance");

        Integer billID = Integer.valueOf(body.get("ID"));
        String sendUsername = body.get("sendUsername");
        String receiveUsername = body.get("receiveUsername");
        double amount = Double.parseDouble(body.get("amount"));
        String memo = body.get("memo");

        if(!username.equals(sendUsername)){
            return Result.error("您不是该交易付款者，无法进行此操作");
        }

        if(amount > balance){
            return Result.error("余额不足");
        }
        else{
            transactionService.finishBill(billID,sendUsername,receiveUsername,amount,memo);
        }

        return Result.success();
    }
}
