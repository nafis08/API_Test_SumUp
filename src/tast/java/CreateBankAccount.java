package tast.java;

import org.testng.annotations.Test;

import POJO.ApiKeyManager;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class CreateBankAccount {

	@Test
	public void creatAccount() {
		RestAssured.baseURI = "https://global-bank-qa-challenge-external.fleet.dev.eu-west-1.sumup.net";

		given().log().all().header("x-api-key", ApiKeyManager.getApiKey())
				.contentType("application/json")
				.body("{\n" + "  \"first_name\": \"daniel\",\n" + "  \"last_name\": \"raegan\",\n"
						+ "  \"date_of_birth\": \"1980-05-30\",\n" + "  \"initial_deposit\": 10\n" + "}")
				.when().post("/api/accounts").then().statusCode(200);
	}

}
