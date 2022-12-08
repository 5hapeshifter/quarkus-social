package io.github.dougllasfps.quarkusSocial.rest;

import io.github.dougllasfps.quarkusSocial.domain.model.Follower;
import io.github.dougllasfps.quarkusSocial.domain.model.User;
import io.github.dougllasfps.quarkusSocial.domain.repository.UserRepository;
import io.github.dougllasfps.quarkusSocial.rest.dto.FollowerRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(FollowerResource.class)
class FollowerResourceTest {

    @Inject
    UserRepository userRepository;

    Long userId;


    @BeforeEach
    @Transactional
    void setUp() {
        //usuario padrao dos testes
        var user = new User();
        user.setAge(30);
        user.setName("Fulano");
        userRepository.persist(user);
        userId = user.getId();

    }

    @Test
    @DisplayName("Should return 409 when followerId is equal to User id")
    public void saveUserAsFollowerTest() {
        var body = new FollowerRequest();
        body.setFollowerId(userId);

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .pathParam("userId", userId)
            .when()
                .put()
            .then()
                .statusCode(Response.Status.CONFLICT.getStatusCode())
                .body(Matchers.is("You can't follow yourself"));

    }

    @Test
    @DisplayName("Should return 404 when user id doesn't exist")
    public void userNotFoundTest() {

        var body = new FollowerRequest();
        body.setFollowerId(userId);
        var inexistenteUserId = 999;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .pathParam("userId", inexistenteUserId)
            .when()
                .put()
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());

    }

}