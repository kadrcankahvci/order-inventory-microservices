🚀 Spring Boot Microservices: Order & Inventory Ecosystem
This repository contains a modern microservices-based E-Commerce backend architecture. The system demonstrates a seamless communication between services using Spring WebFlux WebClient.

🏗 System Architecture
The project currently consists of two core services:

Order Service (Port: 8081): Manages customer orders and processing.

Inventory Service (Port: 8082): Handles stock management and availability checks.

Workflow:
When a customer places an order via the Order Service, it performs a synchronous call to the Inventory Service to verify if the requested product is in stock before committing the order to the database.

🛠 Technology Stack
Java 17+

Spring Boot 3.x

Spring Data JPA

Spring WebFlux (Inter-service communication)

PostgreSQL (Dockerized individual databases for each service)

Docker & Docker Compose (Infrastructure orchestration)

🚀 Getting Started
1. Prerequisites
   Make sure you have Docker and JDK 17+ installed on your machine.

2. Infrastructure Setup
   Navigate to the infrastructure directory and run the following command to start the PostgreSQL databases:

Bash

docker-compose up -d
3. Running Services
   You can run each service via your IDE (IntelliJ IDEA) or using Maven in the terminal:

Bash

# Inside inventory-service or order-service folder
mvn spring-boot:run
🧪 Testing the API
Place an Order (POST)
Endpoint: http://localhost:8081/api/order

Payload:

JSON

{
"skuCode": "iphone_15",
"price": 50000,
"quantity": 1
}
Check Inventory (GET)
Endpoint: http://localhost:8082/api/inventory/{sku-code}

📂 Project Structure
Plaintext

.
├── inventory-service/   # Handles stock and availability
├── order-service/       # Handles customer orders
├── infrastructure/      # Docker Compose files and DB scripts
└── pom.xml             # Parent Maven configuration
🗺 Roadmap & Upcoming Features
[ ] Service Discovery: Implementing Netflix Eureka.

[ ] API Gateway: Centralized entry point for all requests.

[ ] Security: Implementing Keycloak/OAuth2.

[ ] Event-Driven: Asynchronous communication using Apache Kafka.