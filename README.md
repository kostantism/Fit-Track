# FitTrack – Distributed Fitness Management System

## Overview
FitTrack is a distributed fitness management system designed to streamline the organization and management of personal training sessions between customers and trainers. The system addresses the need for customers to find trainers and track progress, while allowing trainers to manage their availability and client goals efficiently.

---

## Functional Description

### Problem Statement
In a distributed environment, coordinating schedules and tracking fitness goals can be complex. FitTrack solves this by:
* **Enabling Customers** to find available trainers, book appointments, track their progress, and manage personal fitness goals.
* **Enabling Trainers** to define availability, manage appointments, and monitor the progress of their assigned clients.
* **Integrating External Services** such as weather forecasting for outdoor sessions and SMS/Email notifications for real-time updates.

### Key Features / Use Cases
* **Visitor (Unauthenticated User):** User registration, login, and viewing the list of available trainers (Name, Specialization, Area, Bio).
* **Customer:** Profile management (fitness goals, notes), appointment booking based on trainer availability with integrated weather forecasts, viewing/canceling appointments, and logging daily progress.
* **Trainer:** Managing availability slots, approving/rejecting appointments, viewing client goals/progress, and adding session notes or training plans.
* **External Services:** Weather forecasting, SMS notifications, and Email notifications.

### User Stories
* **As a Customer**, I want to see a trainer's availability for a specific date so I can book a session.
* **As a Customer**, I want to log my progress (weight, exercise time) to monitor my improvement over time.
* **As a Trainer**, I want to set my free time slots so customers know when they can book me.
* **As a Trainer**, I want to view my clients' progress to adjust their training programs accordingly.

---

## Assumptions
* **System:** Operates as a web-based application using **Thymeleaf** for server-side rendering and **REST APIs** for specific functions. It supports three roles: Customer, Trainer, and Visitor. It does not support concurrent editing of the same appointment by multiple users.
* **Users:** Users possess basic digital skills and use modern browsers. Trainers only access data for their own assigned clients.
* **Technology:** Built with **Java 21** and **Spring Boot** using a layered architecture (Presentation-Service-Domain-Persistence). Data access is handled via **Spring Data JPA/Hibernate**.
* **Security:** Authentication via **Spring Security** using **JWT** and HTTP sessions.
* **Deployment:** The system is designed for distributed environments. Secrets (API keys, JWT secrets) are managed via environment variables.

---

## Requirements

### Functional Requirements
* **Authentication:** Registration/Login with unique email and phone numbers; role-based access control (RBAC).
* **Appointments:** Prevention of past-dated or overlapping bookings; trainer approval workflow; maximum active appointment limits.
* **Progress Tracking:** Customers log data for specific dates (no future logging); trainers can review this history.
* **External Integration:** Integration with **OpenWeatherMap API** and the **NOC service** (extended with SendGrid for emails) for notifications.

### Non-Functional Requirements
* **Security:** Data protection via JWT; users only access their own data or relevant client data.
* **Reliability:** **PostgreSQL** ensures data integrity through constraints (e.g., unique emails).
* **Maintainability:** **Hexagonal Architecture** (Ports & Adapters) allows replacing components (like UI or external services) without core changes.

---

## Running the Database (PostgreSQL)

The application uses PostgreSQL, which runs inside a Docker container.

The database must be running before starting the backend.

From the root directory of the project:

```shell
  docker compose up -d
```

This starts the PostgreSQL database required by the backend.

To stop and remove the container and volumes:

```shell
  docker compose down -v
```

# Running the Main Application (FitTrack)
The main backend application is a Spring Boot application.

From the fit-track directory:

```shell
  ./mvnw spring-boot:run  # MacOS / Linux
  ./mvnw.cmd spring-boot:run  # Windows
```

The application will start on:

http://localhost:8080

Swagger / OpenAPI documentation is available at:

http://localhost:8080/swagger-ui.html

# External Services (Fit-Track-external-services – SMS & Email)

SMS and Email notifications are handled by an external service (Fit-Track-external-services).

To enable notifications:

Navigate to the DS-Lab-NOC-main project directory

Run the NOC service (Spring Boot application)

```shell
  $Env:SENDGRID_API_KEY="Your-SendGrid-API-Key"
  $Env:SENDGRID_EMAIL="Your-SendGrid-Email"

  ./mvnw spring-boot:run  # MacOS / Linux
  ./mvnw.cmd spring-boot:run  # Windows
```

If NOC is not running, the system will continue to work, but SMS and Email notifications will be logged as unavailable.

# Running the Fit-Track-Client (Optional)

Fit-Track-Client is an external Spring Boot client that consumes the FitTrack REST API using JWT authentication.

To run it:

Navigate to the Fit-Track-Client directory

Start the application:

```shell
  ./mvnw spring-boot:run  # MacOS / Linux
  ./mvnw.cmd spring-boot:run  # Windows
```

The client is optional and demonstrates external API consumption with JWT.
