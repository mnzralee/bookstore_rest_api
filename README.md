# Bookstore API

## Table of Contents

- [Project Description](#project-description)  
- [Features](#features)  
- [Technical Architecture](#technical-architecture)  
- [Getting Started](#getting-started)  
  - [Prerequisites](#prerequisites)  
  - [Installation](#installation)  
  - [Configuration](#configuration)  
  - [Running the Application](#running-the-application)  
- [API Endpoints](#api-endpoints)  
  - [Book Resource](#book-resource)  
  - [Author Resource](#author-resource)  
  - [Customer Resource](#customer-resource)  
  - [Cart Resource](#cart-resource)  
  - [Order Resource](#order-resource)  
- [Exception Handling](#exception-handling)  
- [Data Models](#data-models)  
  - [Book](#book)  
  - [Author](#author)  
  - [Customer](#customer)  
  - [Cart](#cart)  
  - [CartItem](#cartitem)  
  - [Order](#order)  
- [Technologies Used](#technologies-used)  
- [Database](#database)  
- [Deployment](#deployment)  
- [Testing](#testing)  
  - [Unit Testing](#unit-testing)  
  - [Integration Testing](#integration-testing)  
- [Contributing](#contributing)  
- [License](#license)  
- [Contact](#contact)  
- [Acknowledgments](#acknowledgments)  

---

## Project Description

The **Bookstore API** is a RESTful web service built to manage an online bookstore. It supports core features such as CRUD operations for books, authors, customers, shopping carts, and orders. This API serves as the backend for web or mobile clients and was developed as part of a university coursework assignment.

---

## Features

- **Book Management:** Create, retrieve, update, and delete books  
- **Author Management:** CRUD operations and fetch books by author  
- **Customer Management:** Manage customer profiles  
- **Cart Management:** Add, update, and remove items in customer carts  
- **Order Management:** Place orders based on cart contents  
- **Exception Handling:** Returns meaningful HTTP error codes and JSON messages  
- **Data Validation:** Ensures integrity through input validation  
- **RESTful Design:** Resource-oriented and uses standard HTTP methods  

---

## Technical Architecture

- **Language:** Java  
- **Framework:** JAX-RS (Jakarta RESTful Web Services)  
- **Storage:** In-memory HashMap (dev); switchable to relational DB (e.g., MySQL)  
- **App Server:** Servlet container (e.g., Tomcat) or embedded Jetty  
- **Data Format:** JSON  
- **Build Tool:** Maven  
- **Exception Handling:** Custom exceptions with JAX-RS `ExceptionMapper`  
- **Design Pattern:** RESTful resource-oriented  

---

## Getting Started

### Prerequisites

- Java JDK 11+  
- Maven 3.6.0+  
- Git (optional)  
- IDE (IntelliJ IDEA or Eclipse recommended)

### Installation

```bash
git clone <repository_url>
cd bookstore-api
```

Or download the ZIP and extract manually.

### Configuration

- The `web.xml` file configures the application path (e.g., `/api`).

### Running the Application

#### 1. Using Maven and Tomcat

```bash
mvn clean package
```

- Deploy the resulting `.war` file from the `target` directory to Tomcat’s `webapps` folder.
- Access the app via `http://localhost:8080/bookstore-api/api`

#### 2. Running with Embedded Server (if configured)

```bash
mvn exec:java
```

Ensure `exec-maven-plugin` is configured.

---

## API Endpoints

### Book Resource

- `POST /books` — Create a book  
- `GET /books` — Retrieve all books  
- `GET /books/{id}` — Retrieve a book by ID  
- `PUT /books/{id}` — Update a book  
- `DELETE /books/{id}` — Delete a book  

### Author Resource

- `POST /authors` — Create an author  
- `GET /authors` — Retrieve all authors  
- `GET /authors/{id}` — Retrieve an author by ID  
- `PUT /authors/{id}` — Update an author  
- `DELETE /authors/{id}` — Delete an author  
- `GET /authors/{id}/books` — Books by a specific author  

### Customer Resource

- `POST /customers` — Create a customer  
- `GET /customers` — Retrieve all customers  
- `GET /customers/{id}` — Retrieve a customer  
- `PUT /customers/{id}` — Update a customer  
- `DELETE /customers/{id}` — Delete a customer  

### Cart Resource

- `POST /customers/{customerId}/cart/items` — Add book to cart  
- `GET /customers/{customerId}/cart` — View cart  
- `PUT /customers/{customerId}/cart/items/{bookId}` — Update quantity  
- `DELETE /customers/{customerId}/cart/items/{bookId}` — Remove item  

### Order Resource

- `POST /customers/{customerId}/orders` — Create an order from cart  
- `GET /customers/{customerId}/orders` — View all orders for a customer  
- `GET /customers/{customerId}/orders/{orderId}` — View a specific order  

---

## Exception Handling

The API provides meaningful error messages and status codes using custom exception classes and `ExceptionMapper`s.

Common HTTP Status Codes:

- `400 Bad Request` — Invalid input  
- `404 Not Found` — Resource not found  
- `500 Internal Server Error` — Unexpected errors  

---

## Data Models

### Book

```json
{
  "id": 1,
  "title": "The Lord of the Rings",
  "authorId": 1,
  "price": 25.99,
  "stock": 10
}
```

### Author

```json
{
  "id": 1,
  "firstName": "J.R.R.",
  "lastName": "Tolkien"
}
```

### Customer

```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com"
}
```

### Cart

```json
{
  "customerId": 1,
  "items": [ ... ]
}
```

### CartItem

```json
{
  "bookId": 1,
  "quantity": 2
}
```

### Order

```json
{
  "orderId": 101,
  "customerId": 1,
  "items": [ ... ],
  "total": 51.98,
  "status": "PLACED"
}
```

---

## Technologies Used

- Java 11+  
- JAX-RS  
- Maven  
- JSON  
- Tomcat / Jetty  

---

## Database

Currently uses an in-memory `HashMap` for simplicity. Recommended to switch to a relational database such as MySQL or PostgreSQL in production.

---

## Deployment

1. Build the `.war` file using `mvn clean package`  
2. Deploy to Tomcat (or your preferred servlet container)  
3. Ensure your servlet path matches your configured API base (e.g., `/api`)  

---

## Testing

### Unit Testing

- Isolated service-level tests  
- Mock dependencies and validate business logic  

### Integration Testing

- Test complete API workflows using mock clients  
- Validate endpoint behavior and data flows  

---

## Contributing

Contributions are welcome. Please fork the repository, make your changes, and open a pull request with a detailed description of the fix or feature.

---

## Contact

If you have any questions or suggestions, feel free to open an issue or contact the maintainer.

---

## Acknowledgments

This project was developed as part of a university coursework submission. Thanks to the instructors and peers for their support and feedback.
