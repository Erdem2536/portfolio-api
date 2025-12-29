# Portfolio API

A simple Investment Portfolio REST API built with Spring Boot, PostgreSQL, Docker, and Flyway.

This project demonstrates clean REST API design, database migrations, containerized infrastructure, and OpenAPI documentation.

---

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Web
- Spring Data JPA
- PostgreSQL 16
- Flyway
- Docker & Docker Compose
- Springdoc OpenAPI (Swagger)

---

## Project Structure

portfolio/
├── src/main/java/com/example/portfolio
│   ├── api
│   ├── application
│   ├── domain
│   ├── infrastructure
│   └── PortfolioApplication.java
│
├── src/main/resources
│   ├── db/migration
│   └── application.yml
│
├── docker-compose.yml
├── request.http
├── pom.xml
└── README.md

---

## Running with Docker

Start PostgreSQL using Docker Compose:

docker compose up -d

PostgreSQL will be available on port 5432 with the following credentials:

- Database: portfolio
- User: portfolio
- Password: portfolio

---

## Running the Application

Option 1: Run from IDE  
Run PortfolioApplication.java

Option 2: Run using Maven Wrapper

./mvnw spring-boot:run

The application will start on:

http://localhost:8080

---

## API Documentation (Swagger)

Swagger UI:

http://localhost:8080/swagger-ui.html

OpenAPI JSON:

http://localhost:8080/v3/api-docs

---

## Available Endpoints

Create Portfolio

POST /api/portfolios

Request body:
{
  "name": "My First Portfolio"
}

---

Get Portfolio by ID

GET /api/portfolios/{id}

Response example:
{
  "id": "uuid",
  "name": "My First Portfolio",
  "holdings": []
}

---

Add Holding to Portfolio

POST /api/portfolios/{id}/holdings

Request body:
{
  "symbol": "AAPL",
  "quantity": 10
}

---

## API Testing

The project includes a request.http file for manual API testing.

In IntelliJ:
1. Open request.http
2. Run requests in order
3. Copy the returned portfolio id between requests

---

## Database Migrations

Database schema is managed with Flyway.

Migration files are located at:

src/main/resources/db/migration

Migrations run automatically on application startup.

---

## Purpose

This project was built as a backend portfolio project to demonstrate real-world Spring Boot development practices, database management, and API documentation.

---

## Author

Oemer Erdem Balci
