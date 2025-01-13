# Store Management System
Welcome to the Store Management System project! This repository contains the code for a comprehensive application designed to manage store inventories, orders, and products efficiently.

## Table of Contents

- Introduction
- Features
- Technologies Used
- Getting Started
- Installation
- Configuration
- Usage
- Running Tests
- Contributing
- License

## Introduction
The Store Management System is a robust application that helps manage the inventory, orders, and products of a store. It is built using modern technologies to ensure efficiency, scalability, and ease of use.

## Features

- User Authentication: Secure login and registration.
- Product Management: Create, read, update, and delete products.
- Order Management: Create, read, update, and delete orders.
- Inventory Management: Track and manage product inventory.
- RESTful API: Easily integrate with other systems and services.

## Technologies Used
- Spring Boot: Backend framework for building the application.
- Hibernate: ORM tool for database interactions.
- MySQL: Database for storing data.
- Maven: Build and dependency management tool.
- JUnit: Testing framework.


Product Endpoints
GET /api/v1/products: Retrieve all products
GET /api/v1/products/{id}: Retrieve a product by ID
POST /api/v1/products: Add a new product
PUT /api/v1/products/{id}: Update an existing product
DELETE /api/v1/products/{id}: Delete a product

Order Endpoints
GET /api/v1/orders: Retrieve all orders
GET /api/v1/orders/{id}: Retrieve an order by ID
POST /api/v1/orders: Add a new order
PUT /api/v1/orders/{id}: Update an existing order
DELETE /api/v1/orders/{id}: Delete an order

## License
This project is licensed under the MIT License.
