package io.github.dougllasfps.quarkusSocial.rest;

import io.github.dougllasfps.quarkusSocial.domain.model.User;
import io.github.dougllasfps.quarkusSocial.domain.repository.UserRepository;
import io.github.dougllasfps.quarkusSocial.rest.dto.CreatePostRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(PostsResource.class) // estamos informando qual endpoint sera testado attraves do metodo Post
class PostsResourceTest {

    @Inject
    UserRepository userRepository;
    Long userId;

    @BeforeEach
    @Transactional
    public void setUp() {
        var user = new User();
        user.setAge(30);
        user.setName("Fulano");

        userRepository.persist(user);
        userId = user.getId();
    }

    @Test
    @DisplayName("Should create a post for a user")
    public void createPostTest() {
        var postRequest = new CreatePostRequest();
        postRequest.setText("Some text");

        var userId = 1;
        given()
                .contentType(ContentType.JSON)
                .body(postRequest)
                .pathParam("userId", userId)
            .when()
                .post()
            .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Should return 404 when trying to make a post for an inexistent user")
    public void inexistentUserTest() {
        var postRequest = new CreatePostRequest();
        postRequest.setText("Some text");

        var inexistentUserId = 999;
        given()
                .contentType(ContentType.JSON)
                .body(postRequest)
                .pathParam("userId", inexistentUserId)
            .when()
                .post()
            .then()
                .statusCode(404);
    }


}