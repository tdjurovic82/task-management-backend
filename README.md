# OSS Task Management Platform – Backend

This project is a backend service built with Java and Spring Boot, simulating an OSS (Operations Support System) task and provisioning management platform.

It provides RESTful APIs for managing tasks, users, and locations (UPRNs), and simulates provisioning flows such as ONT activation on OLTs covering specific areas.

The backend is designed to support a basic Angular frontend application.

---

## Features

- User and task management via REST APIs
- DTO mappings and global exception handling
- API key authentication for secured endpoints
- Location (UPRN) handling
- Provisioning flow simulation (activating ONT on OLT that covers a certain area)
- Error handling with custom exceptions
- Integrated Swagger UI (OpenAPI) for API documentation and testing

---

## Technologies Used

- Java 21
- Spring Boot
- Spring Security (API Key based)
- JPA / Hibernate
- H2 Database (embedded)
- Git / GitHub

---

## Notes

This project is for demonstration purposes only and does not reflect any real-world company's internal systems.  

An embedded H2 database with dummy data (users, tasks, locations) is included for testing and showcasing purposes.

The backend is designed to integrate with an Angular frontend that consumes these APIs.

API documentation is available via [Swagger UI](http://localhost:8080/swagger-ui/index.html).
