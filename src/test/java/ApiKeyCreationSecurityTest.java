package test.java;

import static io.restassured.RestAssured.given;


import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import utils.BaseTest;
import utils.Config;

public class ApiKeyCreationSecurityTest extends BaseTest{
	@Test
    @Description("Test login with unsupported media type")
    public void testUnsupportedContentType() {
        String payload = "{\"username\": \"validuser\", \"password\": \"validPass123\"}";

        given()
            .baseUri(Config.getBaseUrl())
            .contentType("text/plain")
            .body(payload)
        .when()
            .post("/api/auth/login")
        .then()
            .statusCode(415); // Unsupported Media Type
    }

    @Test
    @Description("Test SQL injection in login credentials")
    public void testSqlInjection() {
        String payload = "{ \"username\": \"admin' OR '1'='1\", \"password\": \"anything\" }";

        given()
            .baseUri(Config.getBaseUrl())
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/api/auth/login")
        .then()
            .statusCode(401); // Should NOT allow SQL injection to succeed
    }

    @Test
    @Description("Test script injection in login username")
    public void testScriptInjection() {
        String payload = "{ \"username\": \"<script>alert('x')</script>\", \"password\": \"pass123\" }";

        given()
            .baseUri(Config.getBaseUrl())
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/api/auth/login")
        .then()
            .statusCode(400); // Should not allow script injection
    }

    @Test
    @Description("Test repeated login attempts to simulate brute-force")
    public void testBruteForceLogin() {
        String payload = "{ \"username\": \"testuser\", \"password\": \"wrongpass\" }";

        RestAssured.baseURI = Config.getBaseUrl();

        for (int i = 0; i < 10; i++) {
            given()
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .post("/api/auth/login")
            .then()
                .statusCode(401); // Should fail consistently
        }
    }

    @Test
    @Description("Test login using HTTP instead of HTTPS (if your API requires HTTPS)")
    public void testHttpInsteadOfHttps() {
        RestAssured.baseURI = Config.getBaseUrl().replace("https://", "http://");

        String payload = "{ \"username\": \"user\", \"password\": \"pass123\" }";

        given()
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/api/auth/login")
        .then()
            .statusCode(301); // Permanent redirect
    }
}
