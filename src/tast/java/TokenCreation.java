package tast.java;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class TokenCreation {
	
	@Test
	public static void getToken() {
		RestAssured.baseURI = "https://global-bank-qa-challenge-external.fleet.dev.eu-west-1.sumup.net";

		String response = given().log().all().header("Content-type", "application/json")
				.body("{\n" + "  \"username\": \"francis\",\n" + "  \"password\": \"pass123\"\n" + "}").when()
				.post("/api/auth/login").then().statusCode(200).extract().asString();
		
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String ApiKey = js.getString("api_key");
		
		System.out.println("API Key is: "+ApiKey);
	}

}
