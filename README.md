# Wallet API Project

This project implements a RESTful Wallet API using Java, Spring Boot, and an H2 in-memory database. It allows users to create and manage wallets, perform deposits, withdrawals, and view wallet details. The project is designed with a modular architecture, includes extensive unit tests, and supports runtime configuration.

---

## Features

- **User Management:**
    - Create new users.
- **Wallet Management:**
    - Create wallets linked to users.
    - Retrieve wallet details.
    - Deposit and withdraw funds securely.
- **Database:**
    - Uses an H2 in-memory database for runtime operations.
- **Unit Tests:**
    - Includes comprehensive tests for services and repositories.
- **Documentation:**
    - Swagger UI available for API exploration.

---

## Project Structure

### Main Files:
- **Controllers:**
    - [`WalletController.java`](WalletController.java): Handles wallet operations such as creation, deposit, and withdrawal.
    - [`UserController.java`](UserController.java): Handles user creation.
- **Services:**
    - [`WalletService.java`](WalletService.java): Business logic for wallets.
    - [`UserService.java`](UserService.java): Business logic for users.
- **Repositories:**
    - [`WalletRepository.java`](WalletRepository.java): Database access layer for wallets.
    - [`UserRepository.java`](UserRepository.java): Database access layer for users.
- **Configuration:**
    - [`application.properties`](application.properties): General runtime configuration.
    - [`application-test.properties`](application-test.properties): Test-specific configuration.

---

## Getting Started

### Prerequisites

- Java 23
- Gradle installed.

### Initialize the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/VeronikaNikolaevaValeva/SumUp_WalletAPI.git
   cd wallet-api
   ```

2. Build the project:
   ```bash
   ./gradlew build
   ```

3. Build the project:
   ```bash
   ./gradlew bootRun
   ```

### Access Swagger UI
Once the application is running, access the Swagger UI for API exploration:
   ```bash
   http://localhost:8080/swagger-ui/index.html#/ 
   ```

### Database Configuration and Access
The Wallet API uses an H2 in-memory database. By default, the database is not persistent and resets with every application restart. The H2 Console provides a web-based interface to view and query the database.

#### Database Accessing
The database can be accessed at:
  ```bash
  http://localhost:8080/h2-console
  ```

#### Database Accessing
Use the following credentials to log in:
  - **JDBC URL:** [`jdbc:h2:mem:testdb`](jdbc:h2:mem:testdb)
  - **Username:** [`veronika`](veronika)
  - **Password:** [`valeva`](valeva)

#### Description
The database stores the following entities:
- **User Table:** Contains information about users, such as their unique ID and name.
- **Wallet Table:** Contains wallets linked to users, with columns for wallet ID, balance, and owner (user ID).
---

## Running Tests

### Command to Run Tests
To execute all tests:
  ```bash
  ./gradlew test
  ```
### Test Report
The test results are generated in an HTML report. You can view it in your browser by navigating to:
  ```bash
  file:///path/to/your/project/wallet-api/build/reports/tests/test/index.html
  ```
Replace `/path/to/your/project/` with the actual path to your project folder.

---


## Working with the Postman Collection
The Wallet API project includes a Postman collection to simplify testing and interacting with the API endpoints for creating users, managing wallets, and performing functions like deposits and withdrawals.

### Steps to Use the Postman Collection

1. **Download the Collection**
  - Locate the Postman collection file `SumUp_Wallet_API.postman_collection.json` included in the root of this project.

2. **Run API Requests**
  - Import and open the collection in Postman.
  - Select any pre-configured request.
  - Modify the request body or parameters as needed.

---