# Mars Rover API

# Mars Rover API

# Mars Rover API

[![Build Status](https://github.com/craigkilpatrick/mars-rover-api/actions/workflows/dependency-check.yml/badge.svg)](https://github.com/craigkilpatrick/mars-rover-api/actions/workflows/dependency-check.yml)
[![Gradle Build](https://github.com/craigkilpatrick/mars-rover-api/actions/workflows/gradle.yml/badge.svg)](https://github.com/craigkilpatrick/mars-rover-api/actions/workflows/gradle.yml)
[![Code Coverage](https://codecov.io/gh/craigkilpatrick/mars-rover-api/branch/main/graph/badge.svg)](https://codecov.io/gh/craigkilpatrick/mars-rover-api)
[![Java Version](https://img.shields.io/badge/Java-17-blue?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Renovate](https://img.shields.io/badge/Renovate-enabled-brightgreen?logo=renovatebot)](https://github.com/craigkilpatrick/mars-rover-api/issues)
[![Latest Release](https://img.shields.io/github/v/release/craigkilpatrick/mars-rover-api?include_prereleases&logo=github)](https://github.com/craigkilpatrick/mars-rover-api/releases)
[![Security Status](https://img.shields.io/librariesio/github/craigkilpatrick/mars-rover-api)](https://github.com/craigkilpatrick/mars-rover-api/security/dependabot)
[![Open Issues](https://img.shields.io/github/issues/craigkilpatrick/mars-rover-api)](https://github.com/craigkilpatrick/mars-rover-api/issues)
[![Contributors](https://img.shields.io/github/contributors/craigkilpatrick/mars-rover-api?color=blue)](https://github.com/craigkilpatrick/mars-rover-api/graphs/contributors)

This repository is meant to serve as a starting point for the [Mars Rover Kata](https://kata-log.rocks/mars-rover-kata) exercise that is used to teach [Test Driven Development (TDD)](https://martinfowler.com/bliki/TestDrivenDevelopment.html).


## Description

> #### Your Task
> You’re part of the team that explores Mars by sending remotely controlled vehicles to the surface of the planet. Develop an API that translates the commands sent from earth to instructions that are understood by the rover.

> #### Requirements
> - You are given the initial starting point (x,y) of a rover and the direction (N,S,E,W) it is facing.
> - The rover receives a character array of commands.
> - Implement commands that move the rover forward/backward (f,b).
> - Implement commands that turn the rover left/right (l,r).
> - Implement wrapping at edges. But be careful, planets are spheres.
> - Implement obstacle detection before each move to a new square. If a given sequence of commands encounters an obstacle, the rover moves up to the last possible point, aborts the sequence and reports the obstacle.

## Prerequisites
Before running the Mars Rover API, make sure you have the following prerequisites installed on your system:

- Java Development Kit (JDK) 17
- [Docker](https://docs.docker.com/get-docker/) (for running the application in a containerized environment)
- [Docker Compose](https://docs.docker.com/compose/install/#scenario-one-install-docker-desktop)
- [Make](https://www.gnu.org/software/make/#download)
- [pre-commit](https://pre-commit.com/#install) (v3.2.0+)

## Running and developing locally

To run the Mars Rover API locally, follow these steps:

1. Clone the repository:

  ```bash
  git clone https://github.com/your-username/mars-rover-api.git
  ```

2. Navigate to the project directory:

  ```bash
  cd mars-rover-api
  ```

3. Build and test the application:

  ```bash
  make build
  ```

4. Run the application:

  ```bash
  make up
  ```

## API Definitions

### Swagger UI Endpoint

   - `<MARS ROVER API URL>/swagger-ui/index.html`(e.g. http://localhost:8080/swagger-ui/index.html)

With the application running, navigate to the Swagger UI page to view and test existing Mars Rover API endpoints.

### OpenAPI v3 Endpoint

   - `<MARS ROVER API URL>/v3/api-docs` (e.g. http://localhost:8080/v3/api-docs)

With the application running, navigate to the OpenAPI v3 definition page to see the Open API V3 definition for Mars Rover API.

## Usage

The `mars-rover-api` provides a RESTful API for managing rovers on Mars. This documentation explains how to interact with the rovers through HTTP requests.

## Base URL

The base URL for all requests is: `http://localhost:8080`

## Endpoints

### Get All Rovers

- URL: `/rovers`
- Method: `GET`
- Description: Retrieves a list of all rovers.
- Response Body: JSON array of rover objects.

### Create a New Rover

- URL: `/rovers`
- Method: `POST`
- Description: Creates a new rover.
- Request Body: JSON object representing the new rover.
- Response Body: JSON object representing the created rover.

### Get a Rover by ID

- URL: `/rovers/{id}`
- Method: `GET`
- Description: Retrieves a rover by its ID.
- Path Variable: `id` - The ID of the rover.
- Response Body: JSON object representing the rover.

### Process Commands for a Rover

- URL: `/rovers/{id}/commands`
- Method: `POST`
- Description: Processes a series of commands for a rover.
- Path Variable: `id` - The ID of the rover.
- Request Body: Array of characters representing the commands.
- Response Body: JSON object representing the updated rover.

#### Valid Rover Commands:
- `f`: Moves the rover forward one space based on its current direction.
- `b`: Moves the rover backward one space based on its current direction.
- `l`: Turns the rover 90 degrees to the left.
- `r`: Turns the rover 90 degrees to the right.

### Delete a Rover

- URL: `/rovers/{id}`
- Method: `DELETE`
- Description: Deletes a rover by its ID.
- Path Variable: `id` - The ID of the rover.

## Example Requests

1. Retrieve a list of all rovers:

    Request:
    ```bash
    curl -v localhost:8080/rovers
    ```
    Response Body:
    ```bash
    {"_embedded":{"roverList":[{"id":1,"x":0,"y":0,"direction":"N","_links":{"self":{"href":"http://localhost:8080/rovers/1"},"rovers":{"href":"http://localhost:8080/rovers"}}},{"id":2,"x":1,"y":2,"direction":"N","_links":{"self":{"href":"http://localhost:8080/rovers/2"},"rovers":{"href":"http://localhost:8080/rovers"}}},{"id":3,"x":3,"y":3,"direction":"W","_links":{"self":{"href":"http://localhost:8080/rovers/3"},"rovers":{"href":"http://localhost:8080/rovers"}}},{"id":4,"x":98,"y":98,"direction":"E","_links":{"self":{"href":"http://localhost:8080/rovers/4"},"rovers":{"href":"http://localhost:8080/rovers"}}}]},"_links":{"self":{"href":"http://localhost:8080/rovers"}}}
    ```
2. Retrieve a rover by its ID

    Request:
    ```bash
    curl -v localhost:8080/rovers/1
    ```
    Response Body:
    ```bash
    {"id":1,"x":0,"y":0,"direction":"N","_links":{"self":{"href":"http://localhost:8080/rovers/1"},"rovers":{"href":"http://localhost:8080/rovers"}}}
    ```
3. Move a rover forward two space and turn left

    Request:
    ```bash
    curl -X POST localhost:8080/rovers/1/commands -H 'Content-type:application/json' -d '["f","f","l"]'
    ```
    Response Body:
    ```bash
    {"id":1,"x":0,"y":2,"direction":"W","_links":{"self":{"href":"http://localhost:8080/rovers/1"},"rovers":{"href":"http://localhost:8080/rovers"}}}
    ```

## Postman

To utilize Postman for making API calls instead of using curl, follow these steps:

1. Download and install Postman from the official website: https://www.postman.com/downloads/
2. Launch Postman and create a new request by clicking on the "New" button.
3. Set the request method, URL, and headers as shown in the examples below.
4. For requests that require a request body, select the "Body" tab in Postman and choose the appropriate format (e.g., JSON). Then, enter the request body data.
5. Click the "Send" button to make the API call and view the response.

## Contributing

If you would like to contribute to the Mars Rover API, please follow these guidelines:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and commit them.
4. Push your changes to your forked repository.
5. Submit a pull request to the main repository.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.
