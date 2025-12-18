# Style-On

A Spring Boot application for style recommendations, leveraging AI to provide fashion insights.

## ✨ Features

*   **Framework**: [Spring Boot 3](https://spring.io/projects/spring-boot) with WebFlux for reactive programming.
*   **Database**: [PostgreSQL](https://www.postgresql.org/) for data storage, managed with [Flyway](https://flywaydb.org/) for database migrations.
*   **Containerization**: Fully containerized with [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/) for easy setup and deployment.
*   **Authentication**: Secure endpoints using JSON Web Tokens (JWT).
*   **AI Integration**: Utilizes Google's Generative AI for intelligent features.
*   **API Documentation**: Interactive API documentation provided by [SpringDoc](https://springdoc.org/) (Swagger UI).

## ✅ Prerequisites

Before you begin, ensure you have the following installed on your system:

*   [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
*   [Maven](https://maven.apache.org/download.cgi)
*   [Docker](https://docs.docker.com/get-docker/)
*   [Docker Compose](https://docs.docker.com/compose/install/)

## 🚀 Getting Started

Follow these instructions to get the application running on your local machine.

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd style-on
```

### 2. Create Environment File

The application uses an `.env` file to manage environment variables and secrets. Create a file named `.env` in the root of the project with the following content.

**Note**: Replace the placeholder values (`your_...`) with your actual credentials.

```env
# PostgreSQL Credentials
POSTGRES_USER=your_postgres_user
POSTGRES_PASSWORD=your_postgres_password
POSTGRES_DB=style-on

# JWT Secret
JWT_SECRET_KEY=your_strong_jwt_secret_key_here

# Google Gemini API Key
GEMINI_API_KEY=your_gemini_api_key

# Elasticsearch Configuration (if used)
ELASTICSEARCH_URIS=http://localhost:9200
ELASTICSEARCH_USERNAME=
ELASTICSEARCH_PASSWORD=
ELASTICSEARCH_INDEX_PRODUCT=products
```

### 3. Build and Run with Docker Compose

Once the `.env` file is configured, you can build and run the application and the database using a single command:

```bash
docker-compose up --build
```

This command will:
1.  Build the `style-on` application image from the `Dockerfile`.
2.  Pull the `postgres:18.1` image.
3.  Start both services in the background.

The application will be available at `http://localhost:8090`.

## 🛠️ Running Locally (Without Docker)

If you prefer to run the application directly on your host machine:

1.  Ensure you have a PostgreSQL instance running and accessible.
2.  Update the `application.properties` file or set the environment variables from the `.env` file in your shell. The database URIs will need to point to your local PostgreSQL instance (e.g., `jdbc:postgresql://localhost:5432/style-on`).
3.  Run the application using the Maven wrapper:
    ```bash
    ./mvnw spring-boot:run
    ```

## 📚 API Documentation

Once the application is running, you can access the interactive Scalar UI to explore and test the API endpoints.

*   **Scalar UI URL**: [http://localhost:8090/scalar](http://localhost:8090/scalar)

## 🐳 Stopping the Application

To stop the Docker Compose services, press `CTRL+C` in the terminal where `docker-compose` is running, or run the following command from the project root:

```bash
docker-compose down
```
