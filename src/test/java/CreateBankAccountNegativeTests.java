package test.java;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import utils.BaseTest;
import utils.Config;
import utils.Payload;
import utils.SessionData;

@Listeners({AllureTestNg.class})
public class CreateBankAccountNegativeTests extends BaseTest {

    @Test(dependsOnMethods = {"test.java.SignUpTest.signUp", "test.java.ApiKeyCreationTest.getApiKey"})
    @Description("Create Account With Missing Fields")
    public void createAccountWithMissingFields() {
        RestAssured.baseURI = Config.getBaseUrl();

        String invalidPayload = "{ \"first_name\": \"\", \"last_name\": \"\", \"date_of_birth\": \"\", \"initial_deposit\": 0 }";

        given().log().all()
            .header("x-api-key", SessionData.apiKey)
            .contentType(ContentType.JSON)
            .body(invalidPayload)
            .when().post("/api/accounts")
            .then().log().all()
            .statusCode(400);  // expecting bad request due to missing data
    }

    @Test(dependsOnMethods = {"test.java.SignUpTest.signUp", "test.java.ApiKeyCreationTest.getApiKey"})
    @Description("Create Account With Invalid Deposit Type")
    public void createAccountWithInvalidDepositType() {
        RestAssured.baseURI = Config.getBaseUrl();

        String invalidPayload = "{ \"first_name\": \"John\", \"last_name\": \"Doe\", \"date_of_birth\": \"1990-01-01\", \"initial_deposit\": \"invalidNumber\" }";

        given().log().all()
            .header("x-api-key", SessionData.apiKey)
            .contentType(ContentType.JSON)
            .body(invalidPayload)
            .when().post("/api/accounts")
            .then().log().all()
            .statusCode(400);  // expecting bad request due to invalid data type
    }

    @Test(dependsOnMethods = {"test.java.SignUpTest.signUp", "test.java.ApiKeyCreationTest.getApiKey"})
    @Description("Create Account With Empty Body")
    public void createAccountWithEmptyBody() {
        RestAssured.baseURI = Config.getBaseUrl();

        given().log().all()
            .header("x-api-key", SessionData.apiKey)
            .contentType(ContentType.JSON)
            .body("{}")
            .when().post("/api/accounts")
            .then().log().all()
            .statusCode(400);  // empty body should fail
    }

    @Test(dependsOnMethods = {"test.java.SignUpTest.signUp"})
    @Description("Create Account With Missing Api Key")
    public void createAccountWithMissingApiKey() {
        RestAssured.baseURI = Config.getBaseUrl();

        given().log().all()
            .contentType(ContentType.JSON)
            .body(Payload.userDetails())
            .when().post("/api/accounts")
            .then().log().all()
            .statusCode(403);  // Forbidden
    }

    @Test(dependsOnMethods = {"test.java.SignUpTest.signUp", "test.java.ApiKeyCreationTest.getApiKey"})
    @Description("Create Account With Large Payload")
    public void createAccountWithLargePayload() {
        RestAssured.baseURI = Config.getBaseUrl();

        // Very long string to test boundary
        String longName = "A".repeat(500);

        String largePayload = "{"
            + "\"first_name\": \"" + longName + "\","
            + "\"last_name\": \"" + longName + "\","
            + "\"date_of_birth\": \"1990-01-01\","
            + "\"initial_deposit\": 1000"
            + "}";

        given().log().all()
            .header("x-api-key", SessionData.apiKey)
            .contentType(ContentType.JSON)
            .body(largePayload)
            .when().post("/api/accounts")
            .then().log().all()
            .statusCode(411); // Length required returned
    }
}
