package test.java;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.RestAssured;
import utils.BaseTest;
import utils.Config;

@Listeners({AllureTestNg.class})
public class GetBankAccountDetailsTest {

	@Test(dependsOnMethods= {"test.java.SignUpTest.signUp","test.java.ApiKeyCreationTest.getApiKey","test.java.CreateBankAccountTest.creatAccount"})
	@Description("Get Bank Account Details")
	public void getBankAccountDetails() {
		RestAssured.baseURI = Config.getBaseUrl();
		System.out.println("ID is: "+BaseTest.userId);
		int accountId = BaseTest.userId;
		String response = given().log().all().contentType("application/json")
		.when()
		.get("/api/accounts/{id}", accountId)
		.then().statusCode(200).extract().asString();

		System.out.println(response);
	}

}
