package day6;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;

// Run src/test/resources/employees.json
// Open CMD, in src/test/resources
// json-server employees.json
// Launch URL http://localhost:3000/employees
// Check the response, its annonomous json array

public class ParsingJSONArrayEmployeeAPI {
	
	@Test
	public void testJsonResponseBody() {

		ResponseBody responseBody = given()
		.when()
			.get("http://localhost:3000/employees")
		.then()
			.statusCode(200)
			.extract().response().body();

		// asString() used in case of responseBody whether for response toString() is used
		JsonPath jsonPath = new JsonPath(responseBody.asString());

		// Get the size of the json array
		int empCount = jsonPath.getInt("size()"); // Check the api response, its annonomous json array
		
		//  Print all the details of the employee
		for (int i = 0; i < empCount; i++) {
			String firstName = jsonPath.getString("[" + i + "].first_name"); // i.first_name
			String lastName = jsonPath.getString("[" + i + "].last_name"); // i.last_name
			String email = jsonPath.getString("[" + i + "].email");
			String gender = jsonPath.getString("[" + i + "].gender");
			System.out.println(firstName + " " + lastName + "  " + email + "  " + gender);
		}
		
		// Search for an employee name "Steve" in the list
		boolean status = false;
		for (int i = 0; i < empCount; i++) {
			String firstName = jsonPath.getString("[" + i + "].first_name");
			if (firstName.equalsIgnoreCase("Steve")) {
				status = true;
				break;
			}
		}
		assertThat(status, is(true)); // Steven exists in the list or not
		
	}
}
