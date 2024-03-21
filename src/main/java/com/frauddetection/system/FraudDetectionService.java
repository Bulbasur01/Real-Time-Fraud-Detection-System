package com.frauddetection.system;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FraudDetectionService {

    private static final long WINDOW_SIZE = 5 * 60 * 1000; // 5 minutes in milliseconds
    private static final long MAX_TRANSACTION_AMOUNT_FACTOR = 5;
    private static final long PING_PONG_WINDOW_SIZE = 10 * 60 * 1000; // 10 minutes in milliseconds
    private static final int PING_PONG_THRESHOLD = 3;

    private Map<String, ProcessUserTransactionHistory> userHistories = new ConcurrentHashMap<>();

    public void processTransaction(TransactionEvent event) {
        String userId = event.getUserId();
        userHistories.computeIfAbsent(userId, k -> new ProcessUserTransactionHistory(userId)).update(event);
    }

    public void detectFraud() {
        long currentTime = System.currentTimeMillis();

        for (ProcessUserTransactionHistory history : userHistories.values()) {
            if (history.isMultipleServicesInWindow(3, WINDOW_SIZE)) {
                System.out.println("Alert: User " + history.getUserId()+ " conducting transactions in more than 3 distinct services within a 5-minute window.");
            }
            if (history.isHighAmountTransaction(MAX_TRANSACTION_AMOUNT_FACTOR)) {
                System.out.println("Alert: User " + history.getUserId() + " has transactions significantly higher than typical amounts.");
            }
            if (history.isPingPongActivity(PING_PONG_THRESHOLD, PING_PONG_WINDOW_SIZE, currentTime)) {
                System.out.println("Alert: User " + history.getUserId() + " involved in ping-pong activity within 10 minutes.");
            }
        }
    }
}