# Aurora Spring Boot Project

## Introduction
Aurora Spring Boot is a powerful application that integrates chat services with knowledge learning features, leveraging the Spring Boot framework and advanced AI technologies. This README provides a comprehensive guide on how to get started, understand the coding design, and view the to - do list for the project.

## Getting Started

### Prerequisites
- Docker installed on your local machine.
- Java 17 or higher.
- Maven for dependency management.

### Steps

#### 1. Run Redis with Docker Locally
Execute the following command in your terminal to start a Redis stack container:
```sh
docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 -e REDIS_ARGS="--requirepass 111111" redis/redis-stack:latest
```

#### 2. Run the Application
Navigate to the project directory and run the `AuroraJApplication.java` file. You can use your IDE (e.g., IntelliJ IDEA, Eclipse) or run it from the command line using Maven:
```sh
mvn spring-boot:run
```

#### 3. Call the Learn Endpoint
To prepare the data in Redis, send a POST request to the learn endpoint:
```sh
curl -XPOST http://localhost:8080/learn
```

#### 4. Call the Chat Endpoint
Send a POST request to the chat endpoint to start a chat session. Here is an example:
```sh
curl --location --request POST 'http://localhost:8080/chat' \
--header 'Content-Type: application/json' \
--data-raw '{
    "input": "I want a summary of the detailed design of the order system"
}'
```

## Coding Design

### Layer Structure
The project follows a well - structured layered architecture:

```text
├── ability     ---> Contains various abilities, such as function tools and knowledge management.
├── application ---> Responsible for AI logic, excluding data logic.
├── configure   ---> Holds the configuration for the entire application across all layers.
├── domain      ---> Defines the domain models for core data properties.
├── endpoint    ---> Provides endpoints for HTTP RESTful requests.
└── infra       ---> Implements the logic for each layer.
```

### Current Situation
Currently, all implementations are under the `infra` layer. Each super - layer provides core logic and interfaces, which helps in maintaining a clear separation of concerns and enhancing the maintainability and scalability of the codebase.

### Data Transfer Objects and Models
- **Request**: Mutable for serialization purposes. It is used to transfer data from the client to the server in a RESTful API.
- **VO (Value Object)**: Mutable for serialization. It is often used to return data to the client in a structured format.
- **Model**: Immutable for domain data. In the context of this application, the domain model can be considered as the detailed product of the application, representing the core business entities.

## To - Do List

### Document - related Tasks
- **Split the Document**: Improve the readability and maintainability of the project's documentation by splitting large documents into smaller, more manageable parts.

### User Input Handling
- **Orchestrate User Input**: Develop a mechanism to route user input into different handling flows based on specific rules or conditions, enhancing the flexibility and adaptability of the application.

## Dependencies
The project uses several dependencies managed by Maven. Some of the key dependencies include:
- Spring Boot Starter Web for building web applications.
- Spring AI OpenAI Starter for integrating with OpenAI's AI services.
- Springdoc OpenAPI Starter for generating API documentation.

For a complete list of dependencies, please refer to the `pom.xml` file.

## Testing
The project includes unit tests to ensure the correctness of individual components. You can run the tests using the following Maven command:
```sh
mvn test
```

## License
This project is licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

## Contact
If you have any questions or suggestions regarding this project, please feel free to contact the project maintainers.