package test.java;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import utils.BaseTest;
import utils.Config;

@Listeners({ AllureTestNg.class })
public class ApiKeyCreationNegativeTest extends BaseTest {

    @DataProvider(name = "invalidLoginPayloads")
    public Object[][] invalidLoginPayloads() {
        return new Object[][] {
            { "{\"username\": \"\", \"password\": \"validPass123\"}", 401, "Empty username" }, //Should be failed but returned 200
            { "{\"username\": \"validuser\", \"password\": \"\"}", 401, "Empty password" },
            { "{\"password\": \"validPass123\"}", 400, "Missing username field" },
            { "{\"username\": \"validuser\"}", 400, "Missing password field" },
            { "{\"username\": \"invalidUser\", \"password\": \"wrongPass\"}", 401, "Wrong credentials" },
            { "{\"username\": \"user@#123\", \"password\": \"validPass123\"}", 401, "Special characters in username" }, //May be specification
            { "{\"username\": \"<script>alert(1)</script>\", \"password\": \"validPass123\"}", 400, "Script injection attempt" },
            { "{\"username\": \"" + "a".repeat(10000) + "\", \"password\": \"validPass123\"}", 400, "Excessively long username" },
            { "{\"username\": \"validuser\", \"password\": \"validPass123\", \"unexpected\": \"field\"}", 400, "Unexpected extra field" }
        };
    }

    @Test(dataProvider = "invalidLoginPayloads")
    @Description("Negative tests for login API with invalid payloads")
    public void testInvalidLoginPayloads(String payload, int expectedStatus, String scenario) {
        RestAssured.baseURI = Config.getBaseUrl();

        given()
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/api/auth/login")
        .then().log().all()
            .statusCode(expectedStatus);

        System.out.println("Tested scenario: " + scenario);
    }

    @Test
    @Description("Test login with malformed JSON")
    public void testMalformedJson() {
        String payload = "{ \"username\": \"validuser\", \"password\": \"validpass123\" "; // missing closing }

        given()
            .baseUri(Config.getBaseUrl())
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/api/auth/login")
        .then()
            .statusCode(400); //
    }
}
