package POJO;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiKeyManager {

	private static String apiKey;

	public static String getApiKey() {
		if (apiKey == null) {
			apiKey = generateApiKey();
		}
		return apiKey;
	}

	private static String generateApiKey() {
		RestAssured.baseURI = "https://global-bank-qa-challenge-external.fleet.dev.eu-west-1.sumup.net";

		try {
			AuthenticationApiKey response = given().contentType(ContentType.JSON)
					.body("{\n" + "  \"username\": \"francis\",\n" + "  \"password\": \"pass123\"\n" + "}").when()
					.post("/api/auth/login").then().statusCode(200).extract().as(AuthenticationApiKey.class);

			return response.getApiKey();
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve API key: " + e.getMessage(), e);
		}
	}
}
