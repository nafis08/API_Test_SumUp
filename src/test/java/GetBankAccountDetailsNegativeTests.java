package test.java;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.RestAssured;
import utils.BaseTest;
import utils.Config;
import utils.SessionData;

@Listeners({AllureTestNg.class})
public class GetBankAccountDetailsNegativeTests extends BaseTest {

    @Test(dependsOnMethods= {"test.java.SignUpTest.signUp", "test.java.ApiKeyCreationTest.getApiKey", "test.java.CreateBankAccountTest.creatAccount"})
    @Description("Get Account Details With NegativeId")
    public void getAccountDetailsWithNegativeId() {
        RestAssured.baseURI = Config.getBaseUrl();
        int negativeId = -1;

        given().log().all()
            .header("x-api-key", SessionData.apiKey)
            .contentType("application/json")
            .when()
            .get("/api/accounts/{id}", negativeId)
            .then().log().all()
            .statusCode(404);  // Not found but should be invalid as ID cannot be negative
    }

    @Test(dependsOnMethods= {"test.java.SignUpTest.signUp", "test.java.ApiKeyCreationTest.getApiKey", "test.java.CreateBankAccountTest.creatAccount"})
    @Description("Get Account Details With NonNumericId")
    public void getAccountDetailsWithNonNumericId() {
        RestAssured.baseURI = Config.getBaseUrl();
        String invalidId = "abc123";

        given().log().all()
            .header("x-api-key", SessionData.apiKey)
            .contentType("application/json")
            .when()
            .get("/api/accounts/{id}", invalidId)
            .then().log().all()
            .statusCode(404);  
    }

    @Test(dependsOnMethods= {"test.java.SignUpTest.signUp", "test.java.ApiKeyCreationTest.getApiKey", "test.java.CreateBankAccountTest.creatAccount"})
    @Description("Get Account Details Without Api Key")
    public void getAccountDetailsWithoutApiKey() {
        RestAssured.baseURI = Config.getBaseUrl();
        int accountId = BaseTest.userId;

        given().log().all()
            .contentType("application/json")
            .when()
            .get("/api/accounts/{id}", accountId)
            .then().log().all()
            .statusCode(401);  // Unauthorized due to missing API key
    }
}
