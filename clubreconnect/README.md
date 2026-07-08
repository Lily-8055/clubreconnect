# ClubReconnect Project

This is a complete Spring Boot application for the ClubReconnect platform.

## Prerequisites
- **Java 17** or higher
- **Maven**
- **MySQL Server** running on localhost:3306

## Setup Instructions

### 1. Database Setup
Create a database named `clubreconnect_db` in your MySQL instance:
```sql
CREATE DATABASE clubreconnect_db;
```

### 2. Configuration
Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 3. Run the Application
Navigate to the project root and run:
```bash
mvn spring-boot:run
```

### 4. Access the App
Open your browser and go to:
[http://localhost:8080](http://localhost:8080)

## Features
- **User Profiles**: Custom bios and graduation years.
- **Club Hub**: Create and explore community groups.
- **Events**: Schedule and view activities within clubs.
- **Responsive UI**: Modern design using Bootstrap 5.
