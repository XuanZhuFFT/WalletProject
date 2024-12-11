package org.example.walletweb.Mapper;


import org.apache.ibatis.annotations.*;
import org.example.walletweb.pojo.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface TransactionMapper {

    //@Select("select * from bill_finished where sendUsername = #{username} or receiveUsername = #{username} order by finishTime DESC")
    List<FinishedBill> getFinishedBill(String username,String sendUsername,String receiveUsername,String state);

    //@Select("select * from bill_unfinished where sendUsername = #{username} or receiveUsername = #{username} order by createTime DESC")
    List<UnfinishedBill> getUnfinishedBill(String username,String sendUsername,String receiveUsername,String state);

    @Delete("delete from bill_unfinished where id = #{ID}")
    void cancelBill(Integer ID);

    @Update("update wallet set balance = balance + #{amount} where username = #{username}")
    void recharge(String username,double amount);

    @Update("update wallet set balance = balance - #{amount} where username = #{username}")
    void reduceBalance(String username,double amount);

    @Insert("insert into bill_finished (sendUsername,receiveUsername,amount,finishTime,memo) " +
            "values(#{sendUsername},#{receiveUsername},#{amount},#{finishTime},#{memo})")
    void addFinishedBill(String sendUsername, String receiveUsername, double amount, Timestamp finishTime, String memo);
}
