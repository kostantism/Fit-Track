# FitTrack – Distributed Fitness Management System

## Overview
FitTrack is a distributed fitness management system that supports customers and trainers.
The system provides user registration and authentication, appointment booking, trainer availability,
customer progress tracking, and integration with external services (weather, SMS, email).

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

# External Services (DS-Lab-NOC-main – SMS & Email)

SMS and Email (mock) notifications are handled by an external service (DS-Lab-NOC-main).

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