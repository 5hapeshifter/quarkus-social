package io.github.dougllasfps.quarkusSocial.rest;

import io.github.dougllasfps.quarkusSocial.rest.dto.CreateUserRequest;
import io.github.dougllasfps.quarkusSocial.rest.dto.ResponseError;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserResourceTest {

    @TestHTTPResource("/users")
    private URL apiUrl;

    @Test
    @DisplayName("Should create an user succesfully")
    @Order(1)
    public void createUserTest() {
        var user = new CreateUserRequest();
        user.setName("Fulano");
        user.setAge(30);

        Response response =
                given()
                    .contentType(ContentType.JSON)
                    .body(user)
                .when()
                    .post(apiUrl)
                .then()
                .extract().response();

       assertEquals(201, response.statusCode());
       assertNotNull(response.jsonPath().getString("id")); // estamos conferindo o conteúdo da resposta
    }

    @Test
    @DisplayName("Should return error when json is not valid")
    @Order(2)
    public void createUserValidationErrorTest() {
        var user = new CreateUserRequest();
        user.setName(null);
        user.setAge(null);

        var response =
                given()
                        .contentType(ContentType.JSON)
                        .body(user)
                    .when()
                        .post("/users")
                    .then()
                        .extract().response();

        assertEquals(ResponseError.UNPROCESSABLE_ENTITY_STATUS, response.statusCode());
        assertEquals("Validation Error", response.jsonPath().getString("message")); // Buscando conteudo na resposta do erro

        List<Map<String, String>> errors = response.jsonPath().getList("errors");//pegando a lista de erros
        assertNotNull(errors.get(0).get("message"));
        assertNotNull(errors.get(1).get("message"));
        // assertEquals("Age is required", errors.get(0).get("message")); // o test da errado as vezes porque o spring mapeia de forma desordenada
        // assertEquals("Name is required", errors.get(1).get("message"));
    }

    @Test
    @DisplayName("Should return all users")
    @Order(3) // esse teste passa porque na ordem de execucao estabelecemos ele como o ultimo teste utilizando o @TestMethodOrder e @Order, por conta da insercao de objetos no BD ocorrer nos outros metodos
    public void listAllUsersTest() {
        given()
                .contentType(ContentType.JSON)
            .when()
                .get(apiUrl)
            .then()
                .statusCode(200)
                .body("size()", Matchers.is(1));
    }

}