package day2;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
// Run the students.json first
public class PostRequestBodyExamples {

	/*
	 * 1) HashMap 
	 * 2) org.json library 
	 * 3) Using java POJO class (Plain Old Java
	 * Object) 
	 * 4) External JSON file
	 */
	
	// Create post request body using HashMap
	String studentID;
	@Test
	public void createStudentUsingHashMap() {
		HashMap<String, Object> requestBody=new HashMap<String, Object>();
		requestBody.put("name", "Scott");
		requestBody.put("location", "France");
		requestBody.put("phone", "82455566");
		String courses1[]= {"C","C++"};
		requestBody.put("courses", courses1);
		
		studentID=given()
			.contentType("application/json")
			.body(requestBody)
		
		.when()
			.post("http://localhost:3000/students")
		.then()
			.statusCode(201)
			.body("name", equalTo("Scott"))
			.body("location", equalTo("France"))
			.body("courses[0]", equalTo("C"))
			.body("courses[1]", equalTo("C++"))
			.header("Content-Type","application/json")
			.log().body()
			.extract().jsonPath().getString("id");
	}
	
	// Create post request body using org.json library
	@Test
	public void createStudentUsingJsonLibrary() {
		JSONObject requestBody=new JSONObject();
		requestBody.put("name", "Scott");
		requestBody.put("location", "France");
		requestBody.put("phone", "82455566");
		String courses1[]= {"C","C++"};
		requestBody.put("courses", courses1);
		
		studentID=given()
				.contentType("application/json")
				.body(requestBody.toString())
			
			.when()
				.post("http://localhost:3000/students")
			.then()
				.statusCode(201)
				.body("name", equalTo("Scott"))
				.body("location", equalTo("France"))
				.body("courses[0]", equalTo("C"))
				.body("courses[1]", equalTo("C++"))
				.header("Content-Type","application/json")
				.log().body()
				.extract().jsonPath().getString("id");
	}
	
	// Create post request body using POJO class
	
	@Test
	public void createStudentUsingPOJOClass() {
		StudentPojo requestBody=new StudentPojo();
		requestBody.setName("Scott");
		requestBody.setLocation("France");
		requestBody.setPhone("82455566");
		String courses1[]= {"C","C++"};
		requestBody.setCourses(courses1);
		
		studentID=given()
				.contentType("application/json")
				.body(requestBody)
			
			.when()
				.post("http://localhost:3000/students")
			.then()
				.statusCode(201)
				.body("name", equalTo(requestBody.getName()))
				.body("location", equalTo(requestBody.getLocation()))
				.body("courses[0]", equalTo(requestBody.getCourses()[0]))
				.body("courses[1]", equalTo(requestBody.getCourses()[1]))
				.header("Content-Type","application/json")
				.log().body()
				.extract().jsonPath().getString("id");
	}
	
	// Create post request body using External JSON File
	@Test
	public void createStudentUsingExternalJsonFile() throws FileNotFoundException {
		File file=new File("./src/test/java/day2/Body.json"); //Capture file path
		FileReader fileReader=new FileReader(file); //Open the file in reading mode
		JSONTokener jsonTokener=new JSONTokener(fileReader); //Open the file using JSONTokener form
		JSONObject requestBody=new JSONObject(jsonTokener);// Convert token into JSONObject
		
		studentID=given()
				.contentType("application/json")
				.body(requestBody.toString())
			
			.when()
				.post("http://localhost:3000/students")
			.then()
				.statusCode(201)
				.body("name", equalTo("Scott"))
				.body("location", equalTo("France"))
				.body("courses[0]", equalTo("C"))
				.body("courses[1]", equalTo("C++"))
				.header("Content-Type","application/json")
				.log().body()
				.extract().jsonPath().getString("id");
	}
	
	@AfterMethod
	public void deleteStudentRecord() {
		given()
		.when()
			.delete("http://localhost:3000/students/"+studentID)
		.then()
			.statusCode(200);
	}
}
