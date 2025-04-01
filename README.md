# Aurora Spring Boot Project

## Introduction
Aurora Spring Boot is a LLM application which show you how to create LLM application with spring-boot and spring-ai.

## Getting Started

### Prerequisites
- Docker installed on your local machine.
- Java 17 or higher.
- Maven for dependency management.

### Steps

#### 1. Clone the Repository

#### 2. Put your documents with Markdown format in `doc[material](material)/` directory

#### 3. Add your OpenAI API key into [application.yml](src/main/resources/application.yml)

#### 4. Run the Application
Navigate to the project directory and run the `AuroraJApplication.java` file. You can use your IDE (e.g., IntelliJ IDEA, Eclipse) or run it from the command line using Maven:
```sh
mvn spring-boot:run
```

#### 5. Call the Learn Endpoint
To prepare the data in Redis, send a POST request to the learn endpoint:
```sh
curl -XPOST http://localhost:8080/learn
```

#### 6. Call the Chat Endpoint
```sh
curl --location --request POST 'http://localhost:8080/chat' \
--header 'Content-Type: application/json' \
--data-raw '{
    "input": "whatever you want to ask for the AI"
}'
```

## Coding Design

### Layer Structure
The project is structured into several layers, each serving a specific purpose. The main layers are as follows:
```text
├── application ----> Contains the main application logic
├── core        ----> Contains the core framework support 
├── domain      ----> Contains the domain model and business logic, you can see it as the data property of the application
├── endpoint    ----> Provides the interface for http and so on.
```

### Data and Models
- **Command**: It is used in application layer to transfer the application usecase data.
- **Dto**: Represents a domain model which can carry the domain data.
- **Request**: It represents the request data from the client.
- **Response**: It represents the response data to the client.

Usually, you receive the user input in endpoint layer by Request,
and then you transfer it to the application layer by Command. 
After the application layer process the command, it will return a Dto or some model defined in the application layer, 
which will be transferred to the endpoint layer by Response.

## License
This project is licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

## Contact
If you have any questions or suggestions regarding this project, please feel free to contact the project maintainers.