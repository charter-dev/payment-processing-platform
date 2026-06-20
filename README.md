# Payment Processing Platform

## Overview

Payment Processing Platform adalah mini project yang mensimulasikan proses transaksi pembayaran modern menggunakan arsitektur berbasis event (Event-Driven Architecture).

Project ini dibangun menggunakan Java 17 dan Spring Boot 3 dengan integrasi beberapa komponen enterprise seperti PostgreSQL, Redis, Apache Kafka, dan Elasticsearch yang dijalankan menggunakan Docker Compose.

Tujuan utama project ini adalah menunjukkan implementasi:

* Spring IoC & Dependency Injection
* Java Stream API
* Native SQL Query
* Containerization
* Event-Driven Architecture
* Kafka Producer & Consumer
* Redis Caching Strategy
* Elasticsearch Integration
* Flyway Database Migration
* REST API Development
* API Documentation (Swagger/OpenAPI)

---

# Technology Stack

| Category          | Technology              |
| ----------------- | ----------------------- |
| Language          | Java 17                 |
| Framework         | Spring Boot 3           |
| Build Tool        | Maven                   |
| Database          | PostgreSQL 16           |
| Cache             | Redis 7                 |
| Message Broker    | Apache Kafka            |
| Search Engine     | Elasticsearch 8         |
| Migration         | Flyway                  |
| API Documentation | Swagger / OpenAPI       |
| Containerization  | Docker & Docker Compose |
| Monitoring        | Spring Boot Actuator    |
| ORM               | Spring Data JPA         |
| Validation        | Jakarta Validation      |
| Logging           | SLF4J                   |

---

# Architecture

```text
+--------------------+
| Client / Swagger   |
+----------+---------+
           |
           v
+--------------------+
| Payment REST API   |
| Spring Boot        |
+----------+---------+
           |
           |
           +--------------------+
           |                    |
           v                    v
+----------------+     +----------------+
| PostgreSQL     |     | Redis Cache    |
| Transaction DB |     | Cache Aside    |
+----------------+     +----------------+

           |
           |
           v
+--------------------+
| Kafka Producer     |
+----------+---------+
           |
           v
      payment-topic
           |
           v
+--------------------+
| Kafka Consumer     |
+----------+---------+
           |
           v
+--------------------+
| Elasticsearch      |
| Search Index       |
+--------------------+
```

---

# Features

## Payment Creation

Create payment transaction and persist data into PostgreSQL.

Features:

* Customer Validation
* Transaction Validation
* Transaction ID Generation
* Kafka Event Publishing
* Redis Cache Population

Endpoint:

```http
POST /api/payments
```

---

## Payment Inquiry

Retrieve payment detail using cache-first strategy.

Flow:

```text
Request
   |
Redis Lookup
   |
Cache Hit --> Return Data
   |
Cache Miss
   |
PostgreSQL Query
   |
Update Redis
   |
Return Data
```

Endpoint:

```http
GET /api/payments/{trxId}
```

---

## Dashboard Summary

Using Java Stream API to aggregate transaction statistics.

Example:

```java
paymentRepository.findAll()
    .stream()
    .collect(
        Collectors.groupingBy(
            PaymentTransaction::getStatus,
            Collectors.counting()
        )
    );
```

Endpoint:

```http
GET /api/payments/dashboard
```

---

# Spring IoC Implementation

The application uses Spring Dependency Injection through constructor injection.

Example:

```java
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

}
```

Benefits:

* Loose Coupling
* Easier Testing
* Better Maintainability

---

# Java Stream API Usage

Used for transaction aggregation dashboard.

Example:

```java
paymentRepository.findAll()
    .stream()
    .collect(
        Collectors.groupingBy(
            PaymentTransaction::getStatus,
            Collectors.counting()
        )
    );
```

Concepts demonstrated:

* Stream
* Collectors
* groupingBy
* counting

---

# Native SQL Query

Example native query implementation:

```java
@Query(
 value = "SELECT * FROM customer WHERE customer_id = :customerId",
 nativeQuery = true
)
Optional<Customer> findByCustomerIdNative(
    @Param("customerId") String customerId
);
```

Demonstrates:

* Native SQL
* Parameter Binding
* Direct Database Access

---

# Redis Caching Strategy

Cache Aside Pattern is implemented.

Flow:

```text
Client
  |
Redis
  |
Cache Hit -> Return Data
  |
Cache Miss
  |
Database
  |
Store to Redis
  |
Return Data
```

Implementation:

```java
@Cacheable(
    value = "payments",
    key = "'page:' + #page + ':size:' + #size"
)
```

and

```java
redisTemplate.opsForValue().get(trxId);
```

---

# Kafka Event-Driven Architecture

Producer:

```java
kafkaTemplate.send("payment-topic", event);
```

Consumer:

```java
@KafkaListener(
    topics = "payment-topic",
    groupId = "payment-group"
)
```

Benefits:

* Loose Coupling
* Scalability
* Asynchronous Processing

---

# Elasticsearch Integration

Every successful payment event is indexed into Elasticsearch.

Flow:

```text
Payment Created
      |
Kafka Event
      |
Consumer
      |
Elasticsearch Index
```

Implementation:

```java
elasticRepository.save(document);
```

Benefits:

* Fast Search
* Analytics Capability
* Near Real-Time Indexing

---

# Database Migration

Database schema is managed using Flyway.

Benefits:

* Version Controlled Schema
* Repeatable Deployment
* Environment Consistency

Migration folder:

```text
src/main/resources/db/migration
```

---

# Containerization

All infrastructure components are managed through Docker Compose.

Containers:

* PostgreSQL
* Redis
* Zookeeper
* Kafka
* Elasticsearch
* Spring Boot Application

Run:

```bash
docker-compose up -d
```

Stop:

```bash
docker-compose down
```

---

# API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI:

```text
http://localhost:8080/v3/api-docs
```

---

# Monitoring

Spring Boot Actuator is enabled.

Examples:

```text
/actuator/health
/actuator/info
/actuator/metrics
```

---

# Project Structure

```text
src/main/java/payment
├── controller
├── service
├── repository
├── entity
├── dto
├── exception
├── kafka
├── elastic
├── config
└── util
```

---

# Design Principles Applied

* SOLID Principles
* Layered Architecture
* Dependency Injection
* Event-Driven Architecture
* Cache Aside Pattern
* Repository Pattern
* DTO Pattern
* Exception Handling Pattern

---

# Conclusion

This project demonstrates enterprise-grade backend development using Spring Boot and modern distributed-system components.

Key capabilities demonstrated:

✔ Spring IoC & Dependency Injection

✔ Java Stream API

✔ Native SQL Query

✔ PostgreSQL Integration

✔ Redis Cache

✔ Kafka Producer & Consumer

✔ Event-Driven Architecture

✔ Elasticsearch Integration

✔ Docker Containerization

✔ Flyway Migration

✔ Swagger Documentation

✔ Spring Boot Actuator Monitoring

