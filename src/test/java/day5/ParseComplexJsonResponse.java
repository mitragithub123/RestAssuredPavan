package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class ParseComplexJsonResponse {

	// Converts json file into Json response in the form of json object
	public JSONObject getJsonResponse() {
		File file = new File("src/test/resources/complex.json");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JSONTokener jsonTokener = new JSONTokener(fileReader);
		JSONObject jsonObject = new JSONObject(jsonTokener);
		return jsonObject;
	}

	@Test(priority = 1)
	public void userDetailsValidation() {
		// Parse JSON response
		JsonPath jsonPath = new JsonPath(getJsonResponse().toString());

		// a) Verify the status field in the JSON response is "success".
		String status = jsonPath.getString("status");
		System.out.println(status);
		assertThat(status, is("success"));

		// b) Validate the id, name, and email fields of the user.
		int id = jsonPath.getInt("data.userDetails.id");
		System.out.println(id);
		assertThat(id, is(12345));

		String name = jsonPath.getString("data.userDetails.name");
		System.out.println(name);
		assertThat(name, is("John Doe"));

		String email = jsonPath.getString("data.userDetails.email");
		System.out.println(email);
		assertThat(email, is("john.doe@example.com"));

		// c) Confirm the first phone number is of type home with the value
		// 123-456-7890.
		String homePhone = jsonPath.getString("data.userDetails.phoneNumbers[0].number");
		System.out.println(homePhone);
		assertThat(homePhone, is("123-456-7890"));

		String homePhoneType = jsonPath.getString("data.userDetails.phoneNumbers[0].type");
		System.out.println(homePhoneType);
		assertThat(homePhoneType, is("home"));

		// d) Verify the geo coordinates of the user's address are latitude 39.7817 and
		// longitude -89.6501.
		double latitude = jsonPath.getDouble("data.userDetails.address.geo.latitude");
		System.out.println(latitude);
		assertThat(latitude, is(39.7817));

		double longitude = jsonPath.getDouble("data.userDetails.address.geo.longitude");
		System.out.println(longitude);
		assertThat(longitude, is(-89.6501));

		// e) Validate that the user has enabled notifications and is using the "dark"
		// theme

		boolean notifications = jsonPath.getBoolean("data.userDetails.preferences.notifications");
		System.out.println(notifications);
		assertThat(notifications, is(true));

		String theme = jsonPath.getString("data.userDetails.preferences.theme");
		System.out.println(theme);
		assertThat(theme, is("dark"));

	}

	@Test(priority = 2)
	public void testRecentOrdersValidation() {
		// Parse JSON response
		JsonPath jsonPath = new JsonPath(getJsonResponse().toString());

		// a) Verify total number of orders
		int totalOrders = jsonPath.getInt("data.recentOrders.size()");
		System.out.println(totalOrders);
		assertThat(totalOrders, is(2));

		// b) Validate first order details
		int firstOrderId = jsonPath.getInt("data.recentOrders[0].orderId");
		System.out.println(firstOrderId);
		assertThat(firstOrderId, is(101));

		double firstOrderTotal = jsonPath.getDouble("data.recentOrders[0].totalAmount");
		System.out.println(firstOrderTotal);
		assertThat(firstOrderTotal, is(1226.49));

		// c) Validate second item in the first order
		String secondItemName = jsonPath.getString("data.recentOrders[0].items[1].name");
		System.out.println(secondItemName);
		assertThat(secondItemName, is("Mouse"));

		double secondItemPrice = jsonPath.getDouble("data.recentOrders[0].items[1].price");
		System.out.println(secondItemPrice);
		assertThat(secondItemPrice, is(25.50));

		// d) Validate second order details
		int secondOrderItems = jsonPath.getInt("data.recentOrders[1].items.size()");
		System.out.println(secondOrderItems);
		assertThat(secondOrderItems, is(1));

		String secondOrderItemName = jsonPath.getString("data.recentOrders[1].items[0].name");
		System.out.println(secondOrderItemName);
		assertThat(secondOrderItemName, is("Smartphone"));

		double secondOrderItemPrice = jsonPath.getDouble("data.recentOrders[1].items[0].price");
		System.out.println(secondOrderItemPrice);
		assertThat(secondOrderItemPrice, is(799.99));
	}

	@Test(priority = 3)
	public void testPreferencesAndMetadataValidation() {
		// Parse JSON response
		JsonPath jsonPath = new JsonPath(getJsonResponse().toString());

		// a) Validate languages
		int totalLanguages = jsonPath.getInt("data.userDetails.preferences.languages.size()");
		System.out.println(totalLanguages);
		assertThat(totalLanguages, is(3));

		String firstLanguage = jsonPath.getString("data.userDetails.preferences.languages[0]");
		System.out.println(firstLanguage);
		assertThat(firstLanguage, is("English"));

		// b) Validate metadata
		String requestId = jsonPath.getString("meta.requestId");
		System.out.println(requestId);
		assertThat(requestId, is("abc123xyz"));

		int responseTime = jsonPath.getInt("meta.responseTimeMs");
		System.out.println(responseTime);
		assertThat(responseTime, lessThan(300));
	}
}
