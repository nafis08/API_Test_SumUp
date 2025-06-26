package tast.java;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class Login {
	@Test
	public void login() {
		
		RestAssured.baseURI = "https://global-bank-qa-challenge-external.fleet.dev.eu-west-1.sumup.net";
		
		String response = given().log().all().contentType(ContentType.JSON)
		.body("{\n"
				+ "  \"username\": \"daniel\",\n"
				+ "  \"password\": \"pass123\"\n"
				+ "}")
		.when().log().all().post("/api/auth/api/signup")
		.then().log().all().statusCode(200).extract().asString();
		
		JsonPath js = new JsonPath(response);
		String id = js.getString("id");
		System.out.println(id);
		
	}

}
