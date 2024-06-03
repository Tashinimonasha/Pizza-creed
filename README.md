# Pizza Creed 🍕

Pizza Creed is a pizza bakery in Galle Fort that prepares pizza only for orders.

## Table of Contents
- [Introduction](#introduction)
- [Setup](#setup)
  - [Prerequisites](#prerequisites)
  - [Database Configuration](#database-configuration)
  - [Running the Application](#running-the-application)
- [Endpoints](#endpoints)
- [Postman Collection](#postman-collection)
- [Documentation](#documentation)
- [Admin Panel](#admin-panel)

## Introduction

Pizza Creed is a Spring Boot application providing RESTful APIs for managing pizza orders, including listing available pizzas, creating shopping baskets, managing basket items, and checking out orders.

## Setup

### Prerequisites

Ensure you have the following installed on your system:

- [JDK](https://adoptopenjdk.net/)
- Maven Wrapper (included)
- [MySQL](https://dev.mysql.com/downloads/)

### Database Configuration

1. Create a MySQL database named `pizzacreed`.
2. Duplicate `application.properties.template` in `src/main/resources` and rename it to `application.properties`.
3. Update `application.properties` with your database details.

### Running the Application

1. Open a terminal and navigate to the project directory.
2. Run:
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```
   (On Windows, use `mvnw` instead of `./mvnw`)
3. The application should now be running at `http://localhost:8181`.

## Endpoints

Refer to the [Postman Collection](https://www.postman.com/speeding-capsule-928798/workspace/nibm-ead-2/collection/30452672-92f8720f-f44f-4fe4-b82f-9e1b98d1a2a5?action=share&creator=30452672) for detailed API endpoints and sample requests.

## Postman Collection

Explore and test the API using the [Postman Collection](https://www.postman.com/speeding-capsule-928798/workspace/nibm-ead-2/collection/30452672-92f8720f-f44f-4fe4-b82f-9e1b98d1a2a5?action=share&creator=30452672).

## Documentation

Check out the detailed API documentation on [Postman Documentation](https://documenter.getpostman.com/view/30452672/2s9YsNcpuD).

## Admin Panel

Access the admin panel at `http://localhost:8181/login` with:

- Username: admin
- Password: admin
