
# Prices Microservice

Service to know the price of a product in a period of time.

## Tech Stack

* Java 21
* OpenApi
* Spring Boot 3.3.0
* Spring Boot Data JPA
* Spring Boot Validation
* Spring Boot Web
* H2
* Cucumber
* MapStruct
* Lombok


## Documentation

Built following the API-First approach in hexagonal architecture, tested its behaviour with Cucumber (Gherkin) in Java.

Endpoints published with OpenApi


## API Reference
For more detail, check the `openapi.yml`

#### Get prices

```http
  GET /prices
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `price_date` | `string` | **Required**. The date in which the price must apply |
| `product_id` | `integer` | **Required**. The product to apply the price |
| `brand_id` | `integer` | **Required**. The product's brand |

Returns a price to apply to requested product in requested date.

## Prerequisites

Java 21 must be installed locally in order to run the application.

## Installation

Invoke the build (at the root of the project):

```bash
  $ ./gradlew clean build
```

## Run Locally

Execute the `main` method in the `com.mdelamo.prices.Application` class from your IDE.

## Run Locally

One way is to execute the `main` method in the `com.mdelamo.prices.Application` class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:

```bash
  $ ./gradlew bootRun
```

## Running Tests

To run unit and integration tests, run the following command (at the root of the project):

```bash
  $ ./gradlew clean test
```

Also, the service is tested with acceptance tests with Cucumber.
To run the acceptance tests run the following command (at the root of the project):
```bash
  $ ./gradlew clean test cucumber
```
