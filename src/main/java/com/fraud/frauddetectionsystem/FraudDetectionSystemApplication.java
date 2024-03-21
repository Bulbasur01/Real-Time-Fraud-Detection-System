package com.fraud.frauddetectionsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FraudDetectionSystemApplication {

	public static void main(String[] args) {
		FraudDetectionService fraudDetectionSystem = new FraudDetectionService();

		TransactionEvent[] testEvents = {
				new TransactionEvent(1617906000, 150.00, "user1", "serviceA"),
				new TransactionEvent(1617906060, 4500.00, "user2", "serviceB"),
				new TransactionEvent(1617906120, 75.00, "user1", "serviceC"),
				new TransactionEvent(1617906180, 3000.00, "user3", "serviceA"),
				new TransactionEvent(1617906240, 200.00, "user1", "serviceB"),
				new TransactionEvent(1617906300, 4800.00, "user2", "serviceC"),
				new TransactionEvent(1617906360, 100.00, "user4", "serviceA"),
				new TransactionEvent(1617906420, 4900.00, "user3", "serviceB"),
				new TransactionEvent(1617906480, 120.00, "user1", "serviceD"),
				new TransactionEvent(1617906540, 5000.00, "user3", "serviceC")
		};

		for (TransactionEvent event : testEvents) {
			fraudDetectionSystem.processTransaction(event);
		}

		fraudDetectionSystem.detectFraud();
	}

}
