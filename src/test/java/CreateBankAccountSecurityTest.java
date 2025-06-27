package test.java;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import utils.BaseTest;
import utils.Config;
import utils.Payload;
import utils.SessionData;

public class CreateBankAccountSecurityTest extends BaseTest{
	@Test(dependsOnMethods = {"test.java.SignUpTest.signUp"})
	@Description("Create Account With Invalid Api Key")
    public void createAccountWithInvalidApiKey() {
        RestAssured.baseURI = Config.getBaseUrl();

        given().log().all()
            .header("x-api-key", "invalid-api-key-123")
            .contentType(ContentType.JSON)
            .body(Payload.userDetails())
            .when().post("/api/accounts")
            .then().log().all()
            .statusCode(403);  // Forbidden
    }

    @Test(dependsOnMethods = {"test.java.SignUpTest.signUp", "test.java.ApiKeyCreationTest.getApiKey"})
    @Description("Create Account With Injection Attack")
    public void createAccountWithInjectionAttack() {
        RestAssured.baseURI = Config.getBaseUrl();

        String injectionPayload = "{"
            + "\"first_name\": \"John'; DROP TABLE users; --\","
            + "\"last_name\": \"Doe<script>alert(1)</script>\","
            + "\"date_of_birth\": \"1990-01-01\","
            + "\"initial_deposit\": 1000"
            + "}";

        given().log().all()
            .header("x-api-key", SessionData.apiKey)
            .contentType(ContentType.JSON)
            .body(injectionPayload)
            .when().post("/api/accounts")
            .then().log().all()
            .statusCode(400); // Should have been rejected but returned 411 
    }
}
