package org.example.walletweb.Service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.walletweb.Mapper.TransactionMapper;
import org.example.walletweb.Service.TransactionService;
import org.example.walletweb.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public List<FinishedBill> getFinishedBill(String username,String sendUsername,String receiveUsername,String state){
        return transactionMapper.getFinishedBill(username,sendUsername,receiveUsername,state);
    }

    @Override
    public List<UnfinishedBill> getUnfinishedBill(String username,String sendUsername,String receiveUsername,String state){
        return transactionMapper.getUnfinishedBill(username,sendUsername,receiveUsername,state);
    }

    @Override
    public void cancelBill(Integer ID){
        transactionMapper.cancelBill(ID);
    }

    @Override
    public void finishBill(Integer ID, String sendUsername,String receiveUsername,double amount,String memo){
        transactionMapper.recharge(receiveUsername,amount);
        transactionMapper.reduceBalance(sendUsername,amount);

        Timestamp finishTime = new Timestamp(System.currentTimeMillis());
        transactionMapper.addFinishedBill(sendUsername,receiveUsername,amount,finishTime,memo);
        transactionMapper.cancelBill(ID);
    }
}
