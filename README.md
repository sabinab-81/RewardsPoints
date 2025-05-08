# Reward Points Service

## Overview
The Reward Points Service is a Spring Boot application that calculates reward points for customers based on their transactions. Customers earn points for every dollar spent over $50, with additional points for amounts over $100. The service provides endpoints to retrieve reward points for individual customers as well as all customers within a specified date range.

## Project Structure
It looks like the formatting of your project structure in the `README.md` file got altered. To ensure it displays correctly, you can use Markdown code blocks to preserve the structure. Here's how you can format it:

```markdown
# Project Structure

reward-points-service
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.charter.rewardpointsservice
│   │   │       ├── RewardPointsServiceApplication.java
│   │   │       ├── controller
│   │   │       │    └── RewardController.java
│   │   │       ├── dao
│   │   │       │    └── TransactionRepository.java
│   │   │       ├── dto
│   │   │       │    └── Customer.java
│   │   │       ├── service
│   │   │       │    ├── RewardPointsService.java
│   │   │       │    └── RewardServiceImpl.java
│   ├── resources
│   │   └── application.properties
│   ├── test
│   │   ├── java
│   │   │   └── com.charter.rewardpointsservice
│   │   │       ├── controller
│   │   │       │    └── RewardControllerTest.java
│   │   │       ├── service
│   │   │       │    └── RewardServiceImplTest.java
│   ├── static
│   ├── templates
│
├── pom.xml
```

The project is organized into the following packages:

- **controller**: Contains the REST controllers that handle HTTP requests and responses.
- **service**: Defines the service interfaces.
- **serviceImpl**: Implements the service interfaces.
- **dao**: Contains the repository interfaces for database access.
- **model**: Defines the data models used in the application.

## Implementation Details

### RewardController
The `RewardController` class provides endpoints to get reward points for a specific customer or all customers within a date range.

- **Endpoints**:
  - `GET /rewards/points/{customerId}`: Retrieves reward points for a specific customer within a date range.

- **Methods**:
  - `getRewardPoints`: Validates and adjusts the date range, calculates reward points for a specific customer, and returns the result.

### RewardService
The `RewardService` interface defines methods for calculating reward points.

- **Methods**:
  - `getRewardPoints`: Validates and adjusts the date range, calculates reward points for a specific customer, and returns the result.

### RewardServiceImpl
The `RewardServiceImpl` class implements the `RewardService` interface.

- **Methods**:
  - `calculatePoints`: Calculates reward points based on the transaction amount. Customers receive 2 points for every dollar spent over $100 and 1 point for every dollar spent between $50 and $100.
  - `calculateMonthlyPoints`: Retrieves transactions for a specific customer within a date range, calculates monthly and total reward points, and returns the result.
   - `validateAndAdjustDates`: Validates and adjusts the start and end dates to ensure they are within a 3-month range and that the start date is not after the end date.


### TransactionRepository
The `TransactionRepository` interface extends `JpaRepository` and provides methods for accessing transaction data.

- **Methods**:
  - `findByCustomerIdAndDateBetween`: Retrieves transactions for a specific customer within a date range.
  - `findByDateBetween`: Retrieves transactions within a date range.

### Transaction
The `Transaction` class defines the data model for transactions.

- **Fields**:
  - `id`: The unique identifier for the transaction.
  - `customerId`: The ID of the customer associated with the transaction.
  - `amount`: The transaction amount.
  - `date`: The date of the transaction.

### Testing
The `RewardControllerTest` class contains unit tests for the `RewardController` using `MockMvc` to simulate HTTP requests and responses. It uses `Mockito` to mock the `RewardService` and verify the controller's behavior.

## Getting Started
### Prerequisites
- Java 17 or higher
- Maven
- Spring Boot

### Running the Application
1. Clone the repository:
   git clone https://github.com/Anusha2196/reward-points-service.git
2. Navigate to the project directory:
   cd reward-points-service
3. Build the project:
   mvn clean install
4. Run the application:
   mvn spring-boot:run
5. Running Tests
   To run the tests, use the following command:
   mvn test
   




