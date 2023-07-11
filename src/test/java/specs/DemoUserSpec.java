package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.filter.log.LogDetail.*;

public class DemoUserSpec {
    public static RequestSpecification postRequestSpecification = with()
            .contentType("application/x-www-form-urlencoded")
            .log().all()
            .filter(withCustomTemplates());

    public static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(302)
            .build();
}

