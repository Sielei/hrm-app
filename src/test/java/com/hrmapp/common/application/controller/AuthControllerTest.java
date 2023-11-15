package com.hrmapp.common.application.controller;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
class AuthControllerTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.4-alpine"));

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void shouldReturnJwtTokenOnSuccessfulAuthentication(){
        given().contentType(ContentType.JSON)
                .body(
                """
                        {
                            "username": "CorrectUser",
                            "password": "CorrectPassword"
                        }
                    """
                )
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("token", matchesRegex("^[A-Za-z0-9_-]{2,}(?:\\.[A-Za-z0-9_-]{2,}){2}$"));
    }
    @Test
    void shouldNotAllowDeactivatedUserToAuthenticate(){
        given().contentType(ContentType.JSON)
                .body(
                        """
                                {
                                    "username": "DeactivatedUser",
                                    "password": "DeactivatedUserPassword"
                                }
                            """
                )
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User is deactivated!"));
    }
    @Test
    void shouldNotAllowUserToAuthenticateWithWrongCredentials(){
        given().contentType(ContentType.JSON)
                .body(
                        """
                                {
                                    "username": "CorrectUser",
                                    "password": "WrongPassword"
                                }
                            """
                )
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("Failed to authenticate user! Username or password is incorrect!"));
    }

    @Test
    void shouldSendPasswordResetToken(){}

}