package day3;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

public class AuthenticationTests {
	// Basic authentication
	@Test
	public void verifyBasicAuth() {
		given()
			.auth().basic("postman","password")
		.when()
			.get("https://postman-echo.com/basic-auth")
		.then()
			.statusCode(200)
			.body("authenticated", equalTo(true))
			.log().body();
		
	}
	
	// Basic Preemptive Authentication (Only Rest assured supports this not postman)
	@Test
	public void verifyBasicPreemptiveAuth() {
		given()
			.auth().preemptive().basic("postman","password")
		.when()
			.get("https://postman-echo.com/basic-auth")
		.then()
			.statusCode(200)
			.body("authenticated", equalTo(true))
			.log().body();
		
	}
	
	// Digest Authentication
	@Test
	public void verifyDigestiveAuth() {
		given()
			.auth().digest("postman","password")
		.when()
			.get("https://postman-echo.com/basic-auth")
		.then()
			.statusCode(200)
			.body("authenticated", equalTo(true))
			.log().body();
		
	}
	
	// Bearer token authentication (Will go along with header)
	//Login Github>User icon>Settings>Developer settings>Expand Personal access tokens>Tokens (classic)>Generate new token (classic)
	// Here dummy token added. Else we can not push
	@Test
	public void verifyBearerToken() {
		String bearerTokenString="dummy";
		given()
			.header("Authorization","Bearer "+bearerTokenString)
			
		.when()
			.get("https://api.github.com/user/repos")
		.then()
			.statusCode(200)
			.log().body();
	}
	
	// API Key authentication (Will go along with query parameter)
	// https://api.openweathermap.org/data/2.5/weather?q=Delhi
	@Test
	public void verifyApiKey() {
		given()
			.queryParam("q", "Delhi")
			.queryParam("appid","fe9c5cddb7e01d747b4611c3fc9eaf2c")
		.when()
			.get("https://api.openweathermap.org/data/2.5/weather")
		.then()
			.statusCode(200)
			.log().body();
	}
	
}
