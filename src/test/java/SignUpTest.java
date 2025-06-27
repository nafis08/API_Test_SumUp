package test.java;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import utils.BaseTest;
import utils.Config;
import utils.Payload;

@Listeners({ AllureTestNg.class })
public class SignUpTest extends BaseTest {

    @Test
    @Description("API test to validate SignUp of users")
    public void signUp() {

        // Generate and store user for future tests
        testUser = Payload.generateUser();
        String payload = Payload.userCredentials(testUser);

        RestAssured.baseURI = Config.getBaseUrl();

        String response = given().log().all()
                .contentType(ContentType.JSON)
                .body(payload)
                .when().post("/api/auth/api/signup")
                .then().log().all().statusCode(200)
                .extract().asString();

        JsonPath js = new JsonPath(response);
        String id = js.getString("id");
        userId = js.getInt(id);

        System.out.println("Signed up user ID: " + id);
        System.out.println("User: " + testUser.username + " / " + testUser.password);
    }
}
