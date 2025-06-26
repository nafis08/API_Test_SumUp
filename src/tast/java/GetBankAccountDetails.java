package tast.java;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class GetBankAccountDetails {
	
	@Test
	public void getBankAccountDetails() {
		RestAssured.baseURI = "https://global-bank-qa-challenge-external.fleet.dev.eu-west-1.sumup.net";
		
		int accountId = 2;
		String response = given().log().all().contentType("application/json")
		.when()
		.get("/api/accounts/{id}", accountId)
		.then().statusCode(200).extract().asString();
		
		System.out.println(response);
	}

}
