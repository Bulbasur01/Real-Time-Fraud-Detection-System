package com.fraud.frauddetectionsystem;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FraudDetectionSystemApplication {

	private static String jsonFilePath = "/Transactions.json";
	public static void main(String[] args) {
		FraudDetectionService fraudDetectionSystem = new FraudDetectionService();

		List<TransactionEvent> transactions = readTransactionsFromJson(jsonFilePath);

		for (TransactionEvent event : transactions) {
			fraudDetectionSystem.processTransaction(event);
		}

		fraudDetectionSystem.detectFraud();
	}

	public static List<TransactionEvent> readTransactionsFromJson(String jsonFilePath) {
		List<TransactionEvent> transactions = new ArrayList<>();

		// Read JSON file and parse data
		try (InputStream inputStream = FraudDetectionSystemApplication.class.getResourceAsStream(jsonFilePath)) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode transactionsNode = objectMapper.readTree(inputStream);

			// Iterate over each transaction
			for (JsonNode transactionNode : transactionsNode) {
				long timestamp = transactionNode.get("timestamp").asLong();
				double amount = transactionNode.get("amount").asDouble();
				String userId = transactionNode.get("userID").asText();
				String serviceId = transactionNode.get("serviceID").asText();

				// Create Transaction object and add to list
				TransactionEvent transaction = new TransactionEvent(timestamp, amount, userId, serviceId);
				transactions.add(transaction);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return transactions;
	}

}
