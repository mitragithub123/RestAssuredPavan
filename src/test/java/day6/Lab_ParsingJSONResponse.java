package day6;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class Lab_ParsingJSONResponse {

	// 1. Simple JSON Parsing
	@Test(priority = 1)
	public void testSimpleJsonParsing() throws FileNotFoundException {

		File file = new File(".\\src\\test\\resources\\simple.json");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JSONTokener jsonTokener = new JSONTokener(fileReader);

		JSONObject simpleJsonResponse = new JSONObject(jsonTokener);

		// Parse JSON response
		JsonPath jsonPath = new JsonPath(simpleJsonResponse.toString());
		String placeId = jsonPath.getString("place_id");

		// Assertions
		System.out.println("Place ID: " + placeId);
		assertThat(placeId, is("9172e95034d47483d8e0775710eb6a54"));
	}

	// 2. Nested JSON Parsing
	@Test(priority = 2)
	public void testNestedJsonParsing() throws FileNotFoundException {

		File file = new File(".\\src\\test\\resources\\nested.json");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JSONTokener jsonTokener = new JSONTokener(fileReader);

		JSONObject nestedJsonResponse = new JSONObject(jsonTokener);

		// Parse JSON response
		JsonPath jsonPath = new JsonPath(nestedJsonResponse.toString());

		// a) Get purchase amount
		int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount: " + purchaseAmount);
		assertThat(purchaseAmount, is(1060));

		// b) Get title of first course
		String firstCourseTitle = jsonPath.getString("courses[0].title");
		System.out.println("First Course Title: " + firstCourseTitle);
		assertThat(firstCourseTitle, is("Selenium"));

		// c) Get total copies sold by Postman
		int postmanCopies = jsonPath.getInt("courses[3].copies");
		System.out.println("Total Copies of Postman: " + postmanCopies);
		assertThat(postmanCopies, is(5));

		// d) Total number of courses
		int totalCourses = jsonPath.getInt("courses.size()");
		System.out.println("Total Number of Courses: " + totalCourses);
		assertThat(totalCourses, is(4));

		// e) Print all course titles
		System.out.print("Course Titles: ");
		for (int i = 0; i < totalCourses; i++) {
			String courseTitle = jsonPath.getString("courses[" + i + "].title");
			System.out.println(courseTitle);
		}
		System.out.println();
	}
}
