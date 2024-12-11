package org.example.walletweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnfinishedBill {
    private int id;
    private String sendUsername;
    private String receiveUsername;
    private BigDecimal amount;
    private LocalDateTime createTime;
    private String memo;
}
