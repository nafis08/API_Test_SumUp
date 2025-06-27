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

@Listeners({ AllureTestNg.class })
public class SignUpSecurityTest extends BaseTest{

    @Test
    @Description("Test for SQL injection attempt")
    public void testSqlInjectionAttempt() {
        String payload = "{ \"username\": \"test' OR '1'='1\", \"password\": \"pass123\" }";

        given()
            .baseUri(Config.getBaseUrl())
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/api/auth/api/signup")
        .then()
            .statusCode(400); // Must not be 200 as it should be rejected
    }

    @Test
    @Description("Test for HTML/script injection in username")
    public void testScriptInjection() {
        String payload = "{ \"username\": \"<script>alert('x')</script>\", \"password\": \"pass123\" }";

        given()
            .baseUri(Config.getBaseUrl())
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/api/auth/api/signup")
        .then()
            .statusCode(400); //Should be rejected
    }

    @Test
    @Description("Test repeated signup attempts (brute-force/flooding)")
    public void testSignupFlooding() {
        String payload = "{ \"username\": \"floodTestUser\", \"password\": \"floodTestPass\" }";

        RestAssured.baseURI = Config.getBaseUrl();

        for (int i = 0; i < 10; i++) {
            given()
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .post("/api/auth/api/signup")
            .then()
                .statusCode(400);
        }
    }

    @Test
    @Description("Test insecure HTTP request if HTTPS is required")
    public void testHttpInsteadOfHttps() {
        RestAssured.baseURI = Config.getBaseUrl().replace("https://", "http://");

        String payload = "{ \"username\": \"httpUser\", \"password\": \"pass123\" }";

        given()
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/api/auth/api/signup")
        .then()
            .statusCode(301);
    }
}
