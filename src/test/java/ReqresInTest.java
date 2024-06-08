import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresInTest {
    /*
     1. Make POST request to https://reqres.in
     with body: {"email": "eve.holt@reqres.in","password": "cityslicka"}
     2. Get response:   {"token": "QpwL5tke4Pnpja7X4"}
     3. Check token is QpwL5tke4Pnpja7X4
      */
    @Test
    void loginTest() {
        String data = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"cityslicka\"}";
        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void negativeloginTest() {

        given()
                .log().uri()
                .body("123")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void singleUserTest() {
         /*
      1. Make request to "https://reqres.in/api/users/2"
      2. Get response:   {
            "data": {
                    "id": 2,
                    "email": "janet.weaver@reqres.in",
                    "first_name": "Janet",
                    "last_name": "Weaver",
                    "avatar": "https://reqres.in/img/faces/2-image.jpg"
        },
            "support": {
            "url": "https://reqres.in/#support-heading",
                    "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
        }
      3. Check data.first_name is Janet
       */
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.first_name", is("Janet"));
    }

    @Test
    void postCreateTest() {
        String data = "{\"name\": \"morpheus\",\"job\": \"leader\"}";
        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"));

    }

    @Test
    void patchUpdateTest() {
        String data = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";
        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("job", is("zion resident"));
    }

    @Test
    void deleteTest() {
        String data = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";
        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);

    }


    }
}

