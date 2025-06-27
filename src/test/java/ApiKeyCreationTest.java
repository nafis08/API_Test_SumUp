package test.java;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import POJO.ApiKeyManager;
import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utils.BaseTest;
import utils.Config;
import utils.SessionData;

@Listeners({ AllureTestNg.class })
public class ApiKeyCreationTest extends BaseTest {

	@Test(dependsOnMethods= {"test.java.SignUpTest.signUp"})
	@Description("Get Api Key")
	public static void getApiKey() {
		if (testUser == null) {
			throw new RuntimeException("User not found.");
		}

		System.out.println("Username: "+ testUser.username);
		RestAssured.baseURI = Config.getBaseUrl();

		String response = given().log().all().header("Content-type", "application/json")
				.body("{\n" + "  \"username\": \"" + testUser.username + "\",\n" + "  \"password\": \""
						+ testUser.password + "\"\n" + "}")
				.when().log().all().post("/api/auth/login").then().log().all().statusCode(200).extract().asString();

		System.out.println(response);
		JsonPath js = new JsonPath(response);
		SessionData.apiKey = js.getString("api_key");

		System.out.println("API Key is: " + SessionData.apiKey);
		ApiKeyManager.setApiKey(SessionData.apiKey);
	}

}