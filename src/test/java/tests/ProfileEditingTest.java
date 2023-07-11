package tests;

import models.LoginFormModel;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.Cookie;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static com.codeborne.selenide.Selenide.open;
import static specs.DemoUserSpec.responseSpecification;
import static specs.DemoUserSpec.postRequestSpecification;

public class ProfileEditingTest extends TestBase {

    @Test
    @Tag("Ready")
    public void ProfileEditingTest() {
        String authCookieKey = "NOPCOMMERCE.AUTH";
        LoginFormModel loginFormModel = new LoginFormModel();
        loginFormModel.setLogin("stackinthecorner@gmail.com");
        loginFormModel.setPassword("qwerty123");
        loginFormModel.setNewPassword("qwerty12345");

        //выполнить авторизацию
        step("Perform authorisation", () -> {
            String authorisationCookie = given(postRequestSpecification)
                    .formParam("Email", loginFormModel.getLogin())
                    .formParam("Password", loginFormModel.getPassword())
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecification)
                    .extract()
                    .cookie(authCookieKey);
            open("/Content/jquery-ui-themes/smoothness/images/ui-bg_flat_75_ffffff_40x100.png");
            Cookie authCookie = new Cookie(authCookieKey, authorisationCookie);
            getWebDriver().manage().addCookie(authCookie);

        });
        //залогиниться с новым паролем
        step("Open profile, change password and logout", () -> {
            open("/customer/changepassword");
            $("#OldPassword").setValue(loginFormModel.getPassword());
            $("#NewPassword").setValue(loginFormModel.getNewPassword());
            $("#ConfirmNewPassword").setValue(loginFormModel.getNewPassword());
            $(".change-password-button").click();
            $(".ico-logout").click();
        });
        step("Log in with new password", () -> {
            given(postRequestSpecification)
                    .formParam("Email", loginFormModel.getLogin())
                    .formParam("Password", loginFormModel.getNewPassword())
                    .when()
                    .post("/login")
                    .then()
                    .spec(responseSpecification);
        });
    }

}
