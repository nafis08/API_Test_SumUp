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
public class SignUpNegativeTest extends BaseTest{

    @DataProvider(name = "invalidPayloads")
    public Object[][] invalidPayloads() {
        return new Object[][] {
            { "{\"username\": \"\", \"password\": \"validPass123\"}", 400, "Empty username" },//Should have been rejected with status code 400 due to empty user name but accepts and identifies as existing user returning status code 409
            { "{\"username\": \"validuser\", \"password\": \"\"}", 400, "Empty password" }, //Should have been rejected the request due to empty password but accepts and identifies as existing user returning status code 409
            { "{\"password\": \"validPass123\"}", 400, "Missing username field" },
            { "{\"username\": \"validuser\"}", 400, "Missing password field" },
            { "{\"username\": \"validuser\", \"password\": \"123\"}", 400, "Too short password" }, //Should have been rejected due to too short password but accepts and identifies as existing user returning code 409
            { "{\"username\": \"<script>alert(1)</script>\", \"password\": \"validPass123\"}", 400, "Script injection" }, //Script injection should be invalid but it is already existing
            { "{\"username\": \"<b>bold</b>\", \"password\": \"validPass123\"}", 400, "HTML injection" }, //HTML injection should be rejected but it is already existing as user and returns 409
            { "{\"username\": \"" + "a".repeat(10000) + "\", \"password\": \"validPass123\"}", 500, "Excessively long username" },
            { "{\"username\": \"validuser\", \"password\": \"validPass123\", \"role\": \"admin\"}", 409, "Unexpected field injection" }
        };
    }

    @Test(dataProvider = "invalidPayloads")
    @Description("Test invalid signup payloads for negative scenarios")
    public void testInvalidSignUps(String payload, int expectedStatus, String scenario) {
        RestAssured.baseURI = Config.getBaseUrl();

        given()
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/api/auth/api/signup")
        .then()
            .statusCode(expectedStatus);

        System.out.println("Tested: " + scenario);
    }

    @Test
    @Description("Test signup with malformed JSON")
    public void testMalformedJson() {
        String payload = "{ \"username\": \"validuser\", \"password\": \"validpass123\" "; // missing closing }

        given()
            .baseUri(Config.getBaseUrl())
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/api/auth/api/signup")
        .then()
            .statusCode(400);
    }

    @Test
    @Description("Test signup with unsupported media type")
    public void testUnsupportedContentType() {
        String payload = "{\"username\": \"validuser\", \"password\": \"validPass123\"}";

        given()
            .baseUri(Config.getBaseUrl())
            .contentType("text/plain")
            .body(payload)
        .when()
            .post("/api/auth/api/signup")
        .then()
            .statusCode(415);
    }
}
