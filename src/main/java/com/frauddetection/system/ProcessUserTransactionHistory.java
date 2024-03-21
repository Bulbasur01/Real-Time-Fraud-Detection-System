package com.frauddetection.system;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ProcessUserTransactionHistory {
    private final String userId;
    private final Map<String, Integer> serviceCounts = new HashMap<>();
    private final Map<String, Double> serviceTotalAmounts = new ConcurrentHashMap<>();
    private double totalAmount = 0;

    public void update(TransactionEvent event) {
        String serviceId = event.getServiceId();
        serviceCounts.merge(serviceId, 1, Integer::sum);
        serviceTotalAmounts.merge(serviceId, event.getAmount(), Double::sum);
        totalAmount += event.getAmount();
    }

    public boolean isMultipleServicesInWindow(int threshold, long windowSize) {
        long currentTime = System.currentTimeMillis();
        long windowStart = currentTime - windowSize;

        int distinctServices = (int) serviceCounts.values().stream().filter(count -> count > 0).count();

        return distinctServices > threshold && windowStart < currentTime;
    }

    public boolean isHighAmountTransaction(long factor) {
        for (String serviceId : serviceCounts.keySet()) {
            int transactions = serviceCounts.getOrDefault(serviceId, 0);
            double totalServiceAmount = serviceTotalAmounts.getOrDefault(serviceId, 0.0);
            double averageAmount = totalServiceAmount / transactions;
            if (averageAmount * factor < totalAmount)
                return true;
        }
        return false;
    }

    public boolean isPingPongActivity(int threshold, long windowSize, long currentTime) {
        long windowStart = currentTime - windowSize;

        int maxConsecutiveTransactions = 0;
        int consecutiveTransactions = 0;
        boolean previousTransactionInWindow = false;

        for (String serviceId : serviceCounts.keySet()) {
            int count = serviceCounts.getOrDefault(serviceId, 0);
            if (count > 0) {
                if (!previousTransactionInWindow || currentTime - windowStart > windowSize) {
                    consecutiveTransactions = 0;
                }
                consecutiveTransactions++;
                maxConsecutiveTransactions = Math.max(maxConsecutiveTransactions, consecutiveTransactions);
                previousTransactionInWindow = true;
            } else {
                previousTransactionInWindow = false;
            }
        }

        return maxConsecutiveTransactions >= threshold;
    }

    public String getUserId() {
        return userId;
    }
}
