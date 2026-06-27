package day4;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.Cookie;
import io.restassured.response.Response;

public class CookiesTest {
	@Test
	public void testCookiesInResponse() {
		Response response=given()
		.when()
			.get("https://www.google.com/")
		.then()
			.statusCode(200)
			.log().cookies()
			// Cookie value validation
			//.cookie("__Secure-STRP","ANmZwa1ZGj_hvxiO-YeSLaPRi406awp1WQptpQqCCgEsfaTDc84-kySjngGMWfgSYJbDFsS4f4Nl17v8IhLoLnkxHu-ivVtxvDet")
			.cookie("__Secure-STRP",notNullValue())
			.cookie("__Secure-BUCKET",notNullValue())
			.extract().response(); // Response contains body, headers & cookies
		
		// Extract a specific cookie value
		String cookieValue = response.getCookie("__Secure-STRP");
		System.out.println("Cookie Name:" + cookieValue);

		// Extract all cookies
		Map<String, String> allCookies = response.getCookies();
		System.out.println("All the cookies:" + allCookies);

		// Printing all cookies using enhanced for loop
		for (String key : allCookies.keySet()) {
			System.out.println(key + ": " + allCookies.get(key));
		}
		
		// Get detailed information about the cookie
		Cookie cookieInfo = response.getDetailedCookie("__Secure-STRP");
		System.out.println(cookieInfo);
		System.out.println(cookieInfo.hasExpiryDate()); // Verify the expiry date
		System.out.println(cookieInfo.getExpiryDate()); // Prints the expiry date
		System.out.println(cookieInfo.hasValue());
		System.out.println(cookieInfo.getValue());
		System.out.println(cookieInfo.isSecured());
		
	}
}
