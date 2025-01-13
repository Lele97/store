# Store Management System
Welcome to the Store Management System project! This repository contains the code for a comprehensive application designed to manage store inventories, orders, and products efficiently.

## Table of Contents

- Introduction
- Features
- Technologies Used
- Application endpoint
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

## Application endpoint

### Api Gateway Endpoint

 - POST  /api/v1/users/register
   - Register new user

 - POST /api/v1/users/login
   - Get token
 
 - POST /api/v1/products
   - Add product {required token}

 - GET /api/v1/products
   - Get Products {required token}

 - GET /api/v1/orders/{id}
   - Get order by id {required token}
   
 - POST /api/v1/orders
   - Add a new order {required token}
 
## License
This project is licensed under the MIT License.
