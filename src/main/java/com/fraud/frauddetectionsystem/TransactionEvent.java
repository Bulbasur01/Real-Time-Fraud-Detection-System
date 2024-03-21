package com.fraud.frauddetectionsystem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TransactionEvent {
    private long timestamp;
    private double amount;
    private String userId;
    private String serviceId;
}
