# Spring Boot Authentication using JWT

## Features

- Public endpoints accessible without authentication.
- Private endpoints requiring authentication.
- Role-based access control (e.g., user, admin).

## Endpoints

- `/public`: Accessible by anyone.
- `/private`: Requires authentication.
- `/auth/register`: For user registration.
- `/auth/login`: For user login and JWT token generation.
- `/user/info`: Returns user's email and role information.
- `/admin`: Requires admin role.

## Prerequisites

- Java 17 or later
- Maven
- Postman (to test API endpoints)
- Docker (optional, to run mysql instance in container)
- DBeaver (optional, to connect to MySQL database)

## Database Configuration

Ensure your MySQL database is configured correctly. You can use `compose.yaml` for Docker-based setup or configure it manually.

### Using `compose.yaml`

1. **Open Docker Desktop (to start docker engine).**
2. **Run the following command in the terminal (you can open terminal directly in IntelliJ IDEA):**
    ```bash
    docker-compose up
    ```

This will start a MySQL instance in a Docker container using the configuration provided in `compose.yaml`.

### Testing the Application

1. **Access public endpoint:**
    - Open Postman and send a GET request to `http://localhost:8080/public`.
    - This should be accessible without authentication.

2. **Access private endpoint:**
    - Send a GET request to `http://localhost:8080/private`.
    - This should return unauthorized error.

3. **Register (and optionally obtain JWT token):**
    - Send a POST request to `http://localhost:8080/auth/register` e.g. with the following JSON body:
        ```json
        {
            "firstName": "John",
            "lastName": "Doe",
            "password": "qwerty",
            "email": "john@gmail.com"
        }
        ```
    - You will receive a JWT token in the response.

4. **Login and obtain JWT token:**
    - In the future (e.g. after token expires) send a POST request to `http://localhost:8080/auth/login` with the following JSON body:
        ```json
        {
            "email": "john@gmail.com",
            "password": "qwerty"
        }
        ```
    - You will receive a JWT token in the response.

5. **Access private endpoint with JWT token:**
    - Copy the token from the register/login response.
    - In Postman, set the token in the Authorization header:
        - Type: Bearer Token
        - Token: [your-token-here]
    - Send a GET request to `http://localhost:8080/private`.
    - You should now be able to access this endpoint.

6. **Check user info:**
    - Send a GET request to `http://localhost:8080/user/info` with the Bearer token set in the Authorization header.
    - This will return your email and role information.

7. **Access admin endpoint:**
    - Initially, you won't have access to `/admin`.
    - Connect to the MySQL database using the credentials specified in `application.properties` or `compose.yaml` if your using docker compose or with your own credentials.
    - Update your role to `admin` in the database in user table.
    - Send a GET request to `http://localhost:8080/admin` with the Bearer token set in the Authorization header.
    - You should now be able to access this endpoint.
  
## Things You Might Want to Consider Adding/Changing

1. **Add Refresh Token:**
    - Implement refresh tokens to allow clients to obtain a new JWT without requiring the user to re-authenticate.

2. **Adjust Expiration Time of the Token:**
    - Modify the token expiration time based on your application's security and usability requirements.

3. **Store `SECRET_KEY` More Safely:**
    - Use a secure method to store the `SECRET_KEY`, such as HashiCorp Vault or another secrets management service.

4. **Store User Role in Token:**
    - Include the user's role in the JWT token to avoid querying the database on each request to get the user's role.
    - This can improve the performance and scalability of your application by reducing database load.
