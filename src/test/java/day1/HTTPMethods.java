package day1;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class HTTPMethods {
	/*
	Precondition: (Given) - Content Type, Set cookies, Add auth, Add params, Add headers. Payloads
	Action: (When) - get, post, put, patch, delete etc
	Validation: (Then) - validate status code, extract response, extract headers, cookies and response body 	
	*/
	/*
	 * Validations:
	 * Status code
	 * Response body
	 * Response time
	 * Content-Type
	 * Response string
	 */
	Faker faker=new Faker();
	int userId;
	//Get user
	@Test(priority = 1)
	void getUser() {
		given()
			.header("x-api-key", "free_user_3EnQMnZdflCgDVuez6c2BNTjIRw")
		.when()
			.get("https://reqres.in/api/users?page=2")
		.then()
			.statusCode(200)
			.body("page", equalTo(2))
			.body(containsString("email"))
			.header("Content-Type", "application/json; charset=utf-8")
			.time(lessThan(5000L))
			.log().all();
		
	}
	
	// Create User
	@Test(priority = 2)
	void createUser() {
		HashMap<String, String> responseBody = new HashMap<String, String>();
		responseBody.put("name", "John1");
		responseBody.put("job", "Dev1");
		
		given()
			.header("x-api-key", "free_user_3EnQMnZdflCgDVuez6c2BNTjIRw")
			.body(responseBody)
			.contentType("application/json")
		.when()
			.post("https://reqres.in/api/users")
		.then()
			.statusCode(201)
			.header("Content-Type", "application/json; charset=utf-8")
			.header("Content-Length", notNullValue())
			.time(lessThan(10000L))
			.body("name", equalTo("John1")) //Hard coded
			.body("name", equalTo(responseBody.get("name"))) //Dynamic
			.body("job", equalTo("Dev1")) //Hard coded
			.body("job", equalTo(responseBody.get("job"))) //Dynamic
			.body(containsString("id"))
			.log().all();
		
	}
	
	// Create User (Using Faker)
	@Test(priority = 3)
	void createUser1() {
		HashMap<String, String> responseBody = new HashMap<String, String>();
		responseBody.put("name", faker.name().fullName());
		responseBody.put("job", faker.job().title());
		
		userId=given()
			.header("x-api-key", "free_user_3EnQMnZdflCgDVuez6c2BNTjIRw")
			.body(responseBody)
			.contentType("application/json")
		.when()
			.post("https://reqres.in/api/users")
		.then()
			.statusCode(201)
			.header("Content-Type", "application/json; charset=utf-8")
			.header("Content-Length", notNullValue())
			.body("name", equalTo(responseBody.get("name"))) //Dynamic
			.body("job", equalTo(responseBody.get("job"))) //Dynamic
			.body(containsString("id"))
			.log().all()
			.extract().jsonPath().getInt("id"); // Extract id
		
	}
	
	// Update User (Get the id from createUser1) - Chaining apis
	@Test(priority = 4, dependsOnMethods = "createUser1")
	void updateUser() {
		HashMap<String, String> responseBody = new HashMap<String, String>();
		responseBody.put("name", faker.name().fullName());
		responseBody.put("job", faker.job().title());
		
		given()
			.header("x-api-key", "free_user_3EnQMnZdflCgDVuez6c2BNTjIRw")
			.body(responseBody)
			.contentType("application/json")
		.when()
				.put("https://reqres.in/api/users/" + userId)
		.then()
			.statusCode(200)
			.header("Content-Type", "application/json; charset=utf-8")
			.header("Content-Length", nullValue())
			.body("name", equalTo(responseBody.get("name"))) //Dynamic
			.body("job", equalTo(responseBody.get("job"))) //Dynamic
			.body(containsString("updatedAt"))
			.log().all();
		
	}
	
	// Delete User (Get the id from createUser1) - Chaining apis
	@Test(priority = 5, dependsOnMethods = {"createUser1","updateUser"})
	void deleteUser() {		
		given()
			.header("x-api-key", "free_user_3EnQMnZdflCgDVuez6c2BNTjIRw")
			.contentType("application/json")
		.when()
			.delete("https://reqres.in/api/users/" + userId)
		.then()
			.statusCode(204)
			.body(emptyOrNullString()); //Negative validation to check empty body
		
	}
	
}
