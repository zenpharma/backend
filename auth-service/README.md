# auth-service

JWT authentication and user registration (Spring Boot 3.2, Java 17, PostgreSQL via Flyway).

## Run locally

1. Build the jar: `mvn -B -q package -DskipTests`
2. Start PostgreSQL 16 with a database named `pharmadb` and a user named `pharma`.
3. Start the service with `DB_HOST`, `DB_PASSWORD` and `JWT_SECRET` set (never commit real values).
4. Check `GET /api/auth/health`, then try `POST /api/auth/register` and `POST /api/auth/login`.
