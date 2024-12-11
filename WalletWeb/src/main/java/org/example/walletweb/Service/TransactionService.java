package org.example.walletweb.Service;

import org.example.walletweb.pojo.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    List<FinishedBill> getFinishedBill(String username,String sendUsername,String receiveUsername,String state);

    List<UnfinishedBill> getUnfinishedBill(String username,String sendUsername,String receiveUsername,String state);

    void cancelBill(Integer ID);

    void finishBill(Integer ID, String sendUsername,String receiveUsername,double amount,String memo);
}
