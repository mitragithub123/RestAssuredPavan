package day4;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import org.testng.annotations.Test;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class HeadersTest {
	
	@Test
	public void testHeadersInResponse() {
		Response response=given()
		.when()
			.get("https://www.google.com/")
		.then()
			.statusCode(200)
			.log().headers()
			.header("Content-Type", "text/html; charset=ISO-8859-1") // Entire value validation
			.header("Content-Type", containsString("text/html")) // Contains
			.header("Content-Encoding", notNullValue()) // Checks header value is present
			.header("Content-Encoding", equalTo("gzip")) // Verify header value
			.extract().response(); // Response contains body, headers & cookies
		
		// Extract Specific header
		String headerValue = response.getHeader("Content-Type");
		System.out.println("Value of header Content-Type is: " + headerValue);

		// Extract all headers
		Headers headers = response.getHeaders();
		for (Header header : headers) {
			System.out.println(header.getName() + "--> " + header.getValue());
		}
	}
}
