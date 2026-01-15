🚀 Spring Boot Microservices: Order, Product & Inventory Ecosystem
This repository features a robust, scalable E-Commerce backend architecture built with Java 17 and Spring Boot 3.x. The system demonstrates advanced microservices patterns, including Service Discovery, API Gateway Routing, and Distributed Transactions.

🏗 System Architecture
The ecosystem consists of the following core components:

API Gateway (Port: 8080): The single entry point for all client requests. It handles dynamic routing to downstream services using Spring Cloud Gateway.

Service Registry (Netflix Eureka): A centralized directory where all microservices register themselves for seamless discovery.

Product Service: Manages the product catalog using MongoDB.

Order Service: Orchestrates customer orders and coordinates with the inventory via OpenFeign.

Inventory Service: Maintains stock levels in PostgreSQL and performs transactional stock updates.

🔄 Integrated Workflow
Centralized Access: Clients communicate only with the API Gateway. The Gateway queries Eureka to find the correct service instance and routes the request.

Product & Stock Sync: Adding a product via the Product Service automatically initializes its stock in the Inventory Service.

Transactional Ordering: When an order is placed:

Order Service verifies stock availability.

If available, the order is saved, and a Transactional deduction is triggered in the Inventory Service to ensure data consistency.

🛠 Technology Stack
Core: Java 17, Spring Boot 3.3.x

Routing & Discovery: Spring Cloud Gateway, Netflix Eureka

Communication: Spring Cloud OpenFeign

Databases: MongoDB (Products), PostgreSQL (Orders & Inventory)

DevOps: Docker & Docker Compose

🧪 API Reference (Via Gateway)
All requests should now be directed to the Gateway (Port: 8080):

1. Create Product
POST http://localhost:8080/api/product

JSON

{
  "name": "Gaming Laptop",
  "description": "RTX 4080, 32GB RAM",
  "price": 2500,
  "skuCode": "LAPTOP-G-4080",
  "quantity": 10
}
2. Place Order
POST http://localhost:8080/api/order

JSON

{
  "skuCode": "LAPTOP-G-4080",
  "price": 2500,
  "quantity": 1
}
📂 Project Structure
Plaintext

.
├── api-gateway/         # Centralized entry point & routing
├── discovery-server/    # Service registration (Eureka)
├── inventory-service/   # Stock management & logic
├── order-service/       # Order processing & Feign clients
├── product-service/     # Product catalog management
└── infrastructure/      # Docker Compose & Database setups
🗺 Roadmap
[x] Service Discovery: Netflix Eureka integration.

[x] API Gateway: Centralized routing and entry point.

[x] Stock Management: Real-time transactional deduction.

[ ] Security: Implementing JWT/OAuth2 via the Gateway.

[ ] Resilience: Circuit Breaker implementation with Resilience4j.
