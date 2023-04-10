import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApiTest {
    // Base URL of the API
    String baseURL = "https://fakerestapi.azurewebsites.net/api/v1/Users";
    User user = new User();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void verifyResponseCode() {
        given()
                .when()
                .get(baseURL)
                .then()
                .statusCode(200);
    }

    @Test
    public void checkUser6() throws IOException {
        User[] users = given()
                .when()
                .get(baseURL)
                .then()
                .extract()
                .as(User[].class);

        User user6 = Arrays.stream(users)
                .filter(u -> u.getId() == 6)
                .findFirst()
                .orElse(null);

        assertNotNull(user6);
        assertEquals("User 6", user6.getUserName());

        String user6Json = objectMapper.writeValueAsString(user6);
        User user6Deserialized = objectMapper.readValue(user6Json, User.class);
        assertEquals(user6, user6Deserialized);
    }

    @Test
    public void countObjects() {
        User[] users = given()
                .when()
                .get(baseURL)
                .then()
                .extract()
                .as(User[].class);

        assertEquals(users.length, 10);
    }

}
