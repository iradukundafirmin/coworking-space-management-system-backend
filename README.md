# Coworking Space Management System Backend
### Firmin Iradukunda ID : 27316

A Spring Boot REST API for managing coworking-space facilities, floors, hosts, members, desk bookings and usage charges.

## Technologies
- Java / Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Postman

## Steps to run the API

1. Install a compatible JDK, Maven (if the project has no Maven wrapper), and PostgreSQL.
2. Start PostgreSQL and create a database named `coworking_db`.
3. Open the project in your IDE.
4. Open `src/main/resources/application.properties` and confirm the PostgreSQL URL, username and password match your local database.
5. Run the application from the main Spring Boot application class, or from the project folder run:

   ```bash
   ./mvnw spring-boot:run
   ```

   On Windows:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

   If there is no Maven wrapper, use `mvn spring-boot:run`.

6. When startup completes successfully, the API base URL is `http://localhost:8080`.
