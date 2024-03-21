# Real-Time Fraud Detection System Prototype

## Introduction
This prototype demonstrates a real-time fraud detection system in a distributed fintech ecosystem. The system processes a stream of transactions and identifies potential fraudulent patterns without relying on external libraries designed specifically for anomaly detection.

## Algorithm Description
The system processes each transaction event and maintains a user transaction history. It then checks for various fraudulent patterns based on the provided criteria:
- Multiple services in a window: Identifies users conducting transactions in more than 3 distinct services within a 5-minute window.
- High amount transaction: Flags users with transactions significantly higher than their typical amounts in the last 24 hours.
- Ping-pong activity: Detects sequences of transactions bouncing back and forth between two services within 10 minutes.

## Implementation Details
- **FraudDetectionSystemApplication.java**: Contains the main implementation of the fraud detection system.
- **TransactionEvent**: Represents a single transaction event with timestamp, amount, user ID, and service ID.
- **ProcessUserTransactionHistory**: Maintains the transaction history for each user and provides methods to check for fraudulent patterns.
- The system is written in Java and does not rely on external libraries for anomaly detection.
- Out-of-order event processing is handled by processing events in real-time and maintaining appropriate windowing mechanisms to accommodate network latencies in a distributed system.

## How to Run
1. Compile the FraudDetectionSystem.java file.
2. Run the compiled class file.
3. The system will process the test dataset provided in the main method and generate alerts for any fraudulent patterns detected.

## Test Dataset
The input test dataset represents a stream of transaction events. Each event contains a timestamp, transaction amount, user ID, and service ID. See the provided example in the code comments.

## Expected Results
Based on the provided test dataset, the system should generate alerts for:
- User1: Conducting transactions in more than 3 distinct services within a 5-minute window. Also, user1 has transactions significantly higher than typical amounts and involved in ping-pong activity within 10 minutes.
- User2 and User3: Transactions significantly higher than typical amounts. Both user2 and user3 are also involved in ping-pong activity within 10 minutes.
- User4: Transactions significantly higher than typical amounts.
