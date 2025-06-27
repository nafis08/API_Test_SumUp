package test.java;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import POJO.ApiKeyManager;
import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.RestAssured;
import utils.BaseTest;
import utils.Config;
import utils.Payload;
import utils.SessionData;

@Listeners({AllureTestNg.class})

public class CreateBankAccountTest extends BaseTest{

	@Test(dependsOnMethods= {"test.java.SignUpTest.signUp","test.java.ApiKeyCreationTest.getApiKey"})
	@Description("Create Account")
	public void creatAccount() {

		System.out.println("API Key: "+ApiKeyManager.getApiKey());
		System.out.println("Given body: \n"+ Payload.userDetails());

		RestAssured.baseURI = Config.getBaseUrl();

		given().log().all().header("x-api-key", SessionData.apiKey)
				.contentType("application/json")
				.body(Payload.userDetails())
				.when().post("/api/accounts").then().log().all().statusCode(200);
	}

}
