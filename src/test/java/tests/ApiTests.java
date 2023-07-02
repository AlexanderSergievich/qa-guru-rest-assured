package tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.UserBodyModel;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class ApiTests {
    public String newUserId = "";
    @Test
    @Tag("e2e")
    public void GetSingleUserTest(){
        given().get("https://reqres.in/api/users/2")
                .then() //блок после запроса
                .log().all()
                .statusCode(200)
                .body("data.email", Matchers.equalTo("janet.weaver@reqres.in"));
    }
    @Test
    @Tag("e2e")
    public void UserIsNotFoundTest(){
        given().get("https://reqres.in/api/users/23")
                .then()
                .log().all()
                .statusCode(404);
    }
    @Test
    @Tag("e2e")
    public void CreateAndValidateUserTest(){
        UserBodyModel userBodyModel = new UserBodyModel();
        userBodyModel.setName("sandro");
        userBodyModel.setJob("manager");
        Response response = given().body(userBodyModel)
                .header("Content-Type","application/json")
                .log().uri()
                .log().body()
                .when()
                .post("https://reqres.in/api/users");
                response.then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("name", equalTo(userBodyModel.getName()))
                .body("job", equalTo(userBodyModel.getJob()))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }
    @Test
    @Tag("e2e")
    @ValueSource()
    public void UpdateAndValidateUserTest(){
        Response response = given().body("{\n"+"\"name\":\"Alex\",\n"+"\"job\":\"TopManager\"\n"+"}")
                .header("Content-Type","application/json")
                .log().uri()
                .log().body()
                .when()
                .put("https://reqres.in/api/users/2");
        response.then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo("Alex"))
                .body("job", equalTo("TopManager"))
                .body("updatedAt", notNullValue());
    }
    @Test
    @Tag("e2e")
    public void DeleteUserTest(){
        given().delete("https://reqres.in/api/users/2")
                .then() //блок после запроса
                .log().all()
                .statusCode(204);
    }
    @Test
    @Tag("e2e")
    public void SelectAllUsersTest(){
        Response response =
                given().get("https://reqres.in/api/users?page=2");
        response.then() //блок после запроса
                .log().all()
                .statusCode(200);
        JsonPath jsonPath = response.jsonPath();
        List<Integer> ids = jsonPath.getList("data.id");
        boolean idExists = ids.contains(12);
        Assertions.assertTrue(idExists);
        }
}