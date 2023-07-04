package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
public class UserSpecs {
    public static RequestSpecification requestSpecification = with()
            .header("Content-Type","application/json")
            .log().uri()
            .log().body()
            .filter(withCustomTemplates());
    public static ResponseSpecification getUserResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .build();
    public static ResponseSpecification createUserResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(201)
            .build();
    public static ResponseSpecification deleteUserResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(204)
            .build();
    public static ResponseSpecification notFoundUserResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(404)
            .build();
}
