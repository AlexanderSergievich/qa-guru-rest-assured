package tests;

import models.ExistingUserResponseModel;
import models.NewUserBodyModel;
import models.NewUserResponseModel;
import models.UpdatedUserResponseModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import specs.UserSpecs;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.UserSpecs.*;
public class ApiTests {
    public String baseUrl = "https://reqres.in/api/users/";

    @Test
    @Tag("Ready")
    public void GetSingleUserTest(){
       ExistingUserResponseModel existingUser = step("Send request", ()->
                  given(UserSpecs.requestSpecification).get(baseUrl+"2")
                          .then()
                          .spec(getUserResponseSpec)
                          .extract().as(ExistingUserResponseModel.class));
        step("Check response", ()->
       assertEquals("janet.weaver@reqres.in", existingUser.getData().getEmail()));
    }
    @Test
    @Tag("Ready")
    public void UserIsNotFoundTest(){
        step("Check not found response", ()->
                given(requestSpecification)
                    .get(baseUrl+"23")
                    .then()
                    .spec(notFoundUserResponseSpec));
    }
    @Test
    @Tag("Ready")
    public void CreateAndValidateUserTest(){
        NewUserBodyModel newUserBodyModel = new NewUserBodyModel();
        newUserBodyModel.setName("sandro");
        newUserBodyModel.setJob("manager");

        NewUserResponseModel newUserResponseModel = step("Send request", ()->
                given(UserSpecs.requestSpecification).body(newUserBodyModel)
                    .when()
                    .post(baseUrl)
                    .then()
                    .spec(createUserResponseSpec)
                    .extract().as(NewUserResponseModel.class));
        step("Check response", () -> {
            assertEquals("sandro", newUserResponseModel.getName());
            assertEquals("manager", newUserResponseModel.getJob());
            assertNotNull(newUserResponseModel.getId());
            assertNotNull(newUserResponseModel.getCreatedAt());
        });
    }
    @Test
    @Tag("Ready")
    public void UpdateAndValidateUserTest() {
        NewUserBodyModel newUserBodyModel = new NewUserBodyModel();
        newUserBodyModel.setName("Alex");
        newUserBodyModel.setJob("TopManager");

        UpdatedUserResponseModel updatedUserResponseModel = step("Send request", () ->
                given(UserSpecs.requestSpecification).body(newUserBodyModel)
                        .when()
                        .put(baseUrl + "2")
                        .then()
                        .spec(getUserResponseSpec)
                        .extract().as(UpdatedUserResponseModel.class));
        step("Check response", () -> {
            assertEquals("Alex", updatedUserResponseModel.getName());
            assertEquals("TopManager", updatedUserResponseModel.getJob());
            assertNotNull(updatedUserResponseModel.getUpdatedAt());
        });
    }
    @Test
    @Tag("Ready")
    public void DeleteUserTest(){
        given(UserSpecs.requestSpecification).delete(baseUrl+"2")
                .then() //блок после запроса
                .spec(deleteUserResponseSpec);
    }
}